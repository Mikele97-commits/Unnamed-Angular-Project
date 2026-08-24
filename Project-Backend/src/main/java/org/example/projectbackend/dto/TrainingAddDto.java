package org.example.projectbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TrainingAddDto (
        @JsonProperty("stat") String stat
){}
