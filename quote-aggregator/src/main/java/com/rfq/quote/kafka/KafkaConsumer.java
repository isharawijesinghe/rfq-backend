package com.rfq.quote.kafka;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private final Logger logger = LogManager.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "${spring.kafka.consumer.topic}", groupId = "${spring.kafka.consumer.group-id}", autoStartup = "${spring.kafka.enable}")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }

}
