package com.example.mytask.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;


import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.router.ExpressionEvaluatingRouter;
import org.springframework.integration.router.HeaderValueRouter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;

@EnableIntegration
@Configuration
public class IntegrationConfig {

  @Bean
  public MessageChannel OUTPUT_CHANNEL() {
    return new DirectChannel();
  }

  //  @ServiceActivator(inputChannel = "router.channel")
//  @Bean
//  public PayloadTypeRouter router() {
//    PayloadTypeRouter router = new PayloadTypeRouter();
//    router.set
//  }
//.handle(System.out::println)
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
    router.setChannelMapping("GET_TASK", "GET_TASK_CHANNEL");
    router.setChannelMapping("EDIT_TASK", "EDIT_TASK_CHANNEL");
    router.setChannelMapping("CALCULATE_DEADLINE", "CALCULATE_DEADLINE_CHANNEL");
    router.setChannelMapping("LOG_WORK", "LOG_WORK_CHANNEL");
    return router;
  }

  @ServiceActivator(inputChannel = "LOG_INPUT_CHANNEL")
  public <T> T logInput(T payload) {
    System.out.println("test");
    System.out.println(payload);
    return payload;
  }
}
