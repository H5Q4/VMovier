package com.github.jupittar.vmovier.data;

import android.content.Context;

import com.github.jupittar.vmovier.core.data.entity.User;
import com.github.jupittar.vmovier.core.data.local.UserManager;
import com.github.jupittar.vmovier.util.SharedPreferencesManager;


public class AppUserManager implements UserManager {

  private User mUser;
  private Context mContext;

  public AppUserManager(Context context) {
    mContext = context;
  }

  @Override
  public boolean isLoggedIn() {
    return getUser() != null;
  }

  @Override
  public void setUser(User user) {
    mUser = user;
    SharedPreferencesManager.saveUser(mContext, user);
  }

  @Override
  public User getUser() {
    if (mUser == null) {
      mUser = SharedPreferencesManager.getUser(mContext);
    }
    return mUser;
  }

  @Override
  public void removeUser() {
    SharedPreferencesManager.removeUser(mContext);
    mUser = null;
  }
}
