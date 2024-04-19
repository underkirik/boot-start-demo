package com.example.bootstartdemo.configuration;

import com.example.bootstartdemo.configuration.properties.BackgroundTaskProperties;
import com.example.bootstartdemo.core.impl.TaskStopper;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.concurrent.ScheduledFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

@Configuration
@ConditionalOnProperty("background-executor.enabled")
@EnableConfigurationProperties(BackgroundTaskProperties.class)
public class BackgroundConfiguration {

  private static final Logger log = LoggerFactory.getLogger(BackgroundConfiguration.class);

  @Bean
  public ConcurrentTaskScheduler concurrentTaskScheduler() {
    return new ConcurrentTaskScheduler();
  }

  @Bean
  public TaskStopper taskStopper(BackgroundTaskProperties properties) {
    return new TaskStopper(new LinkedHashMap<>() {
      @Override
      protected boolean removeEldestEntry(Entry<String, ScheduledFuture<?>> eldest) {
        if (size() > properties.getTasksSize()) {
          eldest.getValue().cancel(true);
          log.debug("Eldest task preparing for delete. ID is {}", eldest.getKey());
          return true;
        }
        return false;
      }
    });
  }

}
