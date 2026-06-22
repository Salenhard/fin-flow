package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
    private UUID userId;
    @Column(unique = true, nullable = false)
    private UUID orderId;
    private BigDecimal amount;
    private Status status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
