package org.example.projectbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AnswerDto(
        @JsonProperty("message") String message,
        @JsonProperty("success") boolean success,
        @JsonProperty("token") String token
) {
    public AnswerDto(String message, boolean success) {
        this(message, success, null);
    }
}
