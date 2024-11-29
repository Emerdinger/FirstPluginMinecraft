package com.emer.firstplugin.Commands;

import com.emer.firstplugin.Firstplugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class CreateWarpCommand implements CommandExecutor {

    private final Firstplugin main;
    private static final String WARP_NAME = "warps.yml";

    public CreateWarpCommand(Firstplugin main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player player){
            String playerName = player.getDisplayName();
            File warp = new File(main.getDataFolder(), WARP_NAME);
            YamlConfiguration warpModify = YamlConfiguration.loadConfiguration(warp);

            if (strings.length == 1) {
                warpModify.set(playerName+"."+strings[0]+".x", player.getLocation().getX());
                warpModify.set(playerName+"."+strings[0]+".y", player.getLocation().getY());
                warpModify.set(playerName+"."+strings[0]+".z", player.getLocation().getZ());
                player.sendMessage(ChatColor.GREEN + "Warp " + strings[0] + " created successfully");
                saveFile(warp, warpModify);
            } else if (strings.length == 4){
                warpModify.set(playerName+"."+strings[0]+".x", Integer.valueOf(strings[1]));
                warpModify.set(playerName+"."+strings[0]+".y", Integer.valueOf(strings[2]));
                warpModify.set(playerName+"."+strings[0]+".z", Integer.valueOf(strings[3]));
                player.sendMessage(ChatColor.GREEN + "Warp " + strings[0] + " created successfully");
                saveFile(warp, warpModify);
            } else {
                player.sendMessage(ChatColor.RED + "Error, usage /setTp <nameTp> or /setTp <nameTp> <x> <y> <z>");
            }
        }
        return false;
    }

    private void saveFile(File file, YamlConfiguration configuration) {
        try {
            configuration.save(file);
        } catch (IOException e) {
            System.out.println("Error saving warps.yml");
        }
    }
}
