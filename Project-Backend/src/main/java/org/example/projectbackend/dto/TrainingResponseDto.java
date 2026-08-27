package org.example.projectbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TrainingResponseDto(
        @JsonProperty("message") String message,
        @JsonProperty("success") boolean success,
        @JsonProperty("trainingStatsDto") TrainingDto trainingDto
){}
