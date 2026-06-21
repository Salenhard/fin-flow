package service.impl;

import dto.OrderCancelledDto;
import dto.OrderCreatedDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventProducer {
    private final KafkaTemplate<String, OrderCreatedDto> kafkaTemplateOrderCreated;
    private final KafkaTemplate<String, OrderCancelledDto> kafkaTemplateOrderCancelled;
    private static final String CREATED_ORDER_EVENT_TOPIC = "order.created";
    private static final String CANCELED_ORDER_EVENT_TOPIC = "order.cancled";

    public void sendOrderCreated(OrderCreatedDto orderCreatedDto) {
        log.debug("Sending order created event={} to topic {}", orderCreatedDto.eventId(), CREATED_ORDER_EVENT_TOPIC);
        kafkaTemplateOrderCreated.send(CREATED_ORDER_EVENT_TOPIC, orderCreatedDto);
        log.info("Order created event={} sent to topic {}", orderCreatedDto.eventId(), CREATED_ORDER_EVENT_TOPIC);
    }

    public void sendOrderCanceled(OrderCancelledDto orderCancelledDto) {
        log.debug("Sending order canceled event={} to topic ={}", "test", CANCELED_ORDER_EVENT_TOPIC);
        kafkaTemplateOrderCancelled.send(CANCELED_ORDER_EVENT_TOPIC, orderCancelledDto);
        log.info("Order canceled event={} sent to topic ={}", "test", CANCELED_ORDER_EVENT_TOPIC);
    }


}
