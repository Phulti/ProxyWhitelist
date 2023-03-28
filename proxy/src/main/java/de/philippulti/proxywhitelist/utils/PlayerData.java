package de.philippulti.proxywhitelist.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class PlayerData {

  private final UUID uuid;
  private final String name;

}
