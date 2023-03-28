package de.philippulti.proxywhitelist;

import de.philippulti.proxywhitelist.commands.WhitelistCommand;
import de.philippulti.proxywhitelist.listener.WhitelistListener;
import de.philippulti.proxywhitelist.utils.ConfigUtil;
import de.philippulti.proxywhitelist.utils.WhitelistUtil;
import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class ProxyWhitelistPlugin extends Plugin {

  @Getter
  private static final String prefix = "§7[§cProxyWhitelist§7] ";
  private static WhitelistUtil whitelistUtil;
  @Getter
  private ConfigUtil configUtil;

  @Override
  public void onEnable() {
    registerCommands();
    registerListener();
    configUtil = new ConfigUtil(this);
    whitelistUtil = new WhitelistUtil(prefix, this);
    configUtil.loadConfigs();
  }

  private void registerCommands() {
    ProxyServer.getInstance().getPluginManager().registerCommand(this, new WhitelistCommand("proxywhitelist", this));
  }

  private void registerListener() {
    ProxyServer.getInstance().getPluginManager().registerListener(this, new WhitelistListener(this, whitelistUtil));
  }

  public String getPrefix() {
    return getPrefix();
  }

}
