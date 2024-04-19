package com.example.bootstartdemo.configuration.properties;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import lombok.Data;
import org.springframework.boot.convert.DurationUnit;

@Data
public class TimeExecutorProperties {
  @DurationUnit(ChronoUnit.SECONDS)
  private Duration inSecondsTime;
}
