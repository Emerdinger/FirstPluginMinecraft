package com.emer.firstplugin.Commands;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BoostCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                AttributeModifier modifier = new AttributeModifier(NamespacedKey.fromString("generic.attack_damage"), 100,
                        AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND);
                AttributeModifier luckyModifier = new AttributeModifier(NamespacedKey.fromString("generic.luck"), 1000,
                        AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND);
                ItemStack item = player.getInventory().getItemInMainHand();
                ItemMeta itemMeta = item.getItemMeta();
                Multimap<Attribute, AttributeModifier> attributeAttributeModifierMultimap = ArrayListMultimap.create();
                attributeAttributeModifierMultimap.put(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
                attributeAttributeModifierMultimap.put(Attribute.GENERIC_LUCK, luckyModifier);
                itemMeta.addEnchant(Enchantment.FORTUNE, 1000, true);
                itemMeta.addEnchant(Enchantment.LOOTING, 255, true);
                itemMeta.setAttributeModifiers(attributeAttributeModifierMultimap);
                item.setItemMeta(itemMeta);
                player.sendMessage("Your item has been given 100 attack damage!");
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40);
            }

        }
        return false;
    }
}
