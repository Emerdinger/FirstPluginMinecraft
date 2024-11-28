package com.emer.firstplugin.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
            skullMeta.setOwningPlayer(player);
            skull.setItemMeta(skullMeta);
            player.getInventory().addItem(skull);

            if (strings.length == 1) {
                ItemStack randomSkull = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta skullMetaRandom = (SkullMeta) randomSkull.getItemMeta();
                skullMetaRandom.setOwningPlayer(Bukkit.getOfflinePlayer(strings[0]));
                randomSkull.setItemMeta(skullMetaRandom);
                player.getInventory().addItem(randomSkull);
            }
        }

        return false;
    }
}
