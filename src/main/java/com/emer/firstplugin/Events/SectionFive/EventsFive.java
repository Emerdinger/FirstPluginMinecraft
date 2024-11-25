package com.emer.firstplugin.Events.SectionFive;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Random;

public class EventsFive implements Listener {

    @EventHandler
    public void onClickMenuEvent(InventoryClickEvent inventoryClickEvent) {
        if (ChatColor.translateAlternateColorCodes(
                '&', inventoryClickEvent.getView().getTitle()).equals(ChatColor.GREEN + "Tool Menu")
        && inventoryClickEvent.getCurrentItem() != null) {
            Player player = (Player) inventoryClickEvent.getWhoClicked();
            inventoryClickEvent.setCancelled(true);
            switch (inventoryClickEvent.getRawSlot()) {
                case 0:
                    break;
                case 20:
                    Random random = new Random();
                    Player playerToTp = (Player) Bukkit.getOnlinePlayers().toArray()[random.nextInt(Bukkit.getOnlinePlayers().size())];
                    player.teleport(playerToTp);
                    if (player.getName().equals(playerToTp.getName())) {
                        player.sendMessage(ChatColor.RED + "No players to tp");
                    } else {
                        player.sendMessage(ChatColor.BLUE + "You teleported to " + playerToTp.getName() + "!");
                    }
                    break;
                case 22:
                    player.setHealth(0);
                    player.sendMessage(ChatColor.RED + "You killed yourself!");
                    break;
                case 24:
                    player.closeInventory();
                    player.getInventory().clear();
                    player.sendMessage(ChatColor.AQUA + "Inventory clear");
                    break;
                default:
                    return;
            }
            player.closeInventory();
        }
    }
}
