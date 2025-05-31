package com.example.logprocessor;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Map;


public class LogProcessorConfiguration extends Configuration {

    @Valid
    private KafkaConfig kafka = new KafkaConfig();

    @JsonProperty("kafka")
    public KafkaConfig getKafka() {
        return kafka;
    }

    public static class KafkaConfig {
        @NotEmpty
        private String bootstrapServers;

        @NotEmpty
        private String topic;

        private String acks = "all";
        private int lingerMs = 0;
        private int retries = 0;

        @JsonProperty public String getBootstrapServers() { return bootstrapServers; }
        @JsonProperty public void setBootstrapServers(String s) { this.bootstrapServers = s; }

        @JsonProperty public String getTopic() { return topic; }
        @JsonProperty public void setTopic(String t) { this.topic = t; }

        @JsonProperty public String getAcks() { return acks; }
        @JsonProperty public void setAcks(String acks) { this.acks = acks; }

        @JsonProperty public int getLingerMs() { return lingerMs; }
        @JsonProperty public void setLingerMs(int l) { this.lingerMs = l; }

        @JsonProperty public int getRetries() { return retries; }
        @JsonProperty public void setRetries(int r) { this.retries = r; }

        /** Expose as java.util.Properties for Kafka producer */
        public Map<String, Object> toProducerProps() {
            return Map.of(
                    "bootstrap.servers", bootstrapServers,
                    "acks", acks,
                    "linger.ms", lingerMs,
                    "retries", retries,
                    "key.serializer", "org.apache.kafka.common.serialization.StringSerializer",
                    "value.serializer", "org.apache.kafka.common.serialization.StringSerializer"
            );
        }
    }
}
