package com.emer.firstplugin.Commands;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Calendar;

public class PunishCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player player) {
            if (strings.length == 2 || strings.length == 3) {
                if (Bukkit.getPlayer(strings[0]) != null) {
                    Player target = Bukkit.getPlayer(strings[0]);
                    switch (strings[1].toLowerCase()) {
                        case "kick":
                            target.kickPlayer(ChatColor.RED + "You have been kicked for being a bad player");
                            break;
                        case "ban":
                            Bukkit.getBanList(BanList.Type.NAME).addBan(target.getName(), ChatColor.RED +
                                    "Being a bad player!", null, null);
                            target.kickPlayer(ChatColor.RED + "You have been banned!");
                            break;
                        case "tempban":
                            if (strings.length == 3) {
                                try {
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.add(Calendar.MINUTE, Integer.parseInt(strings[2]));
                                    Bukkit.getBanList(BanList.Type.NAME).addBan(target.getName(), ChatColor.RED +
                                            "Being a bad player!", calendar.getTime(), null);
                                    target.kickPlayer(ChatColor.RED + "You have been banned! until " + calendar.getTime());
                                } catch (Exception ignored) {
                                    player.sendMessage(ChatColor.RED + "You must need to enter a number!");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "You did not specify the time to ban!");
                            }
                            break;
                        default:
                            player.sendMessage(ChatColor.RED + "You did not specify whether to kick, ban or tempban!");
                            return false;
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "This player is not online!");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Invalid usage! Use /punish <player name> <kick/ban/tempban>.");
            }
        } else {

        }
        return false;
    }
}
