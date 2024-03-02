package org.nikolchev98.simpleafk.models;

import org.bukkit.entity.Player;
import org.nikolchev98.simpleafk.enums.Constants;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.nikolchev98.simpleafk.enums.Constants.LIST_OF_AFK_FORMAT;

public class PlayerAFKDataContainer {
    private final Set<PlayerAFKData> playerData;
    private static PlayerAFKDataContainer instance;

    private PlayerAFKDataContainer() {
        this.playerData = new HashSet<>();
    }

    public static PlayerAFKDataContainer getInstance() {
        if (instance == null) {
            instance = new PlayerAFKDataContainer();
        }
        return instance;
    }

    public void addData(PlayerAFKData data) {
        this.playerData.add(data);
    }

    public void removeData(Player player) {
        this.playerData.removeIf(currentPlayerData -> player.getName().equals(currentPlayerData.getPlayerName()));
    }

    public PlayerAFKData getPlayerData(Player player) {
        return this.playerData.stream()
                .filter(data -> data.getPlayerName().equals(player.getName()))
                .findFirst()
                .orElse(null);
    }

    public boolean isAFK(Player player) {
        PlayerAFKData currentPlayerData = getPlayerData(player);
        if (currentPlayerData != null) {
            return currentPlayerData.getAFK();
        }
        return false;
    }

    public void setAFK(boolean state, Player player) {
        this.getPlayerData(player).setAFK(state);
    }

    public void switchAFK(Player player) {
        PlayerAFKData currentPlayerData = this.getPlayerData(player);

        if (currentPlayerData.getAFK()) {
            currentPlayerData.setAFK(false);
            currentPlayerData.setTime(System.currentTimeMillis());

        } else {
            currentPlayerData.setAFK(true);
            currentPlayerData.setTime(System.currentTimeMillis() + Constants.AFK_TRIGGER_MILLISECONDS);
        }

    }

    public void setTimer(long milliseconds, Player player) {
        this.getPlayerData(player).setTime(milliseconds);
    }

    public boolean isEmpty() {
        return getAllAFKFormatted().equals(String.format(LIST_OF_AFK_FORMAT, ""));
    }

    public String getAllAFKFormatted() {
        return String.format(LIST_OF_AFK_FORMAT,
                this.playerData.stream()
                        .filter(PlayerAFKData::getAFK)
                        .map(PlayerAFKData::getPlayerName)
                        .collect(Collectors.joining(", ")));
    }
}
