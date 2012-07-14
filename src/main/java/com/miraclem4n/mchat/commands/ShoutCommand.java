package com.miraclem4n.mchat.commands;

import com.miraclem4n.mchat.api.Parser;
import com.miraclem4n.mchat.types.config.LocaleType;
import com.miraclem4n.mchat.util.MessageUtil;
import com.miraclem4n.mchat.util.MiscUtil;
import com.miraclem4n.mchat.MChat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShoutCommand implements CommandExecutor {
    MChat plugin;

    public ShoutCommand(MChat instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("mchatshout")
                || !MiscUtil.hasCommandPerm(sender, "mchat.shout"))
            return true;

        String message = "";

        for (String arg : args)
            message += " " + arg;

        message = message.trim();

        if (!(sender instanceof Player)) {
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "say " + message);
            return true;
        }

        if (message.length() < 1) {
            MessageUtil.sendMessage(sender, LocaleType.MESSAGE_SHOUT_NO_INPUT.getVal());
            return true;
        }

        Player player = (Player) sender;

        MChat.isShouting.put(sender.getName(), true);

        plugin.getServer().broadcastMessage(Parser.parseChatMessage(player.getName(), player.getWorld().getName(), message));

        MChat.isShouting.put(sender.getName(), false);

        return true;
    }
}