package com.example.mytask.service;

import com.example.mytask.model.User;
import org.json.JSONObject;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

@MessagingGateway
public interface IntegrationGateway {

  @Gateway(requestChannel = "INPUT_CHANNEL", replyChannel = "OUTPUT_CHANNEL")
  <T> T invoke(@Payload T payload, @Header("action") String action);

}
