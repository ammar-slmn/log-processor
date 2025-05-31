package com.example.logprocessor;

import com.example.logprocessor.api.LogResource;
import com.example.logprocessor.health.AppHealthCheck;
import com.example.logprocessor.kafka.KafkaLogProducer;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class LogProcessorApplication extends Application<LogProcessorConfiguration> {

    public static void main(final String[] args) throws Exception {
        new LogProcessorApplication().run(args);
    }

    @Override
    public String getName() {
        return "log-processor";
    }

    @Override
    public void initialize(final Bootstrap<LogProcessorConfiguration> bootstrap) {
        // nothing yet – later we’ll add Kafka bundles, metrics, etc.
    }

    @Override
    public void run(final LogProcessorConfiguration config, final Environment env) {
        // Health check
        env.healthChecks().register("app", new AppHealthCheck());

        // Create and manage Kafka Producer
        KafkaLogProducer kafka = new KafkaLogProducer(config.getKafka());
        env.lifecycle().manage(kafka);

        // REST resource with Kafka Producer dependency
        env.jersey().register(new LogResource(kafka));
    }
}
