package com.github.jupittar.vmovier.helper;

import android.content.Context;

import com.github.jupittar.vmovier.core.helper.LoggerHelper;
import com.github.jupittar.vmovier.core.helper.NetworkHelper;
import com.github.jupittar.vmovier.core.helper.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class HelperModule {

  @Provides
  @Singleton
  LoggerHelper provideLoggerHelper(Context context) {
    return new AppLoggerHelper(context);
  }

  @Provides
  @Singleton
  SchedulerProvider provideAppScheduler() {
    return new AppSchedulerProvider();
  }

  @Provides
  @Singleton
  NetworkHelper provideNetworkHelper(Context context) {
    return new AppNetworkHelper(context);
  }

}
