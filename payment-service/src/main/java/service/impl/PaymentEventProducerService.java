package service.impl;

import dto.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentEventProducerService {
    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;
    private static final String TOPIC_PAYMENT_SUCCESS_EVENT = "payment.success";
    private static final String TOPIC_PAYMENT_FAILED_EVENT = "payment.failed";
    private static final String TOPIC_PAYMENT_CREATED_EVENT = "payment.created";

    public void sendPaymentSuccessEvent(PaymentEvent paymentEvent) {
        log.debug("Sending Payment Success Event={}", paymentEvent.id());
        kafkaTemplate.send(TOPIC_PAYMENT_SUCCESS_EVENT, paymentEvent);
        log.info("Sent Payment Success Event={}", paymentEvent.id());
    }

    public void sendPaymentFailedEvent(PaymentEvent paymentEvent) {
        log.debug("Sending Payment Failed Event={}", paymentEvent.id());
        kafkaTemplate.send(TOPIC_PAYMENT_FAILED_EVENT, paymentEvent);
        log.info("Sent Payment Failed Event={}", paymentEvent.id());
    }

    public void sendPaymentCreatedEvent(PaymentEvent paymentEvent) {
        log.debug("Sending Payment Created Event={}", paymentEvent.id());
        kafkaTemplate.send(TOPIC_PAYMENT_CREATED_EVENT, paymentEvent);
        log.info("Sent Payment Created Event={}", paymentEvent.id());
    }
}
