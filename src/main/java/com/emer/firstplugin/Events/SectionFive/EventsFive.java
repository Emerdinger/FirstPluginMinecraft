package com.emer.firstplugin.Events.SectionFive;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Rail;
import org.bukkit.block.data.type.Cake;
import org.bukkit.block.data.type.GlassPane;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.HashMap;
import java.util.Random;

public class EventsFive implements Listener {

    @EventHandler
    public void onClickMenuEvent(InventoryClickEvent inventoryClickEvent) {
        if (ChatColor.translateAlternateColorCodes(
                '&', inventoryClickEvent.getView().getTitle()).equals(ChatColor.GREEN + "Tool Menu")
        && inventoryClickEvent.getCurrentItem() != null) {
            Player player = (Player) inventoryClickEvent.getWhoClicked();
            inventoryClickEvent.setCancelled(true);
            switch (inventoryClickEvent.getRawSlot()) {
                case 0:
                    break;
                case 20:
                    Random random = new Random();
                    Player playerToTp = (Player) Bukkit.getOnlinePlayers().toArray()[random.nextInt(Bukkit.getOnlinePlayers().size())];
                    player.teleport(playerToTp);
                    if (player.getName().equals(playerToTp.getName())) {
                        player.sendMessage(ChatColor.RED + "No players to tp");
                    } else {
                        player.sendMessage(ChatColor.BLUE + "You teleported to " + playerToTp.getName() + "!");
                    }
                    break;
                case 22:
                    player.setHealth(0);
                    player.sendMessage(ChatColor.RED + "You killed yourself!");
                    break;
                case 24:
                    player.closeInventory();
                    player.getInventory().clear();
                    player.sendMessage(ChatColor.AQUA + "Inventory clear");
                    break;
                default:
                    return;
            }
            player.closeInventory();
        }
    }

    private HashMap<Location, Boolean> railTouchedIsNorth = new HashMap<>();
    private HashMap<Location, Boolean> railTouchedIsEast = new HashMap<>();
    @EventHandler
    public void onRightClickBlock(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            BlockState state = event.getClickedBlock().getState();
            BlockData data = state.getBlockData();

            if (data instanceof Cake) {
                ((Cake) data).setBites(2);
                state.setBlockData(data);
                state.update();
            } else if (data instanceof GlassPane) {
                ((GlassPane) data).setWaterlogged(true);
                state.setBlockData(data);
                state.update();
            }

            if (data instanceof Rail) {
                if (!railTouchedIsNorth.containsKey(state.getLocation()) && !railTouchedIsEast.containsKey(state.getLocation())){
                     railTouchedIsNorth.put(state.getLocation(), true);
                     railTouchedIsEast.put(state.getLocation(), true);
                }
                System.out.println(((Rail) data).getShape());
                System.out.println(railTouchedIsNorth.get(state.getLocation()));
                switch (((Rail) data).getShape()) {
                    case NORTH_SOUTH:
                        if (railTouchedIsNorth.get(state.getLocation())){
                            ((Rail) data).setShape(Rail.Shape.NORTH_EAST);
                            railTouchedIsNorth.put(state.getLocation(), false);
                            state.setBlockData(data);
                            state.update();
                        } else {
                            ((Rail) data).setShape(Rail.Shape.SOUTH_WEST);
                            railTouchedIsNorth.put(state.getLocation(), true);
                            state.setBlockData(data);
                            state.update();
                        }
                        break;
                    case NORTH_EAST:
                        ((Rail) data).setShape(Rail.Shape.EAST_WEST);
                        state.setBlockData(data);
                        state.update();
                        break;
                    case EAST_WEST:
                        if (railTouchedIsEast.get(state.getLocation())) {
                            ((Rail) data).setShape(Rail.Shape.SOUTH_EAST);
                            railTouchedIsEast.put(state.getLocation(), false);
                            state.setBlockData(data);
                            state.update();
                        } else {
                            ((Rail) data).setShape(Rail.Shape.NORTH_WEST);
                            railTouchedIsEast.put(state.getLocation(), true);
                            state.setBlockData(data);
                            state.update();
                        }
                        break;
                    case SOUTH_EAST:
                        ((Rail) data).setShape(Rail.Shape.NORTH_SOUTH);
                        state.setBlockData(data);
                        state.update();
                        break;
                    case NORTH_WEST:
                        ((Rail) data).setShape(Rail.Shape.NORTH_SOUTH);
                        state.setBlockData(data);
                        state.update();
                        break;
                    case SOUTH_WEST:
                        ((Rail) data).setShape(Rail.Shape.EAST_WEST);
                        state.setBlockData(data);
                        state.update();
                        break;
                    default:
                        return;
                }
            }
        }
    }

    @EventHandler
    public void onSneakChangeBlock(PlayerToggleSneakEvent playerToggleSneakEvent) {
        Player player = playerToggleSneakEvent.getPlayer();
        if (playerToggleSneakEvent.isSneaking() && player.getTargetBlockExact(5) != null && player.getTargetBlockExact(5).getType() != Material.OAK_SIGN) {
            player.sendBlockChange(player.getTargetBlockExact(5).getLocation(), Material.DIAMOND_BLOCK.createBlockData());
        } else if (playerToggleSneakEvent.isSneaking() && player.getTargetBlockExact(5) != null && player.getTargetBlockExact(5).getType().equals(Material.OAK_SIGN)) {
            player.sendSignChange(player.getTargetBlockExact(5).getLocation(), new String[]{
                    "Im",
                    "a",
                    "test!",
                    " "
            });
        }
    }
}

