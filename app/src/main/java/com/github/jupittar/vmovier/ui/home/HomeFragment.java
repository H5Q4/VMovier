package com.github.jupittar.vmovier.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.jupittar.commlib.custom.AutoScrollPager;
import com.github.jupittar.commlib.custom.LoadingImageView;
import com.github.jupittar.commlib.custom.recyclerview.EndlessScrollListener;
import com.github.jupittar.commlib.custom.recyclerview.adapter.CommonViewAdapter;
import com.github.jupittar.commlib.custom.recyclerview.entity.SectionedItem;
import com.github.jupittar.commlib.util.SnackbarUtils;
import com.github.jupittar.commlib.util.ToastUtils;
import com.github.jupittar.vmovier.AppComponent;
import com.github.jupittar.vmovier.R;
import com.github.jupittar.vmovier.core.data.entity.Banner;
import com.github.jupittar.vmovier.core.data.entity.Movie;
import com.github.jupittar.vmovier.core.ui.home.HomeContract;
import com.github.jupittar.vmovier.ui.base.LazyFragment;
import com.github.jupittar.vmovier.ui.home.adapter.MoviesAdapter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;

public class HomeFragment extends LazyFragment implements HomeContract.View {

  private static final int TYPE_WEB_PAGE = 1;
  private static final int TYPE_MOVIE = 2;

  @BindView(R.id.btn_reload)
  Button mReloadBtn;
  @BindView(R.id.ll_error)
  LinearLayout mErrorLayout;
  @BindView(R.id.iv_loading)
  LoadingImageView mLoadingIv;
  @BindView(R.id.recycler_view)
  RecyclerView mMovieRecyclerView;

  AutoScrollPager mBanner;

