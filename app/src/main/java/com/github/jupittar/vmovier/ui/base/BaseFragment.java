package com.github.jupittar.vmovier.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.github.jupittar.vmovier.AppComponent;
import com.github.jupittar.vmovier.VMovierApplication;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

  private Unbinder mUnbinder;

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mUnbinder = ButterKnife.bind(this, view);
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    injectDependencies(context, VMovierApplication.getAppComponent());
  }

  @Override
  public void onDestroyView() {
    mUnbinder.unbind();
    super.onDestroyView();
  }

  protected abstract void injectDependencies(Context context, AppComponent component);
}
