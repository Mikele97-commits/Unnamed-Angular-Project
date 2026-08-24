package org.example.projectbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RegisterDto(
        @JsonProperty("username") String username,
        @JsonProperty("password") String password,
        @JsonProperty("email") String email
){}
