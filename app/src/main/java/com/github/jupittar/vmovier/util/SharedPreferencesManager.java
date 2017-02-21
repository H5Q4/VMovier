package com.github.jupittar.vmovier.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.jupittar.commlib.util.GsonUtils;
import com.github.jupittar.vmovier.core.data.entity.User;
import com.github.jupittar.vmovier.data.AppUserManager;

public class SharedPreferencesManager {

  private static final String KEY_VMOVIER_PREF = "vmovier_pref";
  private static final String KEY_USER = "user";

  private SharedPreferencesManager() {
  }

  public static void saveUser(Context context, User user) {
    getEditor(context).putString(KEY_USER, GsonUtils.toJson(user)).commit();
  }

  public static User getUser(Context context) {
    String userString = getSharedPreferences(context).getString(KEY_USER, "");
    return GsonUtils.toObject(userString, User.class);
  }

  public static void removeUser(Context context) {
    getEditor(context).remove(KEY_USER);
  }


  // region Helper Methods
  private static SharedPreferences.Editor getEditor(Context context) {
    SharedPreferences preferences = getSharedPreferences(context);
    return preferences.edit();
  }

  private static SharedPreferences getSharedPreferences(Context context) {
    return context.getSharedPreferences(KEY_VMOVIER_PREF, Context.MODE_PRIVATE);
  }
  // endregion
}
