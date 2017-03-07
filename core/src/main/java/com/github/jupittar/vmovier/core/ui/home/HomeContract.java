package com.github.jupittar.vmovier.core.ui.home;

import com.github.jupittar.vmovier.core.data.entity.Banner;
import com.github.jupittar.vmovier.core.data.entity.Movie;
import com.github.jupittar.vmovier.core.ui.base.BasePresenter;
import com.github.jupittar.vmovier.core.ui.base.Contract;

import java.util.List;

import rx.Observable;

public interface HomeContract {

  interface View extends Contract.View {
    void showBanner(List<Banner> banners);
    void showLatestMovies(List<Movie> movies);
    void showErrorLayout();
    void hideErrorLayout();
    void showReloadSnackbar();
    void showNetworkError();

    boolean notEmpty();
  }

  interface Interactor extends Contract.Interactor {
    Observable<List<Movie>> loadLatestMovies(int page);
    Observable<List<Movie>> loadCachedLatestMovies();
    Observable<List<Banner>> loadBanner();
    Observable<List<Banner>> loadCachedBanner();
  }

  abstract class Presenter<V extends View> extends BasePresenter<V> {
    public abstract void listLatestMovies(int page);
    public abstract void listBanner();
  }

}
