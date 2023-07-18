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
        .channel("routingChannel").get();
  }

  @Router(inputChannel = "routingChannel")
  @Bean
  public HeaderValueRouter router() {
    HeaderValueRouter router = new HeaderValueRouter("action");
    router.setChannelMapping("GET_USER", "GET_USER_CHANNEL");
    return router;
  }
}
