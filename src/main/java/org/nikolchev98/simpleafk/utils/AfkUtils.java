package org.nikolchev98.simpleafk.utils;

import org.bukkit.entity.Player;
import org.nikolchev98.simpleafk.SimpleAFK;
import org.nikolchev98.simpleafk.models.PlayerAFKData;
import org.nikolchev98.simpleafk.models.PlayerAFKDataContainer;

import java.util.Set;

import static org.nikolchev98.simpleafk.enums.Constants.*;

public class AFKUtils {
    protected static final PlayerAFKDataContainer playerAFKDataContainer = PlayerAFKDataContainer.getInstance();

    public static void addPlayer(Player player) {
        PlayerAFKData currentPlayerData = new PlayerAFKData(player.getName(), System.currentTimeMillis(), false);
        playerAFKDataContainer.addData(currentPlayerData);
    }

    public static void removePlayer(Player player) {
        playerAFKDataContainer.removeData(player);
    }

    public static void registerPlayerActivity(Player player) {
        if (playerAFKDataContainer.isAFK(player)) {
            playerAFKDataContainer.setAFK(false, player);
            broadcastNotAFK(player);
        }
        playerAFKDataContainer.setTimer(System.currentTimeMillis(), player);
    }

    public static void triggerAFK(Player player) {
        if (!playerAFKDataContainer.isAFK(player)) {
            broadcastIsAFK(player);
        } else {
            broadcastNotAFK(player);
        }
        playerAFKDataContainer.switchAFK(player);
    }

    public static void broadcastIsAFK(Player player) {
        for (Player currentPlayer : getOnlinePlayersWithEnabledMessagesExcept(player)) {
            currentPlayer.sendMessage(String.format(IS_NOW_AFK_FORMAT, player.getName()));
        }
        player.sendMessage(YOU_ARE_AFK);
    }

    public static void broadcastNotAFK(Player player) {
        for (Player currentPlayer : getOnlinePlayersWithEnabledMessagesExcept(player)) {
            currentPlayer.sendMessage(String.format(NO_LONGER_AFK_FORMAT, player.getName()));
        }
        player.sendMessage(YOU_ARE_NOT_AFK);
    }

    private static Set<Player> getOnlinePlayersWithEnabledMessagesExcept(Player player) {
        Set<Player> onlinePlayersWithEnabledMessages = SimpleAFK.getInstance().getAfkDatabase().getOnlinePlayersWithEnabledMessages();
        onlinePlayersWithEnabledMessages.remove(player);
        return onlinePlayersWithEnabledMessages;
    }
}