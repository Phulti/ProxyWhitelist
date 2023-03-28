package de.philippulti.proxywhitelist.utils;

import de.philippulti.proxywhitelist.ProxyWhitelistPlugin;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class WhitelistUtil {

  private final String prefix;

  private final ProxyWhitelistPlugin plugin;

  public void createWhitelist(final CommandSender sender, final String name) {
    if (whitelistExists(name)) {
      sendWhitelistAlreadyExistsMessage(sender, name);
      return;
    }

    if (!plugin.getProxy().getServers().keySet().stream().anyMatch(s -> s.equalsIgnoreCase(name))) {
      sendServerDoesNotExistMessage(sender, name);
      return;
    }

    plugin.getConfigUtil().getConfiguration().set("proxywhitelist." + name.toLowerCase() + ".active", false);
    plugin.getConfigUtil().getConfiguration().set("proxywhitelist." + name.toLowerCase() + ".user", new ArrayList<>());
  }

  public void deleteWhitelist(final CommandSender sender, final String name) {
    if (!whitelistExists(name)) {
      sendWhitelistDoesNotExistMessage(sender, name);
      return;
    }

    plugin.getConfigUtil().getConfiguration().set("proxywhitelist." + name.toLowerCase() + ".active", null);
    plugin.getConfigUtil().getConfiguration().set("proxywhitelist." + name.toLowerCase() + ".user", null);
    plugin.getConfigUtil().getConfiguration().set("proxywhitelist." + name.toLowerCase(), null);
  }

  public void listWhitelists(final CommandSender sender) {
    StringBuilder whitelistStringBuilder = new StringBuilder(prefix);
    plugin.getConfigUtil().getConfiguration().getSection("proxywhitelist").getKeys().stream().forEach(s -> whitelistStringBuilder.append("§c" + s + "§7,"));
  }

  public void enableWhitelist(final CommandSender sender, final String name) {
    if (!whitelistExists(name)) {
      sendWhitelistDoesNotExistMessage(sender, name);
      return;
    }

    plugin.getConfigUtil().getConfiguration().set("proxywhitelist." + name.toLowerCase() + ".active", true);
  }

  public void disableWhitelist(final CommandSender sender, final String name) {
    if (!whitelistExists(name)) {
      sendWhitelistDoesNotExistMessage(sender, name);
      return;
    }

    plugin.getConfigUtil().getConfiguration().set("proxywhitelist." + name.toLowerCase() + ".active", false);
  }

  public void addPlayerToWhitelist(final CommandSender sender, final String whitelistName, final String playerName) {
    if (!whitelistExists(whitelistName)) {
      sendWhitelistDoesNotExistMessage(sender, whitelistName);
      return;
    }

    PlayerData playerData = PlayerDataUtil.getPlayerData(playerName);
    if (playerData == null) {
      sendPlayerDoesNotExistMessage(sender, playerName);
      return;
    }

    List<String> playerNames = plugin.getConfigUtil().getConfiguration().getStringList("proxywhitelist." + whitelistName.toLowerCase() + ".user");
    if (!playerNames.contains(playerName)) {
      playerNames.add(playerName);
      plugin.getConfigUtil().getConfiguration().set("proxywhitelist." + whitelistName.toLowerCase() + ".user", playerNames);
    }
  }

  public void removePlayerFromWhitelist(final CommandSender sender, final String whitelistName, final String playerName) {
    if (!whitelistExists(whitelistName)) {
      sendWhitelistDoesNotExistMessage(sender, whitelistName);
      return;
    }

    PlayerData playerData = PlayerDataUtil.getPlayerData(playerName);
    if (playerData == null) {
      sendPlayerDoesNotExistMessage(sender, playerName);
      return;
    }

    List<String> playerNames = plugin.getConfigUtil().getConfiguration().getStringList("proxywhitelist." + whitelistName.toLowerCase() + ".user");
    if (playerNames.contains(playerName)) {
      playerNames.remove(playerName);
      plugin.getConfigUtil().getConfiguration().set("proxywhitelist." + whitelistName.toLowerCase() + ".user", playerNames);
    }
  }

  public boolean isActive(final String name) {
    return plugin.getConfigUtil().getConfiguration().getBoolean("proxywhitelist." + name.toLowerCase() + ".active");
  }

  public boolean whitelistExists(final String name) {
    if (plugin.getConfigUtil().getConfiguration().getKeys().contains(name)) {
      return true;
    }
    return false;
  }

  private void sendWhitelistDoesNotExistMessage(final CommandSender sender, final String whitelistName) {
    BaseComponent[] message = new ComponentBuilder(prefix).append("The whitelist §c" + whitelistName + " §7does not exist!").color(ChatColor.GRAY).create();
    sender.sendMessage(message);
  }

  private void sendWhitelistAlreadyExistsMessage(final CommandSender sender, final String whitelistName) {
    BaseComponent[] message = new ComponentBuilder(prefix).append("The whitelist §c" + whitelistName + " §7does already exist!").color(ChatColor.GRAY).create();
    sender.sendMessage(message);
  }

  private void sendPlayerDoesNotExistMessage(final CommandSender sender, final String playerName) {
    BaseComponent[] message = new ComponentBuilder(prefix).append("The player §c" + playerName + " §7does not exist!").color(ChatColor.GRAY).create();
    sender.sendMessage(message);
  }

  private void sendServerDoesNotExistMessage(final CommandSender sender, final String playerName) {
    BaseComponent[] message = new ComponentBuilder(prefix).append("The server §c" + playerName + " §7does not exist!").color(ChatColor.GRAY).create();
    sender.sendMessage(message);
  }

}
