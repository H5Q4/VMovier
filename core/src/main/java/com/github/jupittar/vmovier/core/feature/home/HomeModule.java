package com.github.jupittar.vmovier.core.feature.home;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

  private HomeMvp.View mHomeView;

  public HomeModule(HomeMvp.View homeView) {
    mHomeView = homeView;
  }

  @Provides
  public HomeMvp.View provideView() {
    return mHomeView;
  }

  @Provides
  @HomeScope
  public HomeMvp.Interactor provideInteractor(HomeInteractor interactor) {
    return interactor;
  }

  @Provides
  @HomeScope
  public HomeMvp.Presenter<HomeMvp.View> providePresenter(HomePresenter presenter) {
    presenter.attach(mHomeView);
    return presenter;
  }

}
