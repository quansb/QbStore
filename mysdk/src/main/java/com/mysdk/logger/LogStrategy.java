package com.mysdk.logger;

public interface LogStrategy {

  void log(int priority, String tag, String message);
}
