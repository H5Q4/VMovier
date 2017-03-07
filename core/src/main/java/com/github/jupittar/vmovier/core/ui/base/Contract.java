package com.github.jupittar.vmovier.core.ui.base;

import rx.Subscription;

public interface Contract {

  interface Interactor {}

  interface View {
    void showLoading();
    void hideLoading();
    void showErrorMessage();
  }

  interface Presenter<V extends View> {
    void attach(V view);
    void detach();
    void addSubscription(Subscription s);
    void unSubscribeAll();
  }

}
