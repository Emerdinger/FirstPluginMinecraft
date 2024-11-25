package com.emer.firstplugin.Tabs;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PunishTab implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        if (strings.length == 1) {
            List<String> onlinePlayers = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                onlinePlayers.add(player.getDisplayName());
            }
            return StringUtil.copyPartialMatches(strings[0],
                    onlinePlayers, new ArrayList<>());
        } else if (strings.length == 2) {
            return StringUtil.copyPartialMatches(strings[1],
                    Arrays.asList("kick", "ban", "tempban"), new ArrayList<>());
        }
        return new ArrayList<>();
    }
}
