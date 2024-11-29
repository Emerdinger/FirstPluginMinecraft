package com.emer.firstplugin;

import com.emer.firstplugin.Commands.*;
import com.emer.firstplugin.Events.SectionFive.EventsFive;
import com.emer.firstplugin.Events.SectionFour.Events;
import com.emer.firstplugin.Tabs.GamemodeTab;
import com.emer.firstplugin.Tabs.PunishTab;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public final class Firstplugin extends JavaPlugin implements Listener {

    private Events events;
    public static Firstplugin instance;
    private final HashMap<UUID, UUID> recentMessages = new HashMap<>();

    @Override
    public void onEnable() {
        initiateFile("warps");
        initiateFile("language");
        instance = this;
        events = new Events();
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        getServer().getPluginManager().registerEvents(events, this);
        getServer().getPluginManager().registerEvents(new EventsFive(), this);

        registerCommandsAndTabCompleters();

        events.clearAllBossBars();

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage("Han pasado 100 segundos");
            }
        },2000, 2000);
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

    private void initiateFile(String name) {
        File file = new File(getDataFolder(),name+".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Can't load file! Error.");
            }
        }
    }

    private void registerCommandsAndTabCompleters() {
        Objects.requireNonNull(getCommand("heal"), "Command not found").setExecutor(new HealCommand());
        Objects.requireNonNull(getCommand("gm"), "Command not found").setExecutor(new GamemodeCommand(this));
        Objects.requireNonNull(getCommand("gm"), "Command not found").setTabCompleter(new GamemodeTab());
        Objects.requireNonNull(getCommand("config"), "Command not found").setExecutor(new ConfigCommand(this));
        Objects.requireNonNull(getCommand("permission"), "Command not found").setExecutor(new PermissionsCommand());
        Objects.requireNonNull(getCommand("lava-particle"), "Command not found").setExecutor(new ParticleCommand());
        Objects.requireNonNull(getCommand("vanish"), "Command not found").setExecutor(new VanishCommand());
        Objects.requireNonNull(getCommand("rules"), "Command not found").setExecutor(new BookCommand());
        Objects.requireNonNull(getCommand("banner"), "Command not found").setExecutor(new BannerCommand());
        Objects.requireNonNull(getCommand("punish"), "Command not found").setExecutor(new PunishCommand());
        Objects.requireNonNull(getCommand("punish"), "Command not found").setTabCompleter(new PunishTab());
        Objects.requireNonNull(getCommand("message"), "Command not found").setExecutor(new MessageCommand(this));
        Objects.requireNonNull(getCommand("reply"), "Command not found").setExecutor(new ReplyCommand(this));
        Objects.requireNonNull(getCommand("menu"), "Command not found").setExecutor(new MenuCommand());
        Objects.requireNonNull(getCommand("boost"), "Command not found").setExecutor(new BoostCommand());
        Objects.requireNonNull(getCommand("skull"),"Command not found").setExecutor(new SkullCommand());
        Objects.requireNonNull(getCommand("setTp"), "Command not found").setExecutor(new CreateWarpCommand(this));
        Objects.requireNonNull(getCommand("delTp"), "Command not found").setExecutor(new DeleteWarpCommand(this));
        Objects.requireNonNull(getCommand("go"), "Command not found").setExecutor(new WarpCommand(this));
    }
}
