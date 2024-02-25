package org.nikolchev98.simpleafk;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.nikolchev98.simpleafk.commands.AfkCommand;
import org.nikolchev98.simpleafk.listeners.AFKListener;

import java.util.HashSet;
import java.util.Set;

public final class SimpleAFK extends JavaPlugin {
    //public static Set<Player> PLAYERS_CURRENTLY_AFK = new HashSet<>();
    @Override
    public void onEnable() {
        System.out.println("SimpleAFK is running!");

        getCommand("afk").setExecutor(new AfkCommand());
        getServer().getPluginManager().registerEvents(new AFKListener(), this);
    }

    @Override
    public void onDisable() {
        System.out.println("SimpleAFK is disabled.");
    }
}
