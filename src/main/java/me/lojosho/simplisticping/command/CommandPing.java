package me.lojosho.simplisticping.command;

import me.lojosho.simplisticping.Main;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.Template;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommandPing implements CommandExecutor {

    Player player;
    List<Template> templates;

    private final Main plugin;

    public CommandPing(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player) && args.length == 0) {
            sender.sendMessage(MiniMessage.get().parse(plugin.getConfig().getString("Messages.Console")));
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("simplisticping.reload") || sender.isOp() || !(sender instanceof Player)) {
                plugin.reloadConfig();
                sender.sendMessage(MiniMessage.get().parse(plugin.getConfig().getString("Messages.Reload")));
                return true;
            }
        }

        /*
        Set's up the player which we will be getting their ping from.
        We first default it to the sender, then if there is an argument, use that instead.
         */

        player = Bukkit.getPlayer(sender.getName());
        if (args.length == 1) {
            if (Bukkit.getPlayer(args[0]) != null) {
                player = Bukkit.getPlayer(args[0]);
            } else {
                sender.sendMessage(MiniMessage.get().parse(plugin.getConfig().getString("Messages.UnknownPlayer")));
                return true;
            }
        }

        templates = List.of(Template.of("name", sender.getName()),
                Template.of("ping", Integer.toString(player.getPing())),
                Template.of("Player", player.getName()));

        /*
        Deals with the actual ping message.
         */
        if (args.length == 1) {
            if (sender.hasPermission("simplisticping.other") || sender.isOp() || !(sender instanceof Player)) {
                if (Bukkit.getPlayer(args[0]) != null) {
                    sender.sendMessage(MiniMessage.get().parse(plugin.getConfig().getString("Messages.OtherPlayer"), templates));
                    return true;
                }
            } else {
                sender.sendMessage(MiniMessage.get().parse(plugin.getConfig().getString("Messages.NoPermission"), templates));
                return true;
            }
        } else {
            sender.sendMessage(MiniMessage.get().parse(plugin.getConfig().getString("Messages.Player"), templates));
            return true;
        }
        return false;
    }
}