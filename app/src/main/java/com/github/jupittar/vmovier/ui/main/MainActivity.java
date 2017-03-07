package com.github.jupittar.vmovier.ui.main;

import android.Manifest;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.github.jupittar.commlib.custom.SCViewPager;
import com.github.jupittar.commlib.util.ToastUtils;
import com.github.jupittar.vmovier.AppComponent;
import com.github.jupittar.vmovier.R;
import com.github.jupittar.vmovier.ui.backstage.BackstageFragment;
import com.github.jupittar.vmovier.ui.base.BaseActivity;
import com.github.jupittar.vmovier.ui.channel.ChannelFragment;
import com.github.jupittar.vmovier.ui.home.HomeFragment;
import com.github.jupittar.vmovier.ui.series.SeriesFragment;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;

public class MainActivity extends BaseActivity
    implements NavigationView.OnNavigationItemSelectedListener,
    HomeFragment.OnFragmentInteractionListener {

  @BindView(R.id.toolbar)
  Toolbar mToolbar;
  @BindView(R.id.drawer_layout)
  DrawerLayout mDrawerLayout;
  @BindView(R.id.nav_view)
  NavigationView mNavigationView;
  @BindView(R.id.title_switcher)
  TextSwitcher mTitleSwitcher;
  @BindView(R.id.view_pager)
  SCViewPager mViewPager;

  private boolean mExit;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setUpToolbar();
    setUpDrawer();
    setUpViewPager();
    new RxPermissions(this)
        .request(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)
        .subscribe(new Action1<Boolean>() {
          @Override
          public void call(Boolean granted) {
            if (!granted) {
              ToastUtils.showLong(MainActivity.this, "No permissions to persist data");
            }
          }
        });
  }

  @Override
  protected void injectDependencies(AppComponent component) {
    component.inject(this);
  }

  private void setUpViewPager() {
    List<Fragment> fragments = new ArrayList<>();
    fragments.add(0, HomeFragment.newInstance());
    fragments.add(1, new ChannelFragment());
    fragments.add(2, new SeriesFragment());
    fragments.add(3, new BackstageFragment());
    ContentFragmentPageAdapter adapter = new ContentFragmentPageAdapter(
        getSupportFragmentManager(),
        fragments
    );
    mViewPager.setAdapter(adapter);
    mViewPager.setScrollEnabled(false);
    mViewPager.setOffscreenPageLimit(fragments.size());
  }

  private void setUpToolbar() {
    setSupportActionBar(mToolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    mTitleSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
      @Override
      public View makeView() {
        TextView titleView = new TextView(MainActivity.this);
        TextViewCompat.setTextAppearance(titleView, android.R.style.TextAppearance_Medium);
        Typeface t = Typeface.createFromAsset(MainActivity.this.getAssets(), "fonts/Lobster-Regular.ttf");
        titleView.setTypeface(t);
        titleView.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.white));
        titleView.setGravity(Gravity.CENTER);
        return titleView;
      }
    });
    mTitleSwitcher.setInAnimation(this, R.anim.slide_in_bottom);
    mTitleSwitcher.setOutAnimation(this, R.anim.slide_out_top);
    mTitleSwitcher.setText(getString(R.string.common_latest));
  }

  private void setUpDrawer() {
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    mDrawerLayout.addDrawerListener(toggle);
    toggle.syncState();

    mNavigationView.setNavigationItemSelectedListener(this);
  }

  @Override
  public void onBackPressed() {
    if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
      mDrawerLayout.closeDrawer(GravityCompat.START);
    } else {
      if (mExit) {
        finish();
      } else {
        mExit = true;
        ToastUtils.showShort(MainActivity.this, "再按一次退出应用");
        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
            mExit = false;
          }
        }, 2000);
      }

    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();
    String title = "";
    int position = 0;
    if (id == R.id.nav_home) {
      position = 0;
      title = "Latest";
    } else if (id == R.id.nav_category) {
      position = 1;
      title = "频道";
    } else if (id == R.id.nav_series) {
      position = 2;
      title = "系列";
    } else if (id == R.id.nav_backstage) {
      position = 3;
      title = "幕后";

    }
    mViewPager.setCurrentItem(position, false);
    mTitleSwitcher.setCurrentText(title);
    mDrawerLayout.closeDrawer(GravityCompat.START);
    return true;
  }

  @Override
  public void onTitleChange(String title) {
    TextView textView = (TextView) mTitleSwitcher.getCurrentView();
    String str = textView.getText().toString();
    if (!str.equals(title)) {
      mTitleSwitcher.setText(title);
    }
  }
}
