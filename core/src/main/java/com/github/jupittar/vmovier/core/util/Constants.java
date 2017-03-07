package com.github.jupittar.vmovier.core.util;

public class Constants {

  public static final String BASE_URL = "http://app.vmoiver.com/";
  public static final String API_KEY = "api_key";

  public static final int NETWORK_CONNECTION_TIMEOUT = 30; // 30 sec
  public static final int NETWORK_READ_TIMEOUT = 10;  // 10 sec
  public static final int NETWORK_WRITE_TIMEOUT = 10; // 10 sec
  public static final long CACHE_SIZE = 10 * 1024 * 1024; // 10 MB
  public static final int CACHE_MAX_AGE_MINS = 2; // 2 min
  public static final int CACHE_MAX_STALE_DAYS = 7; // 7 day
  public static final int RETRY_COUNT = 3;  // 3 times

  public static final String CACHE_KEY_LATEST_MOVIES = "latest_movies";
  public static final String CACHE_KEY_BANNER = "banner";

}
