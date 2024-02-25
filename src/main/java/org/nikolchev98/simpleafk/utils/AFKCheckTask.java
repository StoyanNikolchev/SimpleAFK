package org.nikolchev98.simpleafk.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.nikolchev98.simpleafk.enums.Constants;
import org.nikolchev98.simpleafk.models.PlayerAFKData;
import org.nikolchev98.simpleafk.models.PlayerAFKDataContainer;

public class AFKCheckTask extends BukkitRunnable {
    final PlayerAFKDataContainer playerAFKDataContainer = PlayerAFKDataContainer.getInstance();

    public void run() {
        Long currentTime = System.currentTimeMillis();

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            PlayerAFKData currentPlayerData = this.playerAFKDataContainer.getPlayerData(onlinePlayer);

            if (currentPlayerData.getAFK()) {
                continue;
            }

            if (currentTime - currentPlayerData.getTime() >= Constants.AFK_TRIGGER_MILLISECONDS) {
                this.playerAFKDataContainer.switchAFK(onlinePlayer);
                AFKUtils.broadcastIsAFK(onlinePlayer);
            }
        }

    }
}
