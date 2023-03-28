package de.philippulti.proxywhitelist.listener;

import de.philippulti.proxywhitelist.ProxyWhitelistPlugin;
import de.philippulti.proxywhitelist.utils.WhitelistUtil;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

@RequiredArgsConstructor
public class WhitelistListener implements Listener {

  private final ProxyWhitelistPlugin plugin;
  private final WhitelistUtil whitelistUtil;

  @EventHandler
  public void onLogin(final LoginEvent event) {
    if (whitelistUtil.isActive("global")) {
      List<String> playerNames = plugin.getConfigUtil().getConfiguration().getStringList("proxywhitelist.global.user");
      if (!playerNames.contains(event.getConnection().getUniqueId().toString())) {
        event.getConnection().disconnect(new TextComponent(plugin.getPrefix() + "§cYou are not on the whitelist!"));
      }
    }
  }

  @EventHandler
  public void onServerConnectEvent(final ServerConnectEvent event) {
    if (!event.getReason().equals(ServerConnectEvent.Reason.JOIN_PROXY)) {
      if (whitelistUtil.isActive(event.getTarget().getName())) {
        List<String> playerNames = plugin.getConfigUtil().getConfiguration().getStringList("proxywhitelist." + event.getTarget().getName() + ".user");
        if (!playerNames.contains(event.getPlayer().getUniqueId().toString())) {
          event.setCancelled(true);
        }
      }
    }
  }

}
