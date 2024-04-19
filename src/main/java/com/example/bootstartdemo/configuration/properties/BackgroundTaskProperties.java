package com.example.bootstartdemo.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Data
@ConfigurationProperties(prefix = "background-executor")
public class BackgroundTaskProperties {

  private boolean enabled;
  //time; cron
  private String defaultExecutor;
  private int tasksSize;
  @NestedConfigurationProperty
  private CronExecutorProperties cron;
  @NestedConfigurationProperty
  private TimeExecutorProperties time;

}
