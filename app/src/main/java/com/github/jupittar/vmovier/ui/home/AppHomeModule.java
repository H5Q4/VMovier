package com.github.jupittar.vmovier.ui.home;

import android.content.Context;

import com.github.jupittar.vmovier.core.ui.home.HomeModule;
import com.github.jupittar.vmovier.core.ui.home.HomeScope;

import dagger.Module;
import dagger.Provides;

@Module
public class AppHomeModule extends HomeModule {

  private HomeFragment.OnFragmentInteractionListener mOnFragmentInteractionListener;

  public AppHomeModule(Context context, HomeFragment fragment) {
    super(fragment);
    if (context instanceof HomeFragment.OnFragmentInteractionListener) {
      mOnFragmentInteractionListener = (HomeFragment.OnFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
          + " must implement OnFragmentInteractionListener");
    }
  }

  @Provides
  @HomeScope
  public HomeFragment.OnFragmentInteractionListener provideOnFragmentInteractionListener() {
    return mOnFragmentInteractionListener;
  }
}
