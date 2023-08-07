package com.example.mytask.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class HandleLogRequest {

  private static final Logger logger = LogManager.getLogger("PAYLOAD");

  public <T> T process(T payload) {
    logger.info(payload);
    return payload;
  }
}
