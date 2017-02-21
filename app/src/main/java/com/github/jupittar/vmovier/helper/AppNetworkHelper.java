package com.github.jupittar.vmovier.helper;

import android.content.Context;

import com.github.jupittar.commlib.util.NetworkUtils;
import com.github.jupittar.vmovier.core.helper.NetworkHelper;

public class AppNetworkHelper implements NetworkHelper {

  private Context mContext;

  public AppNetworkHelper(Context context) {
    mContext = context;
  }

  @Override
  public boolean isConnected() {
    return NetworkUtils.isNetworkConnected(mContext);
  }
}
