package com.example.mytask.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class HandleLogRequest {

  private static final Logger logger = LogManager.getLogger("PAYLOAD");

  public Message process(Message payload) {
    logger.info(payload.getPayload());
    return payload;
  }
}
