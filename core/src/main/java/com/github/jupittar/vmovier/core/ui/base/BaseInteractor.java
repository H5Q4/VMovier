package com.github.jupittar.vmovier.core.ui.base;

import com.github.jupittar.vmovier.core.data.remote.VMovierApi;
import com.github.jupittar.vmovier.core.helper.SchedulerHelper;

public class BaseInteractor implements Contract.Interactor {

  protected VMovierApi mVMovierApi;
  protected SchedulerHelper mSchedulerHelper;

  public BaseInteractor(VMovierApi vMovierApi, SchedulerHelper schedulerHelper) {
    mVMovierApi = vMovierApi;
    mSchedulerHelper = schedulerHelper;
  }

}
