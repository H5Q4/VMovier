package com.github.jupittar.vmovier;

import com.github.jupittar.vmovier.core.data.remote.NetworkModule;
import com.github.jupittar.vmovier.data.DataModule;
import com.github.jupittar.vmovier.helper.HelperModule;
import com.github.jupittar.vmovier.ui.home.AppHomeModule;
import com.github.jupittar.vmovier.ui.home.HomeSubComponent;
import com.github.jupittar.vmovier.ui.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
    AppModule.class,
    HelperModule.class,
    NetworkModule.class,
    DataModule.class
})
public interface AppComponent {
  void inject(MainActivity activity);
  HomeSubComponent plus(AppHomeModule module);
}
