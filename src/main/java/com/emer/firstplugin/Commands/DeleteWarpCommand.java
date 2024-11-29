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

public class DeleteWarpCommand implements CommandExecutor {

    private Firstplugin main;
    public static final String WARP_NAME = "warps.yml";

    public DeleteWarpCommand(Firstplugin main) {
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player player) {
            String displayName = player.getDisplayName();
            File warpsFile = new File(main.getDataFolder(),WARP_NAME);
            YamlConfiguration warpsConfig = YamlConfiguration.loadConfiguration(warpsFile);

            if (strings.length == 1) {
                warpsConfig.set(displayName+"."+strings[0], null);
                saveConfig(warpsFile, warpsConfig);
                player.sendMessage(ChatColor.GREEN + "You deleted " + strings[0]);
            } else {
                player.sendMessage(ChatColor.RED + "Error, Use /delTp <tp>");
            }
        }
        return false;
    }

    private void saveConfig(File file, YamlConfiguration configuration) {
        try {
            configuration.save(file);
        } catch (IOException e) {
            System.out.println("Error saving warps.yml");
        }
    }
}
