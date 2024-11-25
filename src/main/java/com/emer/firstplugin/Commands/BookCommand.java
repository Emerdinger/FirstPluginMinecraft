package com.emer.firstplugin.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class BookCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player player) {
            ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
            BookMeta bookMeta = (BookMeta) book.getItemMeta();
            bookMeta.setAuthor(ChatColor.AQUA + "Emer");
            bookMeta.setTitle(ChatColor.GREEN + "Rules");
            bookMeta.addPage(
                    ChatColor.RED + "The Rules!" +
                            "\n\n" + ChatColor.GREEN + "1. Don't thief"
            );
            book.setItemMeta(bookMeta);
            player.getInventory().addItem(book);
        }
        return false;
    }
}
