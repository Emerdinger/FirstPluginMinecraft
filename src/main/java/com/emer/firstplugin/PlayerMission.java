package com.emer.firstplugin;

import org.bukkit.boss.BossBar;

public class PlayerMission {
    private String playerName;
    private Float counterLogs;
    private BossBar bossBar;

    public PlayerMission(String playerName, Float counterLogs, BossBar bossBar) {
        this.playerName = playerName;
        this.counterLogs = counterLogs;
        this.bossBar = bossBar;
    }

    public String getPlayer() {
        return playerName;
    }

    public Float getCounterLogs() {
        return counterLogs;
    }

    public BossBar getBossBar() {
        return bossBar;
    }

    public void setPlayer(String playerName) {
        this.playerName = playerName;
    }

    public void setCounterLogs(Float counterLogs) {
        this.counterLogs = counterLogs;
    }

    public void setBossBar(BossBar bossBar) {
        this.bossBar = bossBar;
    }

}
