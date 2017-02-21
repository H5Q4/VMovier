package com.github.jupittar.vmovier.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppSQLiteOpenHelper extends SQLiteOpenHelper {

  private static final String DB_NAME = "vmovier_db";
  public static final String CACHE_TABLE_NAME = "table_cache";
  private static final int DB_VERSION = 1;

  public AppSQLiteOpenHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE IF NOT EXISTS " + CACHE_TABLE_NAME +
        " (account VARCHAR ,key VARCHAR,value VARCHAR);");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }


}
