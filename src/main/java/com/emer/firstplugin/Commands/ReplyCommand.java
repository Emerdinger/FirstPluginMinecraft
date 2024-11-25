package com.emer.firstplugin.Commands;

import com.emer.firstplugin.Firstplugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ReplyCommand implements CommandExecutor {

    private final Firstplugin main;

    public ReplyCommand(Firstplugin main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            if (strings.length >= 1) {
                if (main.getRecentMessages().containsKey(player.getUniqueId())){
                    UUID uuid = main.getRecentMessages().get(player.getUniqueId());
                    Player target = Bukkit.getPlayer(uuid);
                    if (target != null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < strings.length; i++) {
                            stringBuilder.append(strings[i]).append(" ");
                        }
                        player.sendMessage("[private] You -> " + target.getName() + ": " + stringBuilder);
                        target.sendMessage("[private] "+ player.getName() + ": " + stringBuilder);
                        main.getRecentMessages().put(target.getUniqueId(), player.getUniqueId());
                    } else {
                        player.sendMessage(ChatColor.RED + "The player is offline!");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "You haven't messaged anyone yet!");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Invalid usage! Use /message <player> <message>");
            }
        }
        return false;
    }
}
