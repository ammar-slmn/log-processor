package com.example.logprocessor;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * Bind values from config.yml here.  We’ll add Kafka settings in Phase 2.
 */
public class LogProcessorConfiguration extends Configuration {

    @Valid
    @NotEmpty
    private String kafkaBootstrapServers = "localhost:9092";

    @JsonProperty("kafkaBootstrapServers")
    public String getKafkaBootstrapServers() {
        return kafkaBootstrapServers;
    }

    @JsonProperty("kafkaBootstrapServers")
    public void setKafkaBootstrapServers(String kafkaBootstrapServers) {
        this.kafkaBootstrapServers = kafkaBootstrapServers;
    }
}
