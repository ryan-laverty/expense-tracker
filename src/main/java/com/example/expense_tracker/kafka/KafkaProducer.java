package com.example.expense_tracker.kafka;

import com.example.expense_tracker.model.Expense;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    private final KafkaTemplate<String, Expense> kafkaTemplate;

    public void sendExpense(String topic, Expense expense) {
        logger.info("Producing JSON message to kafka: {}", expense);
        kafkaTemplate.send(topic, expense);
    }
}
