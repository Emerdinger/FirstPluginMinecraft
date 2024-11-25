package com.emer.firstplugin.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player ){
            Inventory inventory = Bukkit.createInventory(player, 45, ChatColor.GREEN + "Tool Menu");

            setItemInInventory(inventory, Material.BARRIER, ChatColor.RED + "Close", null, 0);
            setItemInInventory(inventory, Material.ENDER_PEARL, ChatColor.BLUE + "Random Teleport",
                    Arrays.asList(ChatColor.GRAY + "Teleports to a ramdom player"), 20);
            setItemInInventory(inventory, Material.DIAMOND_SWORD, ChatColor.BLUE + "Kill yourself",
                    Arrays.asList(ChatColor.GRAY + "Kill yourself"), 22);
            setItemInInventory(inventory, Material.BUCKET, ChatColor.BLUE + "Clear Inventory",
                    Arrays.asList(ChatColor.GRAY + "Clear your invetory"), 24);

            List<Integer> frameSlots = Arrays.asList(1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,37,38,39,40,41,42,43,44);
            setMultiple(inventory, Material.LIME_STAINED_GLASS_PANE, "Frames",frameSlots);

            player.openInventory(inventory);
        }
        return false;
    }

    private void setItemInInventory(Inventory inventory, Material material, String displayName, List<String> lore, int slot) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(slot, itemStack);
    }

    private void setMultiple(Inventory inventory, Material material, String displayName, List<Integer> slots) {
        ItemStack frame = new ItemStack(material);
        ItemMeta frameMeta = frame.getItemMeta();
        frame.setItemMeta(frameMeta);
        frameMeta.setDisplayName(displayName);
        for (int i : slots) {
            inventory.setItem(i, frame);
        }
    }
}
