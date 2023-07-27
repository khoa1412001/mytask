package com.example.mytask.config;

import static com.example.mytask.constant.RoutePath.*;
import static com.example.mytask.constant.ServiceRoutePath.*;

import com.example.mytask.constant.ServiceRoutePath;
import java.lang.reflect.Field;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.springframework.integration.router.HeaderValueRouter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;


@EnableIntegration
@Configuration
public class IntegrationConfig {

  @Bean(name = RESULT_CHANNEL)
  public MessageChannel resultChannel() {
    return MessageChannels.direct(RESULT_CHANNEL).get();
  }

  @Bean
  public IntegrationFlow myFlow() {
    return IntegrationFlows.from(INPUT_CHANNEL)
        //use handle with log4j
        .log(Level.INFO, "DATA", m -> m.getPayload())
        .routeToRecipients(r -> r
            .recipient(LOG_INPUT_CHANNEL)
            .recipient(ROUTE_CHANNEL))
        .get();
  }

  @Router(inputChannel = ROUTE_CHANNEL)
  @Bean
  public HeaderValueRouter router() {
    HeaderValueRouter router = new HeaderValueRouter("action");
    for (Field f : ServiceRoutePath.class.getDeclaredFields()) {
      String channelName = f.getName();
      router.setChannelMapping(channelName, channelName);
    }
    return router;
  }

  @ServiceActivator(inputChannel = LOG_INPUT_CHANNEL)
  public <T> void logInput(T payload) {
    System.out.println("payload: " + String.valueOf(payload));
  }

  @ServiceActivator(inputChannel = ERROR_CHANNEL, outputChannel = RESULT_CHANNEL)
  public ResponseEntity errorChannel(MessagingException payload) {
//    payload.printStackTrace();
    System.out.println(payload.getFailedMessage().toString());
    System.out.println(payload.getFailedMessage().getPayload());
    return new ResponseEntity("TEST Response", HttpStatus.BAD_REQUEST);
  }

  @ServiceActivator(inputChannel = TEST_CHANNEL)
  public ResponseEntity test() {
//    System.out.println(LocalDateTime.now().getHour());
    throw new RuntimeException("New Error");
//    return new DataResponse("test");
  }

  private String getMessage(String s) {
    return s.substring(s.lastIndexOf(":") + 2);
  }
}
