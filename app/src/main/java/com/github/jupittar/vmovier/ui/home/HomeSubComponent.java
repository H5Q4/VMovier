package com.github.jupittar.vmovier.ui.home;

import com.github.jupittar.vmovier.core.feature.home.HomeScope;

import dagger.Subcomponent;

@HomeScope
@Subcomponent(modules = AppHomeModule.class)
public interface HomeSubComponent {

  void inject(HomeFragment fragment);

}
