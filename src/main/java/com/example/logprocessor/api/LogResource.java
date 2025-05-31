package com.example.logprocessor.api;

import com.example.logprocessor.core.LogEvent;
import com.example.logprocessor.kafka.KafkaLogProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/logs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LogResource {
    private static final Logger log = LoggerFactory.getLogger(LogResource.class);
    private final KafkaLogProducer logProducer;

    public LogResource(KafkaLogProducer logProducer) {
        this.logProducer = logProducer;
    }

    @POST
    public Response ingestLog(@Valid LogEvent event) {
        log.info("Received log: {}", event);
        logProducer.send(event);
        return Response.accepted()
                .entity("{\"status\":\"queued\"}")
                .build();
    }
}
