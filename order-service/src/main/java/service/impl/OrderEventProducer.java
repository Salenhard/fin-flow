package service.impl;

import dto.OrderCancelledEvent;
import dto.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventProducer {
    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplateOrderCreated;
    private final KafkaTemplate<String, OrderCancelledEvent> kafkaTemplateOrderCancelled;
    private static final String CREATED_ORDER_EVENT_TOPIC = "order.created";
    private static final String CANCELED_ORDER_EVENT_TOPIC = "order.cancled";

    public void sendOrderCreated(OrderCreatedEvent orderCreatedEvent) {
        log.debug("Sending order created event={} to topic {}", orderCreatedEvent.eventId(), CREATED_ORDER_EVENT_TOPIC);
        kafkaTemplateOrderCreated.send(CREATED_ORDER_EVENT_TOPIC, orderCreatedEvent);
        log.info("Order created event={} sent to topic {}", orderCreatedEvent.eventId(), CREATED_ORDER_EVENT_TOPIC);
    }

    public void sendOrderCanceled(OrderCancelledEvent orderCancelledEvent) {
        log.debug("Sending order canceled event={} to topic ={}", "test", CANCELED_ORDER_EVENT_TOPIC);
        kafkaTemplateOrderCancelled.send(CANCELED_ORDER_EVENT_TOPIC, orderCancelledEvent);
        log.info("Order canceled event={} sent to topic ={}", "test", CANCELED_ORDER_EVENT_TOPIC);
    }


}
