package com.github.jupittar.vmovier.core.provider;

import rx.Scheduler;

public interface SchedulerProvider {

  Scheduler mainThread();

  Scheduler backgroundThread();

}
