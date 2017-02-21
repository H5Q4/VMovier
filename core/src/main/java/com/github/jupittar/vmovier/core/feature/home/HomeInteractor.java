package com.github.jupittar.vmovier.core.feature.home;

import com.github.jupittar.vmovier.core.data.entity.Banner;
import com.github.jupittar.vmovier.core.data.entity.Movie;
import com.github.jupittar.vmovier.core.data.local.CacheManager;
import com.github.jupittar.vmovier.core.data.remote.func.ExtractDataFunc;
import com.github.jupittar.vmovier.core.data.remote.VMovierApi;
import com.github.jupittar.vmovier.core.helper.SchedulerProvider;
import com.github.jupittar.vmovier.core.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import rx.Observable;


public class HomeInteractor implements HomeMvp.Interactor {

  private static final String CACHE_KEY_LATEST_MOVIES = "latest_movies";
  private static final String CACHE_KEY_BANNER = "banner";

  private VMovierApi mVMovierApi;
  private CacheManager mCacheManager;
  private SchedulerProvider mSchedulerProvider;

  @Inject
  public HomeInteractor(VMovierApi VMovierApi,
                        CacheManager cacheManager,
                        SchedulerProvider schedulerProvider) {
    mVMovierApi = VMovierApi;
    mCacheManager = cacheManager;
    mSchedulerProvider = schedulerProvider;
  }

  @Override
  public Observable<List<Movie>> loadLatestMovies(int page) {
    return mVMovierApi.getMoviesByTab(page, 20, "latest")
        .map(new ExtractDataFunc<>())
        .doOnNext(movies -> {
          mCacheManager.save(false, CACHE_KEY_LATEST_MOVIES, new Gson().toJson(movies));
        }).subscribeOn(mSchedulerProvider.backgroundThread());
  }

  @Override
  public Observable<List<Movie>> loadCachedLatestMovies() {
    return Observable.create(subscriber -> {
      String s = mCacheManager.get(false, CACHE_KEY_LATEST_MOVIES, null);
      if (!StringUtils.isEmpty(s)) {
        List<Movie> movies = new Gson().fromJson(s, new TypeToken<List<Movie>>() {
        }.getType());
        subscriber.onNext(movies);
      }
      subscriber.onCompleted();
    });
  }

  @Override
  public Observable<List<Banner>> loadBanner() {
    return mVMovierApi.getBanner()
        .subscribeOn(mSchedulerProvider.backgroundThread())
        .map(new ExtractDataFunc<>());
  }

  @Override
  public Observable<List<Banner>> loadCachedBanner() {
    return Observable.fromCallable(new Callable<List<Banner>>() {
      @Override
      public List<Banner> call() throws Exception {
        String s = mCacheManager.get(false, CACHE_KEY_BANNER, null);
        return new Gson().fromJson(s, new TypeToken<List<Banner>>(){}.getType());
      }
    }).subscribeOn(mSchedulerProvider.backgroundThread());
  }

}
