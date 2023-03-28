package de.philippulti.proxywhitelist.utils;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.logging.Logger;

public class PlayerDataUtil {

  public static PlayerData getPlayerData(final String name) {
    try {
      String url = "https://api.mojang.com/users/profiles/minecraft/" + name;
      HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
      connection.connect();
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      InputStream in = connection.getInputStream();
      byte[] buffer = new byte[1024];
      int read;
      while ((read = in.read(buffer)) > 0) {
        out.write(buffer, 0, read);
      }

      String data = new String(out.toByteArray(), StandardCharsets.UTF_8);
      Gson gson = new Gson();
      UniqueIdResponse response = gson.fromJson(data, UniqueIdResponse.class);
      if (response == null) {
        return null;
      }

      StringBuilder builder = new StringBuilder(response.id);
      builder.insert(20, "-");
      builder.insert(16, "-");
      builder.insert(12, "-");
      builder.insert(8, "-");
      return new PlayerData(UUID.fromString(builder.toString()), response.name);
    } catch (final Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private static class UniqueIdResponse {
    private String name;
    private String id;
  }

}
