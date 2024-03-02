package org.nikolchev98.simpleafk.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.nikolchev98.simpleafk.SimpleAFK;
import org.nikolchev98.simpleafk.models.PlayerAFKDataContainer;
import org.nikolchev98.simpleafk.utils.AFKUtils;

import java.sql.SQLException;

import static org.nikolchev98.simpleafk.enums.Constants.FAILED_TO_ADD_PLAYER;

public class AFKListener implements Listener {
    private final SimpleAFK simpleAFK;
    private final PlayerAFKDataContainer playerAFKDataContainer = PlayerAFKDataContainer.getInstance();

    public AFKListener(SimpleAFK simpleAFK) {
        this.simpleAFK = simpleAFK;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        try {
            this.simpleAFK.getAfkDatabase().addPlayer(e.getPlayer());
        } catch (SQLException exception) {
            System.out.println(ChatColor.RED + FAILED_TO_ADD_PLAYER);
        }

        AFKUtils.addPlayer(e.getPlayer());

        if (!playerAFKDataContainer.isEmpty()) {
            e.getPlayer().sendMessage(this.playerAFKDataContainer.getAllAFKFormatted());
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        AFKUtils.removePlayer(e.getPlayer());
    }

    @EventHandler
    public void onPlayerMovement(PlayerMoveEvent e) {
        AFKUtils.registerPlayerActivity(e.getPlayer());
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent e) {
        AFKUtils.registerPlayerActivity(e.getPlayer());
    }
}