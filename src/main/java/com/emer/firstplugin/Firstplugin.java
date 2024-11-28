package com.emer.firstplugin;

import com.emer.firstplugin.Commands.*;
import com.emer.firstplugin.Events.SectionFive.EventsFive;
import com.emer.firstplugin.Events.SectionFour.Events;
import com.emer.firstplugin.Tabs.GamemodeTab;
import com.emer.firstplugin.Tabs.PunishTab;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public final class Firstplugin extends JavaPlugin implements Listener {

    private Events events;
    public static Firstplugin instance;
    private HashMap<UUID, UUID> recentMessages = new HashMap<>();

    @Override
    public void onEnable() {
        initiateFile("warps");
        instance = this;
        events = new Events();
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        getServer().getPluginManager().registerEvents(events, this);
        getServer().getPluginManager().registerEvents(new EventsFive(), this);

        ArmorStand armorStand = (ArmorStand) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), 1, 66, 4)
                , EntityType.ARMOR_STAND);

        Block block = Bukkit.getWorld("world").getBlockAt(1, 65, 4);
        block.setType(Material.DIAMOND_BLOCK);


        getCommand("heal").setExecutor(new HealCommand());
        getCommand("gm").setExecutor(new GamemodeCommand(this));
        getCommand("gm").setTabCompleter(new GamemodeTab());
        getCommand("config").setExecutor(new ConfigCommand(this));
        getCommand("permission").setExecutor(new PermissionsCommand());
        getCommand("lava-particle").setExecutor(new ParticleCommand());
        getCommand("vanish").setExecutor(new VanishCommand());
        getCommand("rules").setExecutor(new BookCommand());
        getCommand("banner").setExecutor(new BannerCommand());
        getCommand("punish").setExecutor(new PunishCommand());
        getCommand("punish").setTabCompleter(new PunishTab());
        getCommand("message").setExecutor(new MessageCommand(this));
        getCommand("reply").setExecutor(new ReplyCommand(this));
        getCommand("menu").setExecutor(new MenuCommand());
        getCommand("boost").setExecutor(new BoostCommand());
        getCommand("skull").setExecutor(new SkullCommand());

        events.clearAllBossBars();

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage("Han pasado 10 segundos");
            }
        },200, 200);
    }

    @Override
    public void onDisable() {
        if (events != null) {
            events.clearAllBossBars();
        }

        getLogger().info("FirstPlugin disabled.");
    }

    public static Firstplugin getInstance() {
        return instance;
    }

    public HashMap<UUID, UUID> getRecentMessages() {return recentMessages;}

    private File initiateFile(String name) {
        File file = new File(getDataFolder(),name+".yaml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Can't load file! Error.");
            }
        }
        return file;
    }
}
