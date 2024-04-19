package com.example.bootstartdemo.core;

public interface BackGroundTaskExecutor {
  void schedule(String taskId, Runnable task);

}
