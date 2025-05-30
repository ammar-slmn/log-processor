package com.example.logprocessor.core;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

public class LogEvent {

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssX", timezone = "UTC")
    private Instant timestamp;

    @NotBlank
    private String level;

    @NotBlank
    private String service;

    @NotBlank
    private String message;

    @JsonProperty
    public Instant getTimestamp() { return timestamp; }

    @JsonProperty
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    @JsonProperty
    public String getLevel() { return level; }

    @JsonProperty
    public void setLevel(String level) { this.level = level; }

    @JsonProperty
    public String getService() { return service; }

    @JsonProperty
    public void setService(String service) { this.service = service; }

    @JsonProperty
    public String getMessage() { return message; }

    @JsonProperty
    public void setMessage(String message) { this.message = message; }

    @Override public String toString() {
        return "LogEvent{" +
                "timestamp=" + timestamp +
                ", level='" + level + '\'' +
                ", service='" + service + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
