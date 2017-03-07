package com.github.jupittar.vmovier;

import android.content.Context;
import android.content.res.Resources;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

  private VMovierApplication mApp;

  AppModule(VMovierApplication app) {
    this.mApp = app;
  }

  @Provides
  @Singleton
  Context provideContext() {
    return mApp.getApplicationContext();
  }

  @Provides
  @Singleton
  Resources provideResource() {
    return mApp.getResources();
  }

  @Provides
  @Singleton
  @Named("cacheDir")
  File provideCacheDir(Context context) {
    return context.getCacheDir();
  }

  @Provides
  @Singleton
  @Named("isDebug")
  boolean isDebug() {
    return BuildConfig.DEBUG;
  }

}
