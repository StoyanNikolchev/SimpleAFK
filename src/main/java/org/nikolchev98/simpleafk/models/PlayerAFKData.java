package org.nikolchev98.simpleafk.models;

public class PlayerAFKData {
    private final String playerName;
    private Long time;
    private Boolean isAFK;

    public PlayerAFKData(String player, Long time, Boolean isAFK) {
        this.playerName = player;
        this.time = time;
        this.isAFK = isAFK;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public Long getTime() {
        return this.time;
    }

    public PlayerAFKData setTime(Long time) {
        this.time = time;
        return this;
    }

    public Boolean getAFK() {
        return this.isAFK;
    }

    public PlayerAFKData setAFK(Boolean AFK) {
        this.isAFK = AFK;
        return this;
    }
}
