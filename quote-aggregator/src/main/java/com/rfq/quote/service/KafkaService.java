package com.rfq.quote.service;

import com.rfq.quote.kafka.KafkaProducer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    private final Logger logger = LogManager.getLogger(KafkaService.class);

    private final KafkaProducer kafkaProducer;

    public KafkaService(KafkaProducer kafkaProducer){
        this.kafkaProducer = kafkaProducer;
    }

    public void sendMessage(String message){
        kafkaProducer.sendMessage("producer1", "my-topic", message);
    }
}
