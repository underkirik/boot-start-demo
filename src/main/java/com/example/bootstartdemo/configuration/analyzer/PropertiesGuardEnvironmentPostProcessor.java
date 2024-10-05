package com.example.bootstartdemo.configuration.analyzer;

import com.example.bootstartdemo.exception.BackgroundTaskPropertyException;
import java.util.Objects;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.StringUtils;

public class PropertiesGuardEnvironmentPostProcessor implements EnvironmentPostProcessor {

  @Override
  public void postProcessEnvironment(ConfigurableEnvironment environment,
      SpringApplication application) {
    String defaultExecutor = environment.getProperty("background-executor.default-executor");
    String cronExpression = environment.getProperty("background-executor.cron.expression");
    String timeValue = environment.getProperty("background-executor.time.in-seconds-time");
    String tasksSize = environment.getProperty("background-executor.tasksSize");
    boolean enabled = Boolean.parseBoolean(environment.getProperty("background-executor.enabled"));
    if (enabled) {
      check(defaultExecutor, cronExpression, timeValue, tasksSize);
    }
  }

  private void check(String defaultExecutor, String cronExpression, String timeValue,
      String tasksSize) {
    boolean isInvalidTime =
        !StringUtils.hasText(defaultExecutor) || (!Objects.equals(defaultExecutor, "cron")
            && !Objects.equals(defaultExecutor, "time"));
    if (isInvalidTime) {
      throw new BackgroundTaskPropertyException(
          "Property background-executor.default-executor must be cron or time!");
    }
    if (Objects.equals(defaultExecutor, "cron") && !StringUtils.hasText(cronExpression)) {
      throw new BackgroundTaskPropertyException("Invalid cron expression for 'cron' type!");
    }
    if (Objects.equals(defaultExecutor, "time") && !StringUtils.hasText(timeValue)) {
      throw new BackgroundTaskPropertyException("Invalid time value for 'time' type!");
    }
    if (!StringUtils.hasText(tasksSize) || tasksSize.matches("-?\\d*")
        || Integer.parseInt(tasksSize) <= 0) {
      throw new BackgroundTaskPropertyException("invalid tasks size value!");
    }
  }
}
