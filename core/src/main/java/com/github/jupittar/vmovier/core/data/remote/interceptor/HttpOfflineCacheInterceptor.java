package com.github.jupittar.vmovier.core.data.remote.interceptor;


import com.github.jupittar.vmovier.core.util.Constants;
import com.github.jupittar.vmovier.core.helper.NetworkStateHelper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class HttpOfflineCacheInterceptor implements Interceptor {

  private NetworkStateHelper mNetworkStateHelper;

  public HttpOfflineCacheInterceptor(NetworkStateHelper networkStateHelper) {
    mNetworkStateHelper = networkStateHelper;
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();

    if (!mNetworkStateHelper.isConnected()) {
      CacheControl cacheControl = new CacheControl.Builder()
          .maxStale(Constants.CACHE_MAX_STALE_DAYS, TimeUnit.DAYS)
          .build();
      request = request.newBuilder()
          .cacheControl(cacheControl)
          // if server don't support cache header, uncomment below to replace the upper line
          // may work(not tested!)
//          .header("Cache-Control", "public, only-if-cached, max-stale="
//              + Constants.CACHE_MAX_STALE_DAYS * 24 * 60 * 60)
          .build();
      System.out.println("No Network, Read From Cache");
    }

    return chain.proceed(request);
  }
}
