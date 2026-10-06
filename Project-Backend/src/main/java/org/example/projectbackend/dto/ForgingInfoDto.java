package org.example.projectbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ForgingInfoDto (

        @JsonProperty("name") String name,
        @JsonProperty("type") String type,
        @JsonProperty("cost") int cost,
        @JsonProperty("minDmg") int minDmg,
        @JsonProperty("maxDmg") int maxDmg,
        @JsonProperty("armor") int armor,
        @JsonProperty("finalPlus") int finalPlus
) {}
