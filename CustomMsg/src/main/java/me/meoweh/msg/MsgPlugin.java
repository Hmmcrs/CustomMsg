package me.meoweh.msg;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

// Simple custom /msg plugin //
// by Meoweh/Nyx/QwQ //
// used on LootvoidRM //

/* Note that this wont work on servers like minehut
that already have their own registered global /msg */

public final class MsgPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        registerMsgCommand();
        getLogger().info("MsgPlugin enabled");
    }

    private void registerMsgCommand() {
        PrivateMsgCommand cmd = new PrivateMsgCommand();
        getCommand("msg").setExecutor(cmd);
    }

    // Handles /msg <player> <message> //
    static final class PrivateMsgCommand implements CommandExecutor {

        @Override
        public boolean onCommand(CommandSender sender,
                                 Command command,
                                 String label,
                                 String[] args) {

            if (!(sender instanceof Player player)) {
                sender.sendMessage(ChatColor.RED + "Only players can use /" + label + ".");
                return true;
            }

            if (args.length < 2) {
                player.sendMessage(ChatColor.RED + "Usage: /" + label + " <player> <message>");
                return true;
            }

            Player targetPlayer = Bukkit.getPlayerExact(args[0]);
            if (targetPlayer == null || !targetPlayer.isOnline()) {
                player.sendMessage(ChatColor.RED + "That player is not online.");
                return true;
            }

            String msgText = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

            // This part decides what color the messages are //

            /* Currently its set so that "To, From, :, and the Message"
            are aqua and the player name is gray 
            obviously you can change these if youd like to */
            
            // you --> them
            sendFormattedMessage(
                    player,
                    ChatColor.AQUA + "To " + ChatColor.GRAY + targetPlayer.getName()
                            + ChatColor.AQUA + ": " + msgText
            );

            // them --> you
            sendFormattedMessage(
                    targetPlayer,
                    ChatColor.AQUA + "From " + ChatColor.GRAY + player.getName()
                            + ChatColor.AQUA + ": " + msgText
            );

            return true;
        }

        // tiny helper to change formatting
        private void sendFormattedMessage(Player p, String text) {
            p.sendMessage(text);
        }
    }
}
