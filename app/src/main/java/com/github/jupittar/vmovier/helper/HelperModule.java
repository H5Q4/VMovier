package com.github.jupittar.vmovier.helper;

import android.content.Context;

import com.github.jupittar.vmovier.core.helper.LoggerHelper;
import com.github.jupittar.vmovier.core.helper.NetworkStateHelper;
import com.github.jupittar.vmovier.core.helper.SchedulerHelper;

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
  SchedulerHelper provideAppScheduler() {
    return new AppSchedulerHelper();
  }

  @Provides
  @Singleton
  NetworkStateHelper provideNetworkHelper(Context context) {
    return new AppNetworkStateHelper(context);
  }

}
