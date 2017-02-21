package com.github.jupittar.vmovier.core.data.remote.interceptor;


import com.github.jupittar.vmovier.core.util.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Response;

@SuppressWarnings("unused")
public class HttpCacheInterceptor implements Interceptor {

  @Override
  public Response intercept(Chain chain) throws IOException {
    Response response = chain.proceed(chain.request());
    CacheControl cacheControl = new CacheControl.Builder()
        .maxAge(Constants.CACHE_MAX_AGE_MINS, TimeUnit.MINUTES)
        .build();
    return response.newBuilder()
        .removeHeader("Pragma")
        .header("Cache-Control", cacheControl.toString())
        // if server don't support cache header, uncomment below to replace the upper line
        // may work(not tested!)
//        .header("Cache-Control", "public, max-age=" + Constants.CACHE_MAX_AGE_MINS * 60)
        .build();
  }
}
