package org.nikolchev98.simpleafk.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.stream.Collectors;

import static org.nikolchev98.simpleafk.enums.Constants.*;

public class AfkUtils {
    private static final HashMap<Player, Long> lastMovement = new HashMap<>();

    public static void addPlayerToMap(Player player) {
        lastMovement.put(player, System.currentTimeMillis());
    }

    public static void removePlayerFromMap(Player player) {
        lastMovement.remove(player);
    }

    public static void registerPlayerActivity(Player player) {
        if (isAFK(player)) {
            broadcastNotAFK(player);
        }
        lastMovement.replace(player, System.currentTimeMillis());
    }

    public static boolean isAFK(Player player) {
        Long lastMovementTime = lastMovement.get(player);
        Long currentTime = System.currentTimeMillis();
        return currentTime - lastMovementTime >= AFK_TRIGGER_MILLISECONDS;
    }

    public static void triggerAFK(Player player) {
        if (!isAFK(player)) {
            lastMovement.replace(player, AFK_TRIGGER_MILLISECONDS);
            broadcastIsAFK(player);
            return;
        }

        lastMovement.replace(player, System.currentTimeMillis());
        broadcastNotAFK(player);
    }

    public static String getAllAFKPlayersFormatted() {
        return String.format(CURRENTLY_AFK_FORMAT, lastMovement.keySet().stream()
                .filter(AfkUtils::isAFK)
                .map(Player::getName)
                .collect(Collectors.joining(", ")));
    }

    private static void broadcastIsAFK(Player player) {
        Bukkit.broadcastMessage(String.format(IS_NOW_AFK_FORMAT, player.getName()));
    }

    private static void broadcastNotAFK(Player player) {
        Bukkit.broadcastMessage(String.format(NO_LONGER_AFK_FORMAT, player.getName()));
    }
}
