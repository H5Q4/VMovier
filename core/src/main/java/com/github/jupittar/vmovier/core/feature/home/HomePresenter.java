package com.github.jupittar.vmovier.core.feature.home;

import com.github.jupittar.vmovier.core.data.entity.Movie;
import com.github.jupittar.vmovier.core.helper.SchedulerProvider;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;

public class HomePresenter extends HomeMvp.Presenter<HomeMvp.View> {

  @Inject
  HomeInteractor mHomeInteractor;
  SchedulerProvider mSchedulerProvider;

  @Inject
  public HomePresenter(SchedulerProvider schedulerProvider) {
    mSchedulerProvider = schedulerProvider;
  }

  @Override
  public void listLatestMovies(int page) {
    Observable<List<Movie>> moviesFromApi = mHomeInteractor.loadLatestMovies(page);
    Observable<List<Movie>> moviesFromCache = mHomeInteractor.loadCachedLatestMovies();
    Observable<List<Movie>> listObservable =
        page == 1 ? Observable.concat(moviesFromCache, moviesFromApi) : moviesFromApi;
    Subscription subscription = listObservable
        .observeOn(mSchedulerProvider.mainThread())
        .subscribe(movies -> {
          System.out.println("onNext");
          getMvpView().hideLoading();
          getMvpView().showLatestMovies(movies);
        }, throwable -> {
          if (throwable instanceof SocketTimeoutException
              || throwable instanceof UnknownHostException
              || throwable instanceof SocketException) {
            if (!getMvpView().notEmpty()) {
              getMvpView().showErrorLayout();
            } else {
              getMvpView().showReloadSnackbar();
            }
          }
          getMvpView().hideLoading();
        });
    addSubscription(subscription);
  }

  @Override
  public void listBanner() {
    Subscription subscription = mHomeInteractor.loadBanner()
        .observeOn(mSchedulerProvider.mainThread())
        .subscribe(banners -> {
              getMvpView().showBanner(banners);
            }, throwable -> {
              getMvpView().showErrorMessage();
            }
        );
    addSubscription(subscription);
  }

}
