package com.emer.firstplugin.Zones;

import org.bukkit.Location;

public class SnowBallZone {
    private final Location min;
    private final Location max;

    public SnowBallZone(Location min, Location max) {
        this.min = min;
        this.max = max;
    }

    public boolean isWithin(Location location){
        return location.getX() >= min.getX() && location.getX() <= max.getX()
                && location.getY() >= min.getY() && location.getY() <= max.getY()
                && location.getZ() >= min.getZ() && location.getZ() <= max.getZ();
    }
}
