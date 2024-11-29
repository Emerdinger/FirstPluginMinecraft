package com.emer.firstplugin.Commands;

import com.emer.firstplugin.Firstplugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class WarpCommand implements CommandExecutor {

    private Firstplugin main;
    private static final String WARP_NAME = "warps.yml";

    public WarpCommand(Firstplugin main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player player) {
            File warpsFile = new File(main.getDataFolder(),WARP_NAME);
            YamlConfiguration warpsConfig = YamlConfiguration.loadConfiguration(warpsFile);

            if (strings.length == 1) {
                if (existTp(player, strings, warpsConfig)) {
                    goToWarp(player, warpsConfig, strings);
                } else {
                    player.sendMessage(ChatColor.RED + "Tp not found!");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Error, use /go <tpName>");
            }
        }
        return false;
    }

    private boolean existTp(Player player, String [] strings, YamlConfiguration warpsConfig) {
        return warpsConfig.get(player.getDisplayName() + "." + strings[0]) != null;
    }

    private void goToWarp(Player player, YamlConfiguration configuration, String[] strings){
        try {
            double x = configuration.getDouble(player.getDisplayName()+"."+strings[0]+".x", Double.NaN);
            double y = configuration.getDouble(player.getDisplayName()+"."+strings[0]+".y", Double.NaN);
            double z = configuration.getDouble(player.getDisplayName()+"."+strings[0]+".z", Double.NaN);
            Location warpLocation = new Location(player.getWorld(), x,y,z);
            player.teleport(warpLocation);
        } catch (Exception e) {
            player.sendMessage(ChatColor.RED + "Some cord is null, remove tp an create it again!");
        }
    }
}
