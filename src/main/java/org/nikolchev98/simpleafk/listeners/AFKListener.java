package org.nikolchev98.simpleafk.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static org.nikolchev98.simpleafk.utils.AfkUtils.*;

public class AFKListener implements Listener {

    //Automatically sends a player a list of all the AFK players upon joining the server.
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        addPlayerToMap(e.getPlayer());
        e.getPlayer().sendMessage(getAllAFKPlayersFormatted());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        removePlayerFromMap(e.getPlayer());
    }

    @EventHandler
    public void onPlayerMovement(PlayerMoveEvent e) {
        registerPlayerActivity(e.getPlayer());
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent e) {
        registerPlayerActivity(e.getPlayer());
    }
}