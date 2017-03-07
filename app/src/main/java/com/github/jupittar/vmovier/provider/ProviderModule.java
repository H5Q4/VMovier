package com.github.jupittar.vmovier.provider;

import android.content.Context;

import com.github.jupittar.vmovier.core.provider.LoggerProvider;
import com.github.jupittar.vmovier.core.provider.NetworkStateProvider;
import com.github.jupittar.vmovier.core.provider.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ProviderModule {

  @Provides
  @Singleton
  LoggerProvider provideLoggerHelper(Context context) {
    return new AppLoggerProvider(context);
  }

  @Provides
  @Singleton
  SchedulerProvider provideAppScheduler() {
    return new AppSchedulerProvider();
  }

  @Provides
  @Singleton
  NetworkStateProvider provideNetworkHelper(Context context) {
    return new AppNetworkStateProvider(context);
  }

}
