package service.impl;

import dto.PaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import repository.PaymentRepository;
import service.PaymentService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentEventProducerService paymentEventProducerService;

    @Override
    public void sendPayment(PaymentDto paymentDto) {

    }

    @Override
    public PaymentDto findPaymentById(UUID id) {
        return null;
    }

    @Override
    public PaymentDto findPaymentByOrderId(UUID orderId) {
        return null;
    }

    @Override
    public Page<PaymentDto> findPaymentByOrderId(UUID orderId, Pageable pageable) {
        return null;
    }

    @Override
    public void deleteById(UUID id) {

    }
}
