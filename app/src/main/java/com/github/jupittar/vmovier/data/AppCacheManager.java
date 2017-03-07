package com.github.jupittar.vmovier.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.jupittar.vmovier.core.data.local.CacheManager;
import com.github.jupittar.vmovier.core.data.local.UserManager;

import javax.inject.Inject;

public class AppCacheManager implements CacheManager {

  private static final String GLOBAL_USER = "global_user";

  AppSQLiteOpenHelper mAppSQLiteOpenHelper;
  UserManager mUserManager;

  @Inject
  public AppCacheManager(AppSQLiteOpenHelper appSQLiteOpenHelper, UserManager userManager) {
    mAppSQLiteOpenHelper = appSQLiteOpenHelper;
    mUserManager = userManager;
  }

  @Override
  public String get(boolean isBindUser, String key, String defaultValue) {
    String uid;
    if (!isBindUser) {
      uid = GLOBAL_USER;
    } else if (mUserManager.getUser() == null) {
      return defaultValue;
    } else {
      uid = mUserManager.getUser().getUid();
    }
    Cursor query = this.mAppSQLiteOpenHelper
        .getReadableDatabase()
        .query(AppSQLiteOpenHelper.CACHE_TABLE_NAME, new String[]{"value"}, "account=? AND key=?",
            new String[]{uid, key}, null, null, null);
    query.moveToFirst();
    String res = defaultValue;
    while (!query.isAfterLast()) {
      try {
        res = query.getString(query.getColumnIndex("value"));
        query.moveToNext();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    query.close();
    return res;
  }

  @Override
  public void save(boolean isBindUser, String key, String value) {
    if (value != null) {
      String uid;
      if (!isBindUser) {
        uid = GLOBAL_USER;
      } else if (mUserManager.getUser() != null) {
        uid = mUserManager.getUser().getUid();
      } else {
        return;
      }
      if (get(isBindUser, key, null) != null) {
        remove(isBindUser, key);
      }
      SQLiteDatabase db = this.mAppSQLiteOpenHelper.getWritableDatabase();
      ContentValues values = new ContentValues();
      values.put("account", uid);
      values.put("key", key);
      values.put("value", value);
      db.insert("table_cache", null, values);
    }
  }

  public void remove(boolean isBindUser, String key) {
    String uid;
    if (!isBindUser) {
      uid = GLOBAL_USER;
    } else if (mUserManager.getUser() != null) {
      uid = mUserManager.getUser().getUid();
    } else {
      return;
    }
    this.mAppSQLiteOpenHelper
        .getWritableDatabase()
        .delete(AppSQLiteOpenHelper.CACHE_TABLE_NAME, "account=? AND key=?",
            new String[]{uid, key});
  }

  public void removeAll(boolean isBindUser) {
    String uid;
    if (!isBindUser) {
      uid = GLOBAL_USER;
    } else if (mUserManager.getUser() != null) {
      uid = mUserManager.getUser().getUid();
    } else {
      return;
    }
    this.mAppSQLiteOpenHelper
        .getWritableDatabase()
        .delete("table_cache", "account=?", new String[]{uid});
  }

  public void closeDB() {
    if (mAppSQLiteOpenHelper != null) {
      try {
        mAppSQLiteOpenHelper.getWritableDatabase().close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      mAppSQLiteOpenHelper = null;
    }
  }
}
