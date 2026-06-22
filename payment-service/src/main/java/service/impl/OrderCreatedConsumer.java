package service.impl;

import dto.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import service.PaymentService;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreatedConsumer {

    private final PaymentService paymentService;

    @KafkaListener(
            topics = "order.created",
            groupId = "payment-service"
    )
    public void consume(OrderCreatedEvent event) {
        log.info("Received OrderCreatedEvent: orderId={}", event.orderId());
        paymentService.processOrderCreated(event);
    }
}