package com.github.jupittar.vmovier.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.github.jupittar.vmovier.AppComponent;
import com.github.jupittar.vmovier.R;
import com.github.jupittar.vmovier.ui.base.BaseActivity;
import com.github.jupittar.vmovier.ui.main.MainActivity;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {

  @BindView(R.id.tv_slogan)
  TextView mSloganTv;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash);
    animation.setAnimationListener(new Animation.AnimationListener() {
      @Override
      public void onAnimationStart(Animation animation) {

      }

      @Override
      public void onAnimationEnd(Animation animation) {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
      }

      @Override
      public void onAnimationRepeat(Animation animation) {

      }
    });
    mSloganTv.startAnimation(animation);
  }

  @Override
  protected void injectDependencies(AppComponent component) {

  }
}
