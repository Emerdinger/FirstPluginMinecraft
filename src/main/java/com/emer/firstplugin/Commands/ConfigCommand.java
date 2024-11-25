package com.emer.firstplugin.Commands;

import com.emer.firstplugin.Firstplugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ConfigCommand implements CommandExecutor {

    private final Firstplugin main;

    public ConfigCommand(Firstplugin main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player player) {
            player.sendMessage(main.getConfig().getString("Word"));
            player.sendMessage(main.getConfig().getInt("Number") + "");

            if (main.getConfig().getBoolean("Enable")){
                player.sendMessage(ChatColor.AQUA + "Enable");
            } else {
                player.sendMessage(ChatColor.RED + "Disable");
            }

            for (String string : main.getConfig().getStringList("String-list")) {
                player.sendMessage(string);
            }

            main.getConfig().set("Minecraft", "Test");
            List<String> list = main.getConfig().getStringList("String-list");
            list.add("Santos");
            main.getConfig().set("String-list", list);
            main.saveConfig();
        }
        return false;
    }
}
