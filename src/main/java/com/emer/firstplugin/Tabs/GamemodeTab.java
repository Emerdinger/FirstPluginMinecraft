package com.emer.firstplugin.Tabs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GamemodeTab implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return StringUtil.copyPartialMatches(strings[0],
                    Arrays.asList("c", "s", "sp"), new ArrayList<>());
        }
        return new ArrayList<>();
    }
}
