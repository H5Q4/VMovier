package com.github.jupittar.vmovier.core.ui.home;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

  private HomeContract.View mHomeView;

  public HomeModule(HomeContract.View homeView) {
    mHomeView = homeView;
  }

  @Provides
  public HomeContract.View provideView() {
    return mHomeView;
  }

  @Provides
  @HomeScope
  public HomeContract.Interactor provideInteractor(HomeInteractor interactor) {
    return interactor;
  }

  @Provides
  @HomeScope
  public HomeContract.Presenter<HomeContract.View> providePresenter(HomePresenter presenter) {
    presenter.attach(mHomeView);
    return presenter;
  }

}
