package com.github.jupittar.vmovier.core.data.remote.interceptor;


import com.github.jupittar.vmovier.core.util.Constants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RetryInterceptor implements Interceptor {
  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();
    Response response = null;
    IOException exception = null;

    int tryCount = 0;
    while (tryCount < Constants.RETRY_COUNT && (null == response || !response.isSuccessful())) {
      // retry the request
      try {
        response = chain.proceed(request);
      } catch (IOException e) {
        exception = e;
      } finally {
        tryCount++;
      }
    }

    // throw last exception
    if (response == null && exception != null) {
      throw exception;
    }

    // otherwise just pass the original response on
    return response;
  }
}
