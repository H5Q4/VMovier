package com.github.jupittar.vmovier.core.feature.base;

import com.github.jupittar.vmovier.core.data.remote.VMovierApi;
import com.github.jupittar.vmovier.core.helper.SchedulerProvider;

public class BaseInteractor implements Mvp.Interactor {

  protected VMovierApi mVMovierApi;
  protected SchedulerProvider mSchedulerProvider;

  public BaseInteractor(VMovierApi vMovierApi, SchedulerProvider schedulerProvider) {
    mVMovierApi = vMovierApi;
    mSchedulerProvider = schedulerProvider;
  }

}
