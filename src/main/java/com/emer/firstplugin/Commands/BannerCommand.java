package com.emer.firstplugin.Commands;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.ArrayList;
import java.util.List;

public class BannerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            ItemStack banner = new ItemStack(Material.WHITE_BANNER);
            BannerMeta bannerMeta = (BannerMeta) banner.getItemMeta();
            List<Pattern> patterns = new ArrayList<>();
            patterns.add(new Pattern(DyeColor.RED, PatternType.STRIPE_RIGHT));
            bannerMeta.setPatterns(patterns);
            banner.setItemMeta(bannerMeta);
            player.getInventory().addItem(banner);
        }
        return false;
    }
}
