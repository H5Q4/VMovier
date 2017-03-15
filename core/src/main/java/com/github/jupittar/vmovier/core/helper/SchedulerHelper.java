package com.github.jupittar.vmovier.core.helper;

import rx.Scheduler;

public interface SchedulerHelper {

  Scheduler mainThread();

  Scheduler backgroundThread();

}
