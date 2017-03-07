package com.github.jupittar.vmovier.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public interface Navigator {
  void navigateTo(AppCompatActivity activity, Intent intent);

  void navigateToDelayed(AppCompatActivity activity, Intent intent);

  void finish(AppCompatActivity activity);
}
