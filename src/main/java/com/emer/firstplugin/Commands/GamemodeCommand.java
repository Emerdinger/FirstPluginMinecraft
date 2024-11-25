package com.emer.firstplugin.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class GamemodeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player player) {
            if (!player.isOp()) {
                player.sendMessage(ChatColor.RED + "You need to be operator to execute this command");
                return false;
            }
            changeGamemodePlayer(player, strings);
        } else {
            changeGamemodeConsole(strings);
        }
        return false;
    }

    private void changeGamemodeConsole(String[] strings) {
        if (strings.length == 2) {
            Player player = Bukkit.getServer().getPlayer(strings[1]);
            if (player == null) {
                System.out.println("Player not found");
                return;
            };
            changeGamemode(player, strings);
        } else if (strings.length < 2) {
            System.out.println("You need enter just two arguments: c, s, sp + name");
        } else {
            System.out.println("You need enter just two arguments: c, s, sp + name");
        }
    }

    private void changeGamemodePlayer(Player player, String[] strings) {
        if (strings.length == 1) {

            changeGamemode(player, strings);

        } else if (strings.length < 1) {
            player.sendMessage(ChatColor.RED + "Enter one argument: c, s, sp");
        } else {
            player.sendMessage(ChatColor.RED + "You need enter just one argument: c, s, sp" );
        }
    }

    private void changeGamemode(Player player, String[] strings) {
        Set<String> validLetters = Set.of("c", "s", "sp");

        if (!validLetters.contains(strings[0])){
            player.sendMessage(ChatColor.RED + "Argument " + strings[0] + " is not valid, use: c, s, sp");
        }

        if (strings[0].equals("c")){
            player.setGameMode(GameMode.CREATIVE);
        };
        if (strings[0].equals("s")){
            player.setGameMode(GameMode.SURVIVAL);
        };
        if (strings[0].equals("sp")){
            player.setGameMode(GameMode.SPECTATOR);
        };
    }
}
