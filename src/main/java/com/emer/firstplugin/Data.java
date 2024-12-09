package com.emer.firstplugin;

import java.util.Date;

public class Data {
    private String playerName;
    private String message;
    private boolean value;
    private Date date;

    public Data(String playerName, String message, boolean value, Date date) {
        this.playerName = playerName;
        this.message = message;
        this.value = value;
        this.date = date;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getMessage() {
        return message;
    }

    public boolean getValue() {
        return value;
    }

    public Date getDate() {
        return date;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
