package com.github.jupittar.vmovier.core.data.local;

public interface CacheManager {

  String get(boolean isBindUser, String key, String defaultValue);

  void save(boolean isBindUser, String key, String value);

  void remove(boolean isBindUser, String key);

}
