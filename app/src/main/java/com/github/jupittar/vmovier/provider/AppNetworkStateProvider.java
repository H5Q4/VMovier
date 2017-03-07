package com.github.jupittar.vmovier.provider;

import android.content.Context;

import com.github.jupittar.commlib.util.NetworkUtils;
import com.github.jupittar.vmovier.core.provider.NetworkStateProvider;

public class AppNetworkStateProvider implements NetworkStateProvider {

  private Context mContext;

  public AppNetworkStateProvider(Context context) {
    mContext = context;
  }

  @Override
  public boolean isConnected() {
    return NetworkUtils.isNetworkConnected(mContext);
  }
}
