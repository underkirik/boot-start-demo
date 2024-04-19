package com.example.bootstartdemo.core.impl;

import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TaskStopper {

  private final Map<String, ScheduledFuture<?>> tasks;

  public void stop(String key) {
    if (tasks.containsKey(key)) {
      var currentTask = tasks.get(key);
      currentTask.cancel(true);
      tasks.remove(key);
      log.debug("Tasks with key {} stopped", key);
    }
  }

  void registryTask(String id, ScheduledFuture<?> future) {
    tasks.put(id, future);
    log.debug("Task with key {} registry to tasks list", id);
  }

}
