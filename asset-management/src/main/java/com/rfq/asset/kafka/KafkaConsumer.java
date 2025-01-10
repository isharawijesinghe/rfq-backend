package com.rfq.asset.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "${spring.kafka.consumer.topic}", groupId = "${spring.kafka.consumer.group-id}",autoStartup = "${spring.kafka.enable}")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }

}
