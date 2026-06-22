package service.impl;

import dto.PaymentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentEventProducerService {
    private final KafkaTemplate<String, PaymentDto> kafkaTemplate;
    private static final String TOPIC_PAYMENT_SUCCESS_EVENT = "payment.success";
    private static final String TOPIC_PAYMENT_FAILED_EVENT = "payment.failed";

    public void sendPaymentSuccessEvent(PaymentDto paymentDto) {
        log.debug("Sending Payment Success Event={}", paymentDto.id());
        kafkaTemplate.send(TOPIC_PAYMENT_SUCCESS_EVENT, paymentDto);
        log.info("Sent Payment Success Event={}", paymentDto.id());
    }

    public void sendPaymentFailedEvent(PaymentDto paymentDto) {
        log.debug("Sending Payment Failed Event={}", paymentDto.id());
        kafkaTemplate.send(TOPIC_PAYMENT_FAILED_EVENT, paymentDto);
        log.info("Sent Payment Failed Event={}", paymentDto.id());
    }
}
