package com.emer.firstplugin.Events.SectionFour;

import com.emer.firstplugin.Firstplugin;
import com.emer.firstplugin.PlayerMission;
import com.emer.firstplugin.Zones.SnowBallZone;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Events implements Listener {

    private List<PlayerMission> playerMissionList = new ArrayList<>();
    private List<SnowBallZone> snowBallZoneList = new ArrayList<>();
    private List<UUID> enableChat = new ArrayList<>();

    public Events() {
        snowBallZoneList.add(new SnowBallZone(
                new Location(Bukkit.getWorld("world"), -133, 0, 42),
                new Location(Bukkit.getWorld("world"), -106, 255, 60)
        ));
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent moveEvent) {
        //moveEvent.setCancelled(true);
        //moveEvent.getPlayer().sendMessage(ChatColor.RED + "Stop Moving!");
    }

    @EventHandler
    public void onPlayerEggThrown(PlayerEggThrowEvent eggThrowEvent) {
        eggThrowEvent.getPlayer().sendMessage("You just threw an egg");
    }


    // When a player enters to the server send a message and gift a kit if is him first time
    @EventHandler
    public void onPlayerEnterToServer(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();

        if (!player.hasPlayedBefore()) {
            player.sendMessage(ChatColor.GREEN + "Welcome to the server, it's your first time" +
                    "here, so we'll give an armor!");

            player.getInventory().addItem(newPlayerArmor(Material.LEATHER_HELMET));
            player.getInventory().addItem(newPlayerArmor(Material.LEATHER_CHESTPLATE));
            player.getInventory().addItem(newPlayerArmor(Material.LEATHER_LEGGINGS));
            player.getInventory().addItem(newPlayerArmor(Material.LEATHER_BOOTS));

            return;
        }
        /** Storm
         player.getWorld().setStorm(true);
         player.getWorld().setThundering(true);
         player.getWorld().setThunderDuration(100);
         player.getWorld().setTime(16000);
         **/

        player.sendMessage(ChatColor.GREEN + "Welcome back to the server");

        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 200, 3, true, true));

        // to remove all potion effects
        /**
         for (PotionEffect effect : player.getActivePotionEffects()) {
         player.removePotionEffect(effect.getType());
         }
         **/


    }

    // When a player enter shows a title where says, Hello, Welcome to the server
    @EventHandler
    public void onJoinEventTitles(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();
        player.getPlayer().sendTitle(ChatColor.RED + "Hello!",
                ChatColor.GREEN + "Welcome to the server",
                20,
                60,
                20);

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy("§4§lHello My Friend!"));
        player.setPlayerListHeaderFooter(ChatColor.RED + "Hello", "First Line\n Second Line");

        PlayerMission playerMission = findPlayerMissionByPlayer(player);

        if (playerMission == null) {
            addPlayerMissionBossBarToList(player);
        } else {
            if (playerMission.getBossBar().getProgress() != 1.0) {
                playerMission.getBossBar().addPlayer(player);
            }
        }

        System.out.println(player.getStatistic(Statistic.DROP_COUNT));
    }

    @EventHandler
    public void onPlayerEatSpiderEye(PlayerItemConsumeEvent playerItemConsumeEvent) {
        if (playerItemConsumeEvent.getItem().getType().equals(Material.SPIDER_EYE)) {
            Player player = playerItemConsumeEvent.getPlayer();
            double actualHealth = player.getHealthScale();
            player.setHealthScale(actualHealth + 2.0F);
        }
    }

    @EventHandler
    public void onPlayerBreakALog(BlockBreakEvent blockBreakEvent) {
        Player player = blockBreakEvent.getPlayer();

        Material blockType = blockBreakEvent.getBlock().getType();
        PlayerMission playerMission = findPlayerMissionByPlayer(player);

        if (playerMission == null) {
            addPlayerMissionBossBarToList(player);
            playerMission = findPlayerMissionByPlayer(player);
        }

        if (isWood(blockType)) {
            if (playerMission.getBossBar().getProgress() >= 0.9 && playerMission.getBossBar().getProgress() != 1.0) {
                playerMission.getBossBar().setProgress(1.0);
                playerMission.getBossBar().removePlayer(player);
                player.getPlayer().sendTitle(ChatColor.GREEN + "Congratulations!",
                        ChatColor.GREEN + "You break the 10 logs!",
                        20,
                        50,
                        20);
                rewardFor10Logs(player);
            } else if (playerMission.getBossBar().getProgress() <= 0.9) {
                playerMission.getBossBar().setProgress(playerMission.getBossBar().getProgress() + 0.1);
            }
        }
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent playerToggleSneakEvent) {
        if (playerToggleSneakEvent.isSneaking()) {
            /** send fireworks
             Firework firework = playerToggleSneakEvent.getPlayer().getWorld().spawn(playerToggleSneakEvent.getPlayer().getLocation(),
             Firework.class);
             FireworkMeta fireworkMeta = firework.getFireworkMeta();
             fireworkMeta.addEffect(FireworkEffect.builder()
             .withColor(Color.BLUE)
             .withColor(Color.RED)
             .withFade(Color.BLACK)
             .with(FireworkEffect.Type.CREEPER)
             .withFlicker().build());
             fireworkMeta.setPower(1);
             firework.setFireworkMeta(fireworkMeta);
             **/
            playerToggleSneakEvent.getPlayer().playSound(playerToggleSneakEvent.getPlayer().getLocation(),
                    Sound.BLOCK_ANVIL_BREAK,
                    1.0F,
                    1.0F);
        }

        playerToggleSneakEvent.getPlayer().playSound(playerToggleSneakEvent.getPlayer().getLocation(),
                Sound.BLOCK_NOTE_BLOCK_BANJO,
                1,
                0.79F);
    }

    @EventHandler
    public void onPlayerInteractShootASnowball(PlayerInteractEvent playerInteractEvent) {
        Player player = playerInteractEvent.getPlayer();
        if (playerInteractEvent.hasItem()) {
            if (playerInteractEvent.getAction().equals(Action.RIGHT_CLICK_AIR) ||
                    playerInteractEvent.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                player.getInventory().getItemInMainHand();
                if (player.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_SWORD)) {
                    for (SnowBallZone zone : snowBallZoneList) {
                        if (zone.isWithin(player.getLocation())) {
                            player.launchProjectile(Snowball.class);
                            player.playSound(player.getLocation(), Sound.ENTITY_SNOWBALL_THROW, 1F, 1F);
                            break;
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onHit(ProjectileHitEvent projectileHitEvent) {
        if (projectileHitEvent.getEntity() instanceof Snowball snowball) {
            if (projectileHitEvent.getHitBlock() != null && isWood(projectileHitEvent.getHitBlock().getType())) {
                if (snowball.getShooter() instanceof Player player) {
                    for (SnowBallZone zone : snowBallZoneList) {
                        if (zone.isWithin(player.getLocation())) {
                            if (zone.isWithin(projectileHitEvent.getHitBlock().getLocation())) {
                                projectileHitEvent.getHitBlock().setType(Material.AIR);
                                World world = snowball.getWorld();
                                var hitLocation = snowball.getLocation();
                                world.createExplosion(hitLocation, 6.0F, false, true);
                                player.sendMessage(ChatColor.AQUA + "You destroyed a Log!");
                                Bukkit.getLogger().info(player.getDisplayName() + " Destroyed a Log!");
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteractWithLetter(PlayerInteractEvent playerInteractEvent) {
        Player player = playerInteractEvent.getPlayer();
        ;
        if (playerInteractEvent.getHand() != null && playerInteractEvent.getHand().equals(EquipmentSlot.HAND)) {
            if (playerInteractEvent.getAction().equals(Action.RIGHT_CLICK_AIR) ||
                    playerInteractEvent.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (player.getInventory().getItemInMainHand().getType().equals(Material.OAK_SIGN)) {
                    if (!enableChat.contains(player.getUniqueId())) {
                        enableChat.add(player.getUniqueId());
                        player.sendMessage(ChatColor.RED + "Chat has been disabled");
                    } else {
                        enableChat.remove(player.getUniqueId());
                        player.sendMessage(ChatColor.GREEN + "Chat has been enabled");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onChatIsMuteOrNot(AsyncPlayerChatEvent asyncPlayerChatEvent) {
        if (enableChat.contains(asyncPlayerChatEvent.getPlayer().getUniqueId())) {
            asyncPlayerChatEvent.setCancelled(true);
            asyncPlayerChatEvent.getPlayer().sendMessage("Chat is disabled right now!");
        }
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent playerInteractEntityEvent) {
        Player player = playerInteractEntityEvent.getPlayer();

        if (player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
            player.addPassenger(playerInteractEntityEvent.getRightClicked());
            player.getPassengers();

        } else if (player.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_SWORD)){
            playerInteractEntityEvent.getRightClicked().addPassenger(player);
        }
    }

    @EventHandler
    public void onExpulseAPassenger(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        Entity passenger = player.getPassenger();
        if (passenger == null) {
            return;
        }

        Location playerLocation = player.getLocation();
        Vector launchDirecton = playerLocation.getDirection().multiply(2);
        launchDirecton.setY(0.5);

        passenger.leaveVehicle();

        if (passenger instanceof Player passengerPlayer) {
            Bukkit.getScheduler().runTaskLater(Firstplugin.getInstance(), () -> {
                passengerPlayer.setVelocity(launchDirecton);
                passengerPlayer.sendMessage("Te tiraron!");
                player.sendMessage("You throw up your passenger!");
            }, 2L);
        } else {
            passenger.setVelocity(launchDirecton);
        }

    }

    @EventHandler
    public void onPlayerQuitRemoveFromRecentMessages(PlayerQuitEvent playerQuitEvent) {
        Firstplugin.getInstance().getRecentMessages().remove(playerQuitEvent.getPlayer().getUniqueId());
    }

    private boolean isWood(Material material) {
        return material.name().endsWith("_LOG");
    }

    private void rewardFor10Logs(Player player) {

        ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta diamonSwordMeta = diamondSword.getItemMeta();
        diamonSwordMeta.addEnchant(Enchantment.FIRE_ASPECT, 50, true);
        diamonSwordMeta.addEnchant(Enchantment.SMITE, 50, true);
        diamondSword.setItemMeta(diamonSwordMeta);

        player.getInventory().addItem(diamondSword);

    }

    private PlayerMission findPlayerMissionByPlayer(Player player) {
        for (PlayerMission playerMission : playerMissionList) {
            if (playerMission.getPlayer().equals(player.getDisplayName())) {
                System.out.println("Player found " + playerMission.getPlayer());
                return playerMission;
            }
        }
        return null;
    }

    private ItemStack newPlayerArmor(Material material) {
        ItemStack partOfArmor = new ItemStack(material);
        LeatherArmorMeta partOfArmorMeta = (LeatherArmorMeta) partOfArmor.getItemMeta();
        partOfArmorMeta.setDisplayName("Joint Event");
        partOfArmorMeta.setColor(Color.LIME);
        partOfArmor.setItemMeta(partOfArmorMeta);
        return partOfArmor;
    }

    private void addPlayerMissionBossBarToList(Player player) {
        BossBar bossBar = Bukkit.createBossBar(
                ChatColor.LIGHT_PURPLE + "Break 10 logs",
                BarColor.BLUE,
                BarStyle.SEGMENTED_6,
                BarFlag.DARKEN_SKY
        );

        PlayerMission newPlayerMission = new PlayerMission(player.getDisplayName(), 0.0F, bossBar);

        newPlayerMission.getBossBar().setProgress(0);
        newPlayerMission.getBossBar().addPlayer(player);

        playerMissionList.add(newPlayerMission);
    }

    public void clearAllBossBars() {
        for (PlayerMission playerMission : playerMissionList) {
            playerMission.getBossBar().removeAll();
        }

        playerMissionList.clear();
    }

}
