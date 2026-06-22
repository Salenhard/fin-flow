package service;

import dto.OrderCreatedEvent;
import dto.PaymentEvent;

import java.util.UUID;

public interface PaymentService {

    public void processOrderCreated(OrderCreatedEvent event);

    public PaymentEvent findPaymentById(UUID id);

    public PaymentEvent findPaymentByOrderId(UUID orderId);

    public void deleteById(UUID id);

}
