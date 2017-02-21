package com.github.jupittar.vmovier.core.helper;

import rx.Scheduler;

public interface SchedulerProvider {

  Scheduler mainThread();

  Scheduler backgroundThread();

}
