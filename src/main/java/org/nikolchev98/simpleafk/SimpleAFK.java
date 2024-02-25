package org.nikolchev98.simpleafk;

import org.bukkit.plugin.java.JavaPlugin;
import org.nikolchev98.simpleafk.commands.AfkCommand;
import org.nikolchev98.simpleafk.listeners.AFKListener;
import org.nikolchev98.simpleafk.utils.AFKCheckTask;

public final class SimpleAFK extends JavaPlugin {

    public void onEnable() {
        System.out.println("SimpleAFK is enabled!");
        new AFKCheckTask().runTaskTimer(this, 20L, 1L);
        this.getCommand("afk").setExecutor(new AfkCommand());
        this.getServer().getPluginManager().registerEvents(new AFKListener(), this);
    }

    public void onDisable() {
        System.out.println("SimpleAFK is disabled.");
    }
}
