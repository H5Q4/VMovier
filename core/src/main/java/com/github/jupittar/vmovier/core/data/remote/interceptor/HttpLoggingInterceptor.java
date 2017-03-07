package com.github.jupittar.vmovier.core.data.remote.interceptor;


import com.github.jupittar.vmovier.core.provider.LoggerProvider;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class HttpLoggingInterceptor implements Interceptor {

  private LoggerProvider mLoggerProvider;

  public HttpLoggingInterceptor(LoggerProvider loggerProvider) {
    mLoggerProvider = loggerProvider;
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
    mLoggerProvider.t(0);
    Request request = chain.request();

    long t1 = System.nanoTime();

    if (request.method().equals("GET")) {
      mLoggerProvider.i(String.format("%s: %s%n%s%n",
          request.method(),
          request.url(), request.headers()));
    } else {
      mLoggerProvider.i(String.format("%s: %s%n%s%nBODY: %s",
          request.method(),
          request.url(), request.headers(),
          stringifyRequestBody(request)
      ));
    }

    Response response = chain.proceed(request);

    long t2 = System.nanoTime();
    mLoggerProvider.i(String.format(Locale.CHINA, "Received response from %s in %.1fms%n%s",
        response.request().url(), (t2 - t1) / 1e6d, response.headers()));

    ResponseBody responseBody = response.body();
    String responseBodyString = response.body().string();
    mLoggerProvider.json(responseBodyString);

    return response.newBuilder()
        .body(ResponseBody.create(responseBody.contentType(), responseBodyString.getBytes()))
        .build();
  }

  private static String stringifyRequestBody(Request request) {
    try {
      final Request copy = request.newBuilder().build();
      final Buffer buffer = new Buffer();
      if (copy.body() == null) {
        return "";
      }
      copy.body().writeTo(buffer);
      return buffer.readUtf8();
    } catch (final IOException e) {
      return "did not work";
    }
  }

}
