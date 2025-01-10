package com.rfq.asset.service;

import com.rfq.asset.kafka.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    private final Logger logger = LoggerFactory.getLogger(KafkaService.class);


    private final KafkaProducer kafkaProducer;

    public KafkaService(KafkaProducer kafkaProducer){
        this.kafkaProducer = kafkaProducer;
    }

    public void sendMessage(String message){
        kafkaProducer.sendMessage("producer1", "my-topic", message);
    }
}
