package com.example.mytask.config;

import com.example.mytask.payload.DataResponse;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

@MessagingGateway(errorChannel = "ERROR_CHANNEL")
public interface IntegrationGateway {

  @Gateway(requestChannel = "INPUT_CHANNEL", replyChannel = "OUTPUT_CHANNEL")
  <T> DataResponse invoke(@Payload T payload, @Header("action") String action);

}
