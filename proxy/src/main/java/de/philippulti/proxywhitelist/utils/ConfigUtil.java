package de.philippulti.proxywhitelist.utils;

import de.philippulti.proxywhitelist.ProxyWhitelistPlugin;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;

@RequiredArgsConstructor
public class ConfigUtil {

  private final ProxyWhitelistPlugin plugin;
  @Getter
  private Configuration configuration;
  private File configurationFile;

  public void loadConfigs() {
    try {
      configurationFile = new File(plugin.getDataFolder(), "proxywhitelist.yml");
      if (!configurationFile.exists()) {
        configurationFile.getParentFile().mkdirs();
        configurationFile.createNewFile();
      }
      configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configurationFile);

    } catch (final Exception e) {
      plugin.getLogger().info("An exception occured whilst loading configs: " + e);
    }
  }

  public void saveConfigs() {
    try {
      if (configurationFile.length() == 0) {
        configuration.set("proxywhitelist.global.active", false);
        configuration.set("proxywhitelist.global.user", new ArrayList<>());
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, configurationFile);
      } else {
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, configurationFile);
      }
    } catch (Exception e) {
      plugin.getLogger().info("An exception occured whilst loading configs: " + e);
    }
  }

}
