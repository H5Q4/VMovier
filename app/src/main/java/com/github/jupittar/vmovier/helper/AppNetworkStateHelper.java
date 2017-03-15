package com.github.jupittar.vmovier.helper;

import android.content.Context;

import com.github.jupittar.commlib.util.NetworkUtils;
import com.github.jupittar.vmovier.core.helper.NetworkStateHelper;

public class AppNetworkStateHelper implements NetworkStateHelper {

  private Context mContext;

  public AppNetworkStateHelper(Context context) {
    mContext = context;
  }

  @Override
  public boolean isConnected() {
    return NetworkUtils.isNetworkConnected(mContext);
  }
}
