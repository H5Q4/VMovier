package com.github.jupittar.vmovier.helper;

import com.github.jupittar.vmovier.core.helper.SchedulerHelper;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AppSchedulerHelper implements SchedulerHelper {

  @Override
  public Scheduler mainThread() {
    return AndroidSchedulers.mainThread();
  }

  @Override
  public Scheduler backgroundThread() {
    return Schedulers.io();
  }

}
