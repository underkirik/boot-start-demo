package com.example.bootstartdemo.core.impl;

import com.example.bootstartdemo.configuration.properties.BackgroundTaskProperties;
import com.example.bootstartdemo.core.BackGroundTaskExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnExpression("'${background-executor.default-executor}'.equals('time)' and ${background-executor.enabled:true}")
public class TimeBackGroundTaskExecutor implements BackGroundTaskExecutor {

  private final BackgroundTaskProperties backgroundTaskProperties;
  private final ConcurrentTaskScheduler concurrentTaskScheduler;
  private final TaskStopper taskStopper;

  @Override
  public void schedule(String taskId, Runnable task) {
    log.debug("TimeBackgroundTaskExecutor: task with id {} preparing for schedule", taskId);
    var future = concurrentTaskScheduler.schedule(task,
        new PeriodicTrigger(backgroundTaskProperties.getTime().getInSecondsTime()));
    taskStopper.registryTask(taskId, future);
  }
}
