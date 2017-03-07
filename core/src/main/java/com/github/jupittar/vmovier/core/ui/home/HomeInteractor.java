package com.github.jupittar.vmovier.core.ui.home;

import com.github.jupittar.vmovier.core.data.entity.Banner;
import com.github.jupittar.vmovier.core.data.entity.Movie;
import com.github.jupittar.vmovier.core.data.local.CacheManager;
import com.github.jupittar.vmovier.core.data.remote.func.ExtractDataFunc;
import com.github.jupittar.vmovier.core.data.remote.VMovierApi;
import com.github.jupittar.vmovier.core.provider.SchedulerProvider;
import com.github.jupittar.vmovier.core.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static com.github.jupittar.vmovier.core.util.Constants.CACHE_KEY_BANNER;
import static com.github.jupittar.vmovier.core.util.Constants.CACHE_KEY_LATEST_MOVIES;


public class HomeInteractor implements HomeContract.Interactor {

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
        .subscribeOn(mSchedulerProvider.backgroundThread());
  }

  @Override
  public Observable<List<Movie>> loadCachedLatestMovies() {
    return Observable.<List<Movie>>fromCallable(() -> {
      String s = mCacheManager.get(false, CACHE_KEY_LATEST_MOVIES, null);
      if (StringUtils.isEmpty(s)) {
        return null;
      }
      return new Gson().fromJson(s, new TypeToken<List<Movie>>() {
      }.getType());
    }).subscribeOn(mSchedulerProvider.backgroundThread());
  }

  @Override
  public Observable<List<Banner>> loadBanner() {
    return mVMovierApi.getBanner()
        .subscribeOn(mSchedulerProvider.backgroundThread())
        .map(new ExtractDataFunc<>());
  }

  @Override
  public Observable<List<Banner>> loadCachedBanner() {
    return Observable.<List<Banner>>fromCallable(() -> {
      String s = mCacheManager.get(false, CACHE_KEY_BANNER, null);
      if (StringUtils.isEmpty(s)) {
        return null;
      }
      return new Gson().fromJson(s, new TypeToken<List<Banner>>() {
      }.getType());
    }).subscribeOn(mSchedulerProvider.backgroundThread());
  }

}
