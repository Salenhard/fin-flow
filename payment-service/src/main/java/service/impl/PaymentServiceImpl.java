package service.impl;

import dto.OrderCreatedEvent;
import dto.PaymentEvent;
import dto.PaymentResult;
import entity.Payment;
import entity.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import repository.PaymentRepository;
import service.PaymentService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentEventProducerService paymentEventProducerService;
    private final MockPaymentGateway mockPaymentGateway;

    @Override
    public void processOrderCreated(OrderCreatedEvent event) {
        log.debug("Received OrderCreatedEvent: orderId={}", event.orderId());

        if (paymentRepository.existsByOrderId(event.orderId())) {
            log.warn("Payment already exists for orderId={}", event.orderId());
            return;
        }

        Payment payment = new Payment();
        payment.setOrderId(event.orderId());
        payment.setUserId(event.userId());
        payment.setAmount(event.amount());
        payment.setStatus(Status.PENDING);
        payment.setCreatedAt(Timestamp.from(Instant.now()));
        payment = paymentRepository.save(payment);

        PaymentEvent paymentEvent = new PaymentEvent(
                payment.getId(),
                payment.getOrderId(),
                payment.getUserId(),
                payment.getAmount(),
                Status.PENDING,
                payment.getCreatedAt(),
                payment.getUpdatedAt());

        paymentEventProducerService.sendPaymentCreatedEvent(paymentEvent);

        PaymentResult paymentResult = mockPaymentGateway.process(payment.getId(), payment.getOrderId(), payment.getAmount());

        payment.setStatus(paymentResult.status());
        payment.setUpdatedAt(Timestamp.from(Instant.now()));
        payment = paymentRepository.save(payment);

        paymentEvent = new PaymentEvent(
                payment.getId(),
                payment.getOrderId(),
                payment.getUserId(),
                payment.getAmount(),
                paymentResult.status(),
                payment.getCreatedAt(),
                payment.getUpdatedAt());

        switch (paymentResult.status()) {
            case FAILED -> paymentEventProducerService.sendPaymentFailedEvent(paymentEvent);
            case SUCCESS -> paymentEventProducerService.sendPaymentSuccessEvent(paymentEvent);
        }
        log.info("Payment processed: orderId={}, status={}", event.orderId(), payment.getStatus());
    }

    @Override
    public PaymentEvent findPaymentById(UUID paymentId) {
        paymentRepository.findByPaymentId(paymentId);
        return null;
    }

    @Override
    public PaymentEvent findPaymentByOrderId(UUID orderId) {
        paymentRepository.findByOrderId(orderId);
        return null;
    }

    @Override
    public void deleteById(UUID id) {
        paymentRepository.deleteById(id);
    }
}
