package com.emer.firstplugin.Commands;

import com.emer.firstplugin.Firstplugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageCommand implements CommandExecutor {
    private final Firstplugin main;

    public MessageCommand(Firstplugin main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            if (strings.length >= 2) {
                if (Bukkit.getPlayerExact(strings[0]) != null) {
                    Player target = Bukkit.getPlayerExact(strings[0]);

                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 1; i < strings.length; i++) {
                        stringBuilder.append(strings[i]).append(" ");
                    }
                    player.sendMessage("[private] You -> " + target.getName() + ": " + stringBuilder);
                    target.sendMessage("[private] " + player.getName() + ": " + stringBuilder);
                    main.getRecentMessages().put(target.getUniqueId(), player.getUniqueId());
                } else {
                    player.sendMessage(ChatColor.RED + "This player was not found!");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Invalid usage! Use /Message <player> <message>.");
            }
        }
        return false;
    }
}
