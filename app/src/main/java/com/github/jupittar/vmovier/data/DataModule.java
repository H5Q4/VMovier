package com.github.jupittar.vmovier.data;

import android.content.Context;

import com.github.jupittar.vmovier.core.data.local.CacheManager;
import com.github.jupittar.vmovier.core.data.local.UserManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

  @Provides
  @Singleton
  public AppSQLiteOpenHelper provideAppSQLiteOpenHelper(Context context) {
    return new AppSQLiteOpenHelper(context);
  }

  @Provides
  @Singleton
  public UserManager provideUserManager(Context context) {
    return new AppUserManager(context);
  }

  @Provides
  @Singleton
  public CacheManager provideCacheManager(AppCacheManager manager) {
    return manager;
  }

}
