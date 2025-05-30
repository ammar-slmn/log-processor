package com.example.logprocessor.api;

import com.example.logprocessor.core.LogEvent;
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

    @POST
    public Response ingestLog(@Valid LogEvent event) {
        // For Phase 1 merely acknowledge we received it;
        // Phase 2 will push to Kafka.
        log.info("Received log: {}", event);
        return Response.accepted().entity("{\"status\":\"queued\"}").build();
    }
}
