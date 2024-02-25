package org.nikolchev98.simpleafk.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.nikolchev98.simpleafk.models.PlayerAFKDataContainer;
import org.nikolchev98.simpleafk.utils.AFKUtils;

import static org.nikolchev98.simpleafk.enums.Constants.*;

public class AfkCommand implements CommandExecutor {
    private final PlayerAFKDataContainer playerAFKDataContainer = PlayerAFKDataContainer.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player) && args.length == 0) {
            return true;

        } else if (args.length == 0) {
            Player player = (Player) sender;
            AFKUtils.triggerAFK(player);
            return true;

        } else if (args.length == 1) {
            String argument = args[0];

            //Shows a list of all AFK players
            if (argument.equalsIgnoreCase("all")) {
                sender.sendMessage(this.playerAFKDataContainer.getAllAFKFormatted());
                return true;

            } else {
                Player targetPlayer = Bukkit.getPlayer(argument);

                if (targetPlayer == null) {
                    sender.sendMessage(NO_SUCH_PLAYER);
                    return true;

                } else if (this.playerAFKDataContainer.isAFK(targetPlayer)) {
                    sender.sendMessage(String.format(String.format(IS_AFK_FORMAT, argument)));
                    return true;

                } else {
                    sender.sendMessage(String.format(String.format(IS_NOT_AFK, argument)));
                    return true;
                }
            }

        } else {
            sender.sendMessage(INVALID_COMMAND);
            return true;
        }
    }
}
