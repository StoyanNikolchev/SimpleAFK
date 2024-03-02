package org.nikolchev98.simpleafk;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.nikolchev98.simpleafk.commands.AfkCommand;
import org.nikolchev98.simpleafk.commands.TriggerMessagesCommand;
import org.nikolchev98.simpleafk.database.AFKDatabase;
import org.nikolchev98.simpleafk.listeners.AFKListener;
import org.nikolchev98.simpleafk.utils.AFKCheckTask;

import java.sql.SQLException;

public final class SimpleAFK extends JavaPlugin {
    private AFKDatabase afkDatabase;
    public static SimpleAFK instance;

    public AFKDatabase getAfkDatabase() {
        return this.afkDatabase;
    }

    public void onEnable() {
        instance = this;

        try {
            //Creates plugin directory if it doesn't exist already
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }

            //Sets up SQLite database
            this.afkDatabase = new AFKDatabase(getDataFolder().getAbsolutePath() + "/afk.db");
        } catch (SQLException exception) {
            System.out.println("Failed to connect to SQLite database!");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        System.out.println("SimpleAFK is enabled!");
        new AFKCheckTask().runTaskTimer(this, 20L, 1L);
        this.getCommand("afk").setExecutor(new AfkCommand());
        this.getCommand("trigger_afk_messages").setExecutor(new TriggerMessagesCommand(this));
        this.getServer().getPluginManager().registerEvents(new AFKListener(this), this);
    }

    public void onDisable() {
        try {
            afkDatabase.closeConnection();
        } catch (SQLException exception) {
            System.out.println("Failed to close connection to SQLite database.");
        }

        System.out.println("SimpleAFK is disabled.");
    }

    public static SimpleAFK getInstance() {
        return instance;
    }
}
