package com.emer.firstplugin.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PermissionsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player player) {
            if (player.hasPermission("firstplugin.use")) {
                player.sendMessage("You have permission to use this");
            } else {
                player.sendMessage("You don't have permissions to use this");
            }
        }
        return false;
    }
}
