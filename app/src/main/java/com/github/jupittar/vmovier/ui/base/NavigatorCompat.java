package com.github.jupittar.vmovier.ui.base;


import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;

import com.github.jupittar.vmovier.ui.Navigator;


public class NavigatorCompat implements Navigator {

  private static final int DELAY = 300;

  private static Handler mUiHandler = new Handler(Looper.getMainLooper());

  @Override
  public void navigateTo(AppCompatActivity activity, Intent intent) {
    if (activity == null) {
      return;
    }
    activity.startActivity(intent);
  }

  @Override
  public void navigateToDelayed(final AppCompatActivity activity, final Intent intent) {
    mUiHandler.postDelayed(new Runnable() {
      @Override
      public void run() {
        if (activity == null) {
          return;
        }
        activity.startActivity(intent);
      }
    }, DELAY);
  }

  @Override
  public void finish(AppCompatActivity activity) {
    if (activity == null) {
      return;
    }
    activity.finish();
  }
}