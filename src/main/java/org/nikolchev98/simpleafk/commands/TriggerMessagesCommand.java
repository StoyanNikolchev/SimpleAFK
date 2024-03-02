package org.nikolchev98.simpleafk.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.nikolchev98.simpleafk.SimpleAFK;

import java.sql.SQLException;

import static org.nikolchev98.simpleafk.enums.Constants.*;

public class TriggerMessagesCommand implements CommandExecutor {
    private final SimpleAFK simpleAFK;

    public TriggerMessagesCommand(SimpleAFK simpleAFK) {
        this.simpleAFK = simpleAFK;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(SENDER_MUST_BE_PLAYER);
            return true;
        }

        Player player = (Player) commandSender;
        try {
            if (this.simpleAFK.getAfkDatabase().triggerPlayerMessages(player)) {
                player.sendMessage(String.format(ENABLED_MESSAGES_FORMAT, ChatColor.GREEN));
            } else {
                player.sendMessage(String.format(DISABLED_MESSAGES_FORMAT, ChatColor.RED));
            }
            return true;

        } catch (SQLException exception) {
            player.sendMessage(exception.getMessage());
            return true;
        }
    }
}
