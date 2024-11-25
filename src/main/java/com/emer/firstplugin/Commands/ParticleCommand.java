package com.emer.firstplugin.Commands;

import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ParticleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            System.out.println("PARTICLE");
            player.sendMessage("You spawn a particle");
            player.spawnParticle(Particle.DRIPPING_LAVA, player.getLocation(), 5);
        }
        return false;
    }
}
