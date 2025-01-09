package com.rfq.asset.service;

import com.rfq.asset.kafka.KafkaProducer;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    private final KafkaProducer kafkaProducer;

    public KafkaService(KafkaProducer kafkaProducer){
        this.kafkaProducer = kafkaProducer;
    }

    public void sendMessage(String message){
        kafkaProducer.sendMessage("my-topic", message);
    }
}
