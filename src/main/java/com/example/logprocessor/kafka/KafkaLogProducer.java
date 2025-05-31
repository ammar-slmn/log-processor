package com.example.logprocessor.kafka;

import com.example.logprocessor.LogProcessorConfiguration.KafkaConfig;
import com.example.logprocessor.core.LogEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.dropwizard.lifecycle.Managed;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Simple wrapper, owns the KafkaProducer instance and implements
 * DropWizard's Managed Interface so it closes cleanly on shutdown.
 */
public class KafkaLogProducer implements Managed {
    private static final Logger log = LoggerFactory.getLogger(KafkaLogProducer.class);

    private final KafkaProducer<String, String> producer;
    private final String topic;
    private final ObjectMapper mapper;

    public KafkaLogProducer(KafkaConfig cfg){
        Properties props = new Properties();
        props.putAll(cfg.toProducerProps());
        this.topic = cfg.getTopic();
        this.producer = new KafkaProducer<>(props);

        // Configure ObjectMapper to handle Java 8 time types
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.mapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public void send(LogEvent event) {
        try {
            String json = mapper.writeValueAsString(event);
            String key = event.getService();
            producer.send(new ProducerRecord<>(topic, key, json),
                (md, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send log to Kafka", ex);
                    } else {
                        log.debug("Successfully sent log to Kafka topic: {}", topic);
                    }
                });
        } catch(JsonProcessingException e) {
            log.error("Unable to serialise LogEvent", e);
        }
    }

    /** Managed Lifecycle ----------*/
    @Override
    public void start() {
        log.info("Kafka Producer started");
    }

    @Override
    public void stop() {
        try {
            producer.flush();
        } finally {
            producer.close();
        }
        log.info("Kafka Producer Closed");
    }
}