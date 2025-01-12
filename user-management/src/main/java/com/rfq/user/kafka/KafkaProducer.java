package com.rfq.user.kafka;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class KafkaProducer {

    private final Logger logger = LogManager.getLogger(KafkaProducer.class);

    private final Map<String, KafkaTemplate<String, String>> kafkaTemplateMap;

    public KafkaProducer(Map<String, KafkaTemplate<String, String>> kafkaTemplateMap) {
        this.kafkaTemplateMap = kafkaTemplateMap;
    }

    public void sendMessage(String producerKey, String topic, String message) {
        KafkaTemplate<String, String> kafkaTemplate = kafkaTemplateMap.get(producerKey);
        if (kafkaTemplate != null) {
            kafkaTemplate.send(topic, message);
        } else {
            throw new IllegalArgumentException("Producer not enabled or not found for key: " + producerKey);
        }
    }

}
