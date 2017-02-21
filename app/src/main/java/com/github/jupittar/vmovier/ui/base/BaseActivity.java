package com.github.jupittar.vmovier.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.github.jupittar.vmovier.AppComponent;
import com.github.jupittar.vmovier.VMovierApplication;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

  private Unbinder mUnbinder;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    injectDependencies(VMovierApplication.getAppComponent());
  }

  @Override
  public void setContentView(@LayoutRes int layoutResID) {
    super.setContentView(layoutResID);
    mUnbinder = ButterKnife.bind(this);
  }

  @Override
  protected void onDestroy() {
    mUnbinder.unbind();
    super.onDestroy();
  }

  protected abstract void injectDependencies(AppComponent component);

}
