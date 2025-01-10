package com.rfq.asset.configuration;

import com.rfq.asset.utils.Constants;
import jakarta.annotation.Nullable;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String consumerGroupId;

    @Value("${spring.kafka.properties.security.protocol}")
    private String securityProtocol;

    @Value("${spring.kafka.properties.sasl.mechanism : null}")
    private String saslMechanism;

    @Value("${spring.kafka.properties.sasl.jaas.config : null}")
    private String saslJaasConfig;

    @Value("${spring.kafka.properties.sasl.client.callback.handler.class : null}")
    private String saslClientCallbackHandlerClass;

    @Bean
    @ConditionalOnProperty(name = "spring.kafka.enable", havingValue = "true", matchIfMissing = true)
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        if (!Constants.KAFKA_PLAINTEXT_SECURITY_PROTOCOL.equalsIgnoreCase(securityProtocol)) { //If it is "PLAINTEXT" no security protocol require
            config.put("security.protocol", securityProtocol);
            config.put("sasl.mechanism", saslMechanism);
            config.put("sasl.jaas.config", saslJaasConfig);
            config.put("sasl.client.callback.handler.class", saslClientCallbackHandlerClass);
        }
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    @ConditionalOnProperty(name = "spring.kafka.enable", havingValue = "true", matchIfMissing = true)
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    // Consumer Configuration
    @Bean
    @ConditionalOnProperty(name = "spring.kafka.enable", havingValue = "true", matchIfMissing = true)
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        if (!Constants.KAFKA_PLAINTEXT_SECURITY_PROTOCOL.equalsIgnoreCase(securityProtocol)) { //If it is "PLAINTEXT" no security protocol require
            config.put("security.protocol", securityProtocol);
            config.put("sasl.mechanism", saslMechanism);
            config.put("sasl.jaas.config", saslJaasConfig);
            config.put("sasl.client.callback.handler.class", saslClientCallbackHandlerClass);
        }
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public Map<String, KafkaTemplate<String, String>> kafkaTemplateMap(@Nullable KafkaTemplate<String, String> kafkaTemplate) {
        Map<String, KafkaTemplate<String, String>> kafkaTemplateMap = new HashMap<>();
        if (kafkaTemplate != null) {kafkaTemplateMap.put("producer1", kafkaTemplate);}
        return kafkaTemplateMap.isEmpty() ? Collections.emptyMap() : kafkaTemplateMap;
    }


}
