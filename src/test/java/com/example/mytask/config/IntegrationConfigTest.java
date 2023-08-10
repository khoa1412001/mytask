package com.example.mytask.config;

import static com.example.mytask.constant.RoutePath.INPUT_CHANNEL;
import static com.example.mytask.constant.RoutePath.RESULT_CHANNEL;
import static com.example.mytask.constant.RoutePath.ROUTE_CHANNEL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlowBuilder;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.ServiceActivatingHandler;
import org.springframework.integration.router.HeaderValueRouter;

@ExtendWith(MockitoExtension.class)
class IntegrationConfigTest {

  @InjectMocks
  private IntegrationConfig integrationFlow;

  @Mock
  private IntegrationFlowBuilder mockFlowBuilder;

  @Mock
  private HandleLogRequest handleLogRequest;

  @Captor
  private ArgumentCaptor<ServiceActivatingHandler> serviceActivatingHandlerCapture;

  @Captor
  private ArgumentCaptor<HeaderValueRouter> headerValueRouterArgumentCaptor;

  @BeforeAll
  static void setupStatic() {
    mockStatic(IntegrationFlows.class);
  }

  @Test
  void shouldReturnResultChannel() {
    DirectChannel directChannel = integrationFlow.resultChannel();
    assertThat(directChannel.getComponentName()).isEqualTo(RESULT_CHANNEL);
  }

  @Test
  void shouldReturnInputChannel() {
    DirectChannel directChannel = integrationFlow.inputChannel();
    assertThat(directChannel.getComponentName()).isEqualTo(INPUT_CHANNEL);
  }

  @Test
  void myFlowShouldCallHandleOneTimesAndReturnResultChannel() {
    mockIntegrationFlowBuilder();
    integrationFlow.myFlow();

    verify(mockFlowBuilder).handle(serviceActivatingHandlerCapture.capture());
    verifyServiceActivatingHandler(serviceActivatingHandlerCapture.getAllValues());

    verify(mockFlowBuilder).route(headerValueRouterArgumentCaptor.capture());
    verifyHeaderValueRouter(headerValueRouterArgumentCaptor.getValue());

    verify(mockFlowBuilder).get();
  }

  private void verifyServiceActivatingHandler(List<ServiceActivatingHandler> handlers) {
    assertThat(handlers).hasSize(1);
    handlers.forEach(handler -> assertThat(handler).isInstanceOf(ServiceActivatingHandler.class));
  }

  private void verifyHeaderValueRouter(HeaderValueRouter router) {
    assertThat(router).isInstanceOf(HeaderValueRouter.class);
  }

  private void mockIntegrationFlowBuilder() {
    when(IntegrationFlows.from(any(DirectChannel.class))).thenReturn(mockFlowBuilder);
    when(mockFlowBuilder.handle(ArgumentMatchers.<ServiceActivatingHandler>any()))
        .thenReturn(mockFlowBuilder);
    when(mockFlowBuilder.route(any(HeaderValueRouter.class))).thenReturn(mockFlowBuilder);
  }
}
