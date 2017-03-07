package com.github.jupittar.vmovier.core.ui.base;

import com.github.jupittar.vmovier.core.data.remote.VMovierApi;
import com.github.jupittar.vmovier.core.provider.SchedulerProvider;

public class BaseInteractor implements Contract.Interactor {

  protected VMovierApi mVMovierApi;
  protected SchedulerProvider mSchedulerProvider;

  public BaseInteractor(VMovierApi vMovierApi, SchedulerProvider schedulerProvider) {
    mVMovierApi = vMovierApi;
    mSchedulerProvider = schedulerProvider;
  }

}
