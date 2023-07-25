package com.example.mytask.config;

import com.example.mytask.payload.DataResponse;
import java.time.LocalDateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;


import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.router.HeaderValueRouter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;


@EnableIntegration
@Configuration
public class IntegrationConfig {

  @Bean
  public MessageChannel OUTPUT_CHANNEL() {
//    MessageChannels.direct("LOG").get();
    return new DirectChannel();
  }

  @Bean
  public IntegrationFlow myFlow() {
    return IntegrationFlows.from("INPUT_CHANNEL")
//        .transform(Transformers.toMap())
//        .handle(System.out::println).get();
//        .channel("OUTPUT_CHANNEL").get();

        .routeToRecipients(r -> r
            .recipient("LOG_INPUT_CHANNEL")
            .recipient("ROUTING_CHANNEL"))
        .get();
  }

  @Router(inputChannel = "ROUTING_CHANNEL")
  @Bean
  public HeaderValueRouter router() {
    HeaderValueRouter router = new HeaderValueRouter("action");
    //user
    router.setChannelMapping("GET_USER", "GET_USER_CHANNEL");
    router.setChannelMapping("CREATE_USER", "CREATE_USER_CHANNEL");
    router.setChannelMapping("EDIT_USER", "EDIT_USER_CHANNEL");
    //task
    router.setChannelMapping("CREATE_TASK", "CREATE_TASK_CHANNEL");
    router.setChannelMapping("GET_TASK", "GET_TASK_CHANNEL");
    router.setChannelMapping("EDIT_TASK", "EDIT_TASK_CHANNEL");
    router.setChannelMapping("ASSIGN_TASK", "ASSIGN_TASK_CHANNEL");
    router.setChannelMapping("CALCULATE_DEADLINE", "CALCULATE_DEADLINE_CHANNEL");
    router.setChannelMapping("LOG_WORK", "LOG_WORK_CHANNEL");
    router.setChannelMapping("TEST", "TEST");
    return router;
  }

  @ServiceActivator(inputChannel = "LOG_INPUT_CHANNEL")
  public <T> void logInput(T payload) {
    System.out.println("payload: " + String.valueOf(payload));
  }

  @ServiceActivator(inputChannel = "ERROR_CHANNEL", outputChannel = "OUTPUT_CHANNEL")
  public DataResponse errorChannel(MessagingException payload) {
    payload.printStackTrace();
    System.out.println(payload.getMessage());
    return new DataResponse("test error");
  }

  @ServiceActivator(inputChannel = "TEST")
  public DataResponse test() {
//    System.out.println(LocalDateTime.now().getHour());
    throw new RuntimeException("New Error");
//    return new DataResponse("test");
  }

  private String getMessage(String s) {
    return s.substring(s.lastIndexOf(":") + 2);
  }
}
