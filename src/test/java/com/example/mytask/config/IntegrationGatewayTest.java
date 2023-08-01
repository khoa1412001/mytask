package com.example.mytask.config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.gateway.MessagingGatewaySupport;
import org.springframework.messaging.MessageChannel;

@ExtendWith(MockitoExtension.class)
public class IntegrationGatewayTest {

  private volatile MessagingGatewaySupport messagingGateway;

  private final MessageChannel requestChannel = Mockito.mock(MessageChannel.class);

  private final MessageChannel replyChannel = Mockito.mock(MessageChannel.class);
  M
  @BeforeEach
  public void initializeSample() {
    this.messagingGateway = new MessagingGatewaySupport() {

    };
    this.messagingGateway.setRequestChannel(this.requestChannel);
    this.messagingGateway.setReplyChannel(this.replyChannel);

    this.messagingGateway.setBeanFactory(this.applicationContext);
    this.messagingGateway.afterPropertiesSet();
    this.messagingGateway.start();
    this.applicationContext.refresh();
    Mockito.when(this.messageMock.getHeaders())
        .thenReturn(new MessageHeaders(Collections.emptyMap()));
  }

  @AfterEach
  public void tearDown() {
    this.messagingGateway.stop();
    this.applicationContext.close();
  }

  @Test
  public void testIntegrationGateway() {

  }

}
