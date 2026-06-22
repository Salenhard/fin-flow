package service;

import dto.PaymentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PaymentService {

    public void sendPayment(PaymentDto paymentDto);

    public PaymentDto findPaymentById(UUID id);

    public PaymentDto findPaymentByOrderId(UUID orderId);

    public Page<PaymentDto> findPaymentByOrderId(UUID orderId, Pageable pageable);

    public void deleteById(UUID id);

}
