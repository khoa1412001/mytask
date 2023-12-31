package com.example.mytask.config;

import static com.example.mytask.constant.RoutePath.*;

import org.springframework.http.ResponseEntity;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

@MessagingGateway()
public interface IntegrationGateway {

  @Gateway(requestChannel = INPUT_CHANNEL, replyChannel = RESULT_CHANNEL)
  <T> ResponseEntity<Object> process(@Payload T payload, @Header("action") String action);

  @Gateway(requestChannel = INPUT_CHANNEL, replyChannel = RESULT_CHANNEL, payloadExpression = "''")
  ResponseEntity<Object> process(@Header("action") String action);

}