  private MoviesAdapter mMoviesAdapter;
  private DateFormat mDateFormat
      = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.ENGLISH);

  @Inject
  HomeContract.Presenter<HomeContract.View> mPresenter;
  @Inject
  OnFragmentInteractionListener mOnFragmentInteractionListener;

  private int mPage = 1;

  public HomeFragment() {
    // Required empty public constructor
  }

  public static HomeFragment newInstance() {
    HomeFragment fragment = new HomeFragment();
    return fragment;
  }

  @Override
  public void onFirstAppear() {
    setUpBanner();
    mPresenter.listBanner();
    setUpRecyclerView();
    mPresenter.listLatestMovies(mPage);
  }

  private void setUpBanner() {
    LinearLayout bannerContainer = (LinearLayout) LayoutInflater.from(getActivity())
        .inflate(R.layout.banner, null);
    mBanner = (AutoScrollPager) bannerContainer.findViewById(R.id.banner);
    bannerContainer.removeView(mBanner);
  }

  private void setUpRecyclerView() {
    final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    mMovieRecyclerView.setLayoutManager(linearLayoutManager);
    mMovieRecyclerView.setHasFixedSize(true);
    mMovieRecyclerView.setItemAnimator(new DefaultItemAnimator());
    mMoviesAdapter = new MoviesAdapter(getActivity(), R.layout.item_movie,
        R.layout.item_section);
    mMoviesAdapter.addHeaderView(mBanner);
    mMoviesAdapter.setOnItemClickListener(new CommonViewAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(View view, int position) {
        if (position == 0) {
          return;
        }
        position--;
        SectionedItem<Movie> item = mMoviesAdapter.getItemList().get(position);
        if (item.isHead()) {
          return;
        }
//        String movieId = item.getItem().getPostid();
//        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
//        intent.putExtra(MovieDetailActivity.POST_ID, movieId);
//        startActivity(intent);
      }
    });
    mMovieRecyclerView.setAdapter(mMoviesAdapter);
    mMovieRecyclerView.addOnScrollListener(new EndlessScrollListener() {

      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int position = linearLayoutManager.findFirstVisibleItemPosition();
        if (position == 0) {
          mBanner.startAutoPlay();
          return;
        } else {
          mBanner.stopAutoPlay();
        }
        if (mMoviesAdapter.hasHeader()) {
          position--;
        }
        String title;
        if (dy < 0 && mMoviesAdapter.getItemList().get(position + 1).isHead()) {
          Movie movie = mMoviesAdapter.getItemList().get(position - 1).getItem();
          title = mDateFormat
              .format(new Date(Long.parseLong(movie.getPublish_time()) * 1000))
              .split(",")[0];
          if (title.equals(mDateFormat
              .format(new Date(Long.parseLong(
                  mMoviesAdapter.getItemList().get(0).getItem()
                      .getPublish_time()) * 1000))
              .split(",")[0])) {
            title = "Latest";
          }
          mOnFragmentInteractionListener.onTitleChange(title);
        } else if (dy > 0 && mMoviesAdapter.getItemList().get(position).isHead()) {
          title = mMoviesAdapter.getItemList().get(position).getTitle();
          mOnFragmentInteractionListener.onTitleChange(title);
        }
      }

      @Override
      public void onLoadMore() {
        mPage++;
        mPresenter.listLatestMovies(mPage);
      }
    });
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_home, container, false);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mOnFragmentInteractionListener = null;
    mPresenter.detach();
    mPresenter = null;
  }

  //region Implementation for HomeContract.View
  @Override
  public void showBanner(final List<Banner> banners) {
    List<String> imageUrls = new ArrayList<>();
    for (Banner banner :
        banners) {
      imageUrls.add(banner.getImage());
    }
    mBanner.setImages(imageUrls, new AutoScrollPager.AutoScrollPagerListener() {
      @Override
      public void displayImage(String imgUrl, ImageView imageView) {
        Glide.with(getActivity())
            .load(imgUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(imageView);
      }

      @Override
      public void setTitle(int position, TextView textView) {

      }

      @Override
      public void onImageClick(int position, ImageView imageView) {
        Banner banner = banners.get(position);
        String bannerType = banner.getExtra_data().getApp_banner_type();
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        switch (Integer.parseInt(bannerType)) {
//          case TYPE_WEB_PAGE:
//            bundle.putString(WebViewActivity.KEY_URL,
//                banner.getExtra_data().getApp_banner_param());
//            intent.setClass(getActivity(),
//                WebViewActivity.class);
//            intent.putExtras(bundle);
//            startActivity(intent);
//            break;
//          case TYPE_MOVIE:
//            boolean isAlbum = banner.getExtra_data().getIs_album()
//                .equals("1");
//            if (!isAlbum) {
//              bundle.putString(MovieDetailActivity.POST_ID,
//                  banner.getExtra_data().getApp_banner_param());
//              intent.putExtras(bundle);
//              intent.setClass(getActivity(), MovieDetailActivity.class);
//              startActivity(intent);
//              return;
//            }
//            break;
        }
      }
    });
  }

  @Override
  public void showLatestMovies(List<Movie> movies) {
    if (movies != null) {
      List<SectionedItem<Movie>> sectionedItems = new ArrayList<>();
      String lastTitle = "";
      if (mMoviesAdapter.getItemCountIgnoreHF() > 0) {
        Movie lastMovie = mMoviesAdapter.getItemList()
            .get(mMoviesAdapter.getItemCountIgnoreHF() - 1).getItem();
        lastTitle = mDateFormat
            .format(new Date(Long.parseLong(
                lastMovie.getPublish_time()) * 1000))
            .split(",")[0];
      }
      for (int i = 0; i < movies.size(); i++) {
        String title = mDateFormat
            .format(new Date(Long.parseLong(
                movies.get(i).getPublish_time()) * 1000))
            .split(",")[0];
        if (i == 0 && mMoviesAdapter.getItemCountIgnoreHF() == 0) {
          lastTitle = title;
        }
        if (!title.equals(lastTitle)) {
          sectionedItems.add(new SectionedItem<Movie>(true, title));
          lastTitle = title;
        }
        sectionedItems.add(new SectionedItem<>(movies.get(i)));

      }
      mMoviesAdapter.addAll(sectionedItems);
    }
  }

  @Override
  public void showErrorLayout() {
    mErrorLayout.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideErrorLayout() {
    mErrorLayout.setVisibility(View.GONE);
  }

  @Override
  public void showReloadSnackbar() {
    new SnackbarUtils.SnackbarBuilder()
        .view(getActivity().findViewById(android.R.id.content))
        .message(getString(R.string.network_error))
        .duration(Snackbar.LENGTH_INDEFINITE)
        .backgroundColor(ContextCompat.getColor(getActivity(),
            R.color.material_blue_grey))
        .messageColor(ContextCompat.getColor(getActivity(),
            R.color.material_amber_dark))
        .actionText(getString(R.string.common_retry))
        .action(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            mPresenter.listLatestMovies(mPage);
          }
        })
        .build()
        .show();
  }

  @Override
  public void showNetworkError() {
    ToastUtils.showLong(getActivity(), R.string.network_error);
  }

  @Override
  public boolean notEmpty() {
    return mMoviesAdapter.getItemCountIgnoreHF() != 0;
  }

  @Override
  public void showLoading() {
    mLoadingIv.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideLoading() {
    mLoadingIv.setVisibility(View.GONE);
  }

  @Override
  public void showErrorMessage() {
    ToastUtils.showLong(getActivity(), R.string.common_error);
  }
  //endregion

  @Override
  protected void injectDependencies(Context context, AppComponent component) {
    component.plus(new AppHomeModule(context, this)).inject(this);
  }

  public interface OnFragmentInteractionListener {
    void onTitleChange(String title);
  }
}
