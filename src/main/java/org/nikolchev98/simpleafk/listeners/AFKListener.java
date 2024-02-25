package org.nikolchev98.simpleafk.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.nikolchev98.simpleafk.models.PlayerAFKDataContainer;
import org.nikolchev98.simpleafk.utils.AFKUtils;

public class AFKListener implements Listener {
    private final PlayerAFKDataContainer playerAFKDataContainer = PlayerAFKDataContainer.getInstance();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        AFKUtils.addPlayer(e.getPlayer());
        e.getPlayer().sendMessage(this.playerAFKDataContainer.getAllAFKFormatted());
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