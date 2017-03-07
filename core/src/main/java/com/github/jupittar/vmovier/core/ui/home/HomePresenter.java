package com.github.jupittar.vmovier.core.ui.home;

import com.github.jupittar.vmovier.core.data.entity.Banner;
import com.github.jupittar.vmovier.core.data.entity.Movie;
import com.github.jupittar.vmovier.core.data.local.CacheManager;
import com.github.jupittar.vmovier.core.provider.SchedulerProvider;
import com.google.gson.Gson;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;

import static com.github.jupittar.vmovier.core.util.Constants.CACHE_KEY_BANNER;
import static com.github.jupittar.vmovier.core.util.Constants.CACHE_KEY_LATEST_MOVIES;

public class HomePresenter extends HomeContract.Presenter<HomeContract.View> {

  @Inject
  HomeContract.Interactor mInteractor;
  @Inject
  CacheManager mCacheManager;
  SchedulerProvider mSchedulerProvider;

  @Inject
  public HomePresenter(SchedulerProvider schedulerProvider) {
    mSchedulerProvider = schedulerProvider;
  }

  @Override
  public void listLatestMovies(int page) {
    Observable<List<Movie>> moviesFromApi =
        mInteractor.loadLatestMovies(page)
            .doOnNext(movies -> {
              mCacheManager.save(false, CACHE_KEY_LATEST_MOVIES, new Gson().toJson(movies));
            });
    Observable<List<Movie>> moviesFromCache =
        mInteractor.loadCachedLatestMovies()
        .filter(movies -> movies != null);
    Observable<List<Movie>> listObservable
        = page == 1 ?
        Observable.concatDelayError(moviesFromCache, moviesFromApi)
            .observeOn(mSchedulerProvider.mainThread(), true)
        : moviesFromApi.observeOn(mSchedulerProvider.mainThread());
    Subscription subscription = listObservable
        .subscribe(movies -> {
          getMvpView().hideLoading();
          getMvpView().showLatestMovies(movies);
        }, throwable -> {
          if (throwable instanceof SocketTimeoutException
              || throwable instanceof UnknownHostException
              || throwable instanceof SocketException) {
            if (!getMvpView().notEmpty()) {
              getMvpView().showErrorLayout();
            } else if (page > 1) {
              getMvpView().showReloadSnackbar();
            }
          } else {
            getMvpView().showErrorMessage();
          }
          getMvpView().hideLoading();
        });
    addSubscription(subscription);
  }

  @Override
  public void listBanner() {
    Observable<List<Banner>> bannerFromCache
        = mInteractor.loadCachedBanner()
        .filter(banners -> banners != null);
    Observable<List<Banner>> bannerFromApi
        = mInteractor.loadBanner()
        .doOnNext(banners -> {
          mCacheManager.save(false, CACHE_KEY_BANNER, new Gson().toJson(banners));
        });
    Subscription subscription
        = Observable.concatDelayError(bannerFromCache, bannerFromApi)
        .observeOn(mSchedulerProvider.mainThread(), true)
        .subscribe(banners -> {
              if (banners != null) {
                getMvpView().showBanner(banners);
              }
            }, throwable -> {
              if (throwable instanceof SocketTimeoutException
                  || throwable instanceof UnknownHostException
                  || throwable instanceof SocketException) {
                getMvpView().showNetworkError();
              }
            }
        );
    addSubscription(subscription);
  }

}
