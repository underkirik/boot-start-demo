package com.example.bootstartdemo.configuration.analyzer;

import com.example.bootstartdemo.exception.BackgroundTaskPropertyException;
import java.text.MessageFormat;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

public class BackgroundTaskPropertyFailureAnalyzer extends
    AbstractFailureAnalyzer<BackgroundTaskPropertyException> {

  @Override
  protected FailureAnalysis analyze(Throwable rootFailure, BackgroundTaskPropertyException cause) {
    return new FailureAnalysis(
        MessageFormat.format("Exception when try to set property: {}", cause.getMessage()),
        "set-application-properties", cause);
  }
}
