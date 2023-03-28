package de.philippulti.proxywhitelist.commands;

import de.philippulti.proxywhitelist.ProxyWhitelistPlugin;
import de.philippulti.proxywhitelist.utils.WhitelistUtil;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class WhitelistCommand extends Command implements TabExecutor {

  private ProxyWhitelistPlugin plugin;
  private String prefix;
  @Getter
  private WhitelistUtil whitelistUtil;

  public WhitelistCommand(final String name, final ProxyWhitelistPlugin plugin) {
    super(name);
    this.plugin = plugin;
    this.prefix = plugin.getPrefix();
    whitelistUtil = new WhitelistUtil(plugin.getPrefix(), plugin);
  }

  @Override
  public void execute(final CommandSender sender, final String[] args) {
    if (!sender.hasPermission("proxywhitelist.command.*")) {
      sender.sendMessage(new TextComponent(prefix + "§cYou are not allowed to use this command!"));
      return;
    }

    if (args.length == 0) {
      handleHelp(sender);

    } else if (args.length == 1) {
      if (args[0].equalsIgnoreCase("list")) {
        whitelistUtil.listWhitelists(sender);
      } else {
        sender.sendMessage(new TextComponent(prefix + "§7Unknown command! Use §c/proxywhitelist §7to show available commands!"));
      }

    } else if (args.length == 2) {
      final String whitelistName = args[1];
      if (args[0].equalsIgnoreCase("info")) {
        //TODO: add info method to WhitelistUtil.class
      } else if (args[0].equalsIgnoreCase("create")) {
        whitelistUtil.createWhitelist(sender, whitelistName);
      } else if (args[0].equalsIgnoreCase("delete")) {
        whitelistUtil.deleteWhitelist(sender, whitelistName);
      } else if (args[0].equalsIgnoreCase("toggle")) {
        if (whitelistUtil.whitelistExists(whitelistName)) {
          if (whitelistUtil.isActive(whitelistName)) {
            whitelistUtil.disableWhitelist(sender, whitelistName);
          } else {
            whitelistUtil.enableWhitelist(sender, whitelistName);
          }
        }
      } else if (args[0].equalsIgnoreCase("clear")) {
        //TODO: add clear method to WhitelistUtil.class
      } else {
        sender.sendMessage(new TextComponent(prefix + "§7Unknown command! Use §c/proxywhitelist §7to show available commands!"));
      }

    } else if (args.length == 3) {
      final String whitelistName = args[1];
      final String playerName = args[2];
      if (args[0].equalsIgnoreCase("adduser")) {
        whitelistUtil.addPlayerToWhitelist(sender, whitelistName, playerName);
      } else if (args[0].equalsIgnoreCase("deluser")) {
        whitelistUtil.removePlayerFromWhitelist(sender, whitelistName, playerName);
      } else {
        sender.sendMessage(new TextComponent(prefix + "§7Unknown command! Use §c/proxywhitelist §7to show available commands!"));
      }

    } else {
      sender.sendMessage(new TextComponent(prefix + "§7Unknown command! Use §c/proxywhitelist §7to show available commands!"));
    }


  }

  private void handleHelp(final CommandSender sender) {
    BaseComponent[] components = new ComponentBuilder(prefix)
        .append("§c/proxywhitelist list §8- §7list all available whitelists").color(ChatColor.GRAY)
        .append("§c/proxywhitelist info <whitelist> §8- §7list all informations about a whitelist").color(ChatColor.GRAY)
        .append("§c/proxywhitelist create <whitelist> §8- §7create a new whitelist").color(ChatColor.GRAY)
        .append("§c/proxywhitelist delete <whitelist> §8- §7delete an existing whitelist").color(ChatColor.GRAY)
        .append("§c/proxywhitelist toggle <whitelist> §8- §7toggle the status of a whitelist").color(ChatColor.GRAY)
        .append("§c/proxywhitelist clear <whitelist> §8- §7clear all users from a whitelist").color(ChatColor.GRAY)
        .append("§c/proxywhitelist adduser <whitelist> <username> §8- §7add a user to a whitelist").color(ChatColor.GRAY)
        .append("§c/proxywhitelist deluser <whitelist> <username> §8- §7remove a user from a whitelist").color(ChatColor.GRAY)
        .create();
  }

  @Override
  public Iterable<String> onTabComplete(final CommandSender sender, final String[] args) {
    return null;
  }
}
