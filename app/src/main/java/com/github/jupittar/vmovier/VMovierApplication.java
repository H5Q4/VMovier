package com.github.jupittar.vmovier;

import android.app.Application;

import com.github.jupittar.vmovier.core.data.remote.ApiModule;
import com.github.jupittar.vmovier.data.DataModule;
import com.github.jupittar.vmovier.provider.ProviderModule;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

public class VMovierApplication extends Application {

  private static final String LOGGER_TAG = "vmovier_log";

  private static VMovierApplication sVMovierApplication;
  private static AppComponent sAppComponent;

  public static AppComponent getAppComponent() {
    return sAppComponent;
  }

  public static VMovierApplication getInstance() {
    return sVMovierApplication;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    sVMovierApplication = this;
    if (BuildConfig.DEBUG) {
      initApp4Debug();
    } else {
      initApp4Release();
    }
    sAppComponent = createAppComponent();
  }

  private AppComponent createAppComponent() {
    return DaggerAppComponent
        .builder()
        .appModule(new AppModule(this))
        .providerModule(new ProviderModule())
        .apiModule(new ApiModule())
        .dataModule(new DataModule())
        .build();
  }

  private void initApp4Release() {
    Logger
        .init()
        .logLevel(LogLevel.NONE);
  }

  private void initApp4Debug() {
    Logger
        .init(LOGGER_TAG)
        .methodCount(3)                 // default 2
        .logLevel(LogLevel.FULL)        // default LogLevel.FULL
        .hideThreadInfo()
        .methodOffset(2);                // default 0
  }

}
