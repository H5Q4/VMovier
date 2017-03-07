package com.github.jupittar.vmovier.core.ui.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BasePresenter<V extends Contract.View> implements Contract.Presenter<V> {

  private V mView;
  private CompositeSubscription mCompositeSubscription;

  @Override
  public void attach(V view) {
    this.mView = view;
  }

  @Override
  public void detach() {
    this.mView = null;
    unSubscribeAll();
  }

  @Override
  public void addSubscription(Subscription s) {
    if (mCompositeSubscription  == null) {
      mCompositeSubscription = new CompositeSubscription();
    }
    mCompositeSubscription.add(s);
  }

  @Override
  public void unSubscribeAll() {
    if (mCompositeSubscription == null) {
      return;
    }
    mCompositeSubscription.unsubscribe();
  }

  public boolean isViewAttached() {
    return mView != null;
  }

  public V getMvpView() {
    if (!isViewAttached()) {
      throw new RuntimeException("Please call Presenter.attach(MvpView) before requesting" +
          " data to the Presenter");
    }
    return mView;
  }
}
