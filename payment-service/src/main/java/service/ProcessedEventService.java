package service;

import entity.Payment;
import entity.ProcessedEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProcessedEventService {

    public void deleteByOrderId(UUID orderId);

    public void deleteById(Long id);

    public Page<ProcessedEvent> findProcessedEventByOrderId(UUID orderId, Pageable pageable);

    public Page<ProcessedEvent> findProcessedEventById(Long id, Pageable pageable);

    public ProcessedEvent findById(Long id);

    public ProcessedEvent save(ProcessedEvent processedEvent);

    public ProcessedEvent update(ProcessedEvent processedEvent, Long id);

}
