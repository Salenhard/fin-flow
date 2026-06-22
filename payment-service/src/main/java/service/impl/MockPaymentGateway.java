package service.impl;

import dto.PaymentResult;
import entity.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
public class MockPaymentGateway {
    private final Random random = new Random();
    private static final BigDecimal LIMIT =
            BigDecimal.valueOf(100_000);

    public PaymentResult process(UUID paymentId, UUID orderId, BigDecimal amount) {
        try {
            Thread.sleep(
                    ThreadLocalRandom.current()
                            .nextInt(1000, 3000)
            );
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (random.nextInt(100) < 20) {
            String reason = "Payment timeout";
            log.warn(
                    "Payment failed. paymentId={}, reason={}",
                    paymentId,
                    reason
            );
            return new PaymentResult(Status.FAILED, reason);
        }

        if (amount.compareTo(LIMIT) > 0) {
            String reason = "Insufficient funds";
            log.warn(
                    "Payment failed. paymentId={}, reason={}",
                    paymentId,
                    reason
            );
            return new PaymentResult(Status.FAILED, "Insufficient funds");
        }

        log.info(
                "Payment completed. paymentId={}, status={}",
                paymentId,
                Status.SUCCESS
        );
        return new PaymentResult(Status.SUCCESS, "Payment processed successfully");
    }
}
