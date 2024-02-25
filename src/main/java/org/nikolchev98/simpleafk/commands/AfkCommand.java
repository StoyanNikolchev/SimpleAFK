package org.nikolchev98.simpleafk.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static org.nikolchev98.simpleafk.enums.Constants.*;
import static org.nikolchev98.simpleafk.utils.AfkUtils.*;

public class AfkCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        //Prevents the console from AFK-ing itself.
        if (!(sender instanceof Player) && args.length == 0) {
            return true;
        }

        //Triggers AFK for the sender
        if (args.length == 0) {
            Player player = (Player) sender;
            triggerAFK(player);
            return true;
        }

        if (args.length == 1) {
            String argument = args[0];

            //Shows the sender all AFK players.
            if (argument.equalsIgnoreCase("all")) {
                sender.sendMessage(getAllAFKPlayersFormatted());
                return true;
            }

            Player targetPlayer = Bukkit.getPlayer(argument);
            if (targetPlayer == null) {
                sender.sendMessage("No such player online!");
                return true;
            }

            if (isAFK(targetPlayer)) {
                sender.sendMessage(String.format(IS_AFK_FORMAT, argument));
                return true;
            }

            sender.sendMessage(String.format(IS_NOT_AFK, argument));
            return true;
        }

        //Invalid command if the arguments > 1
        sender.sendMessage("Invalid command structure.");
        return true;
    }


}
