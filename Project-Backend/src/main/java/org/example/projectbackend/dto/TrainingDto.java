package org.example.projectbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/* strength: number;
    endurance: number;
    perception: number;
    speed: number;
    dexterity: number;
    luck: number;

    strengthCost: number;
    enduranceCost: number;
    perceptionCost: number;
    speedCost: number;
    dexterityCost: number;
    luckCost: number;*/

    public record TrainingDto(
            @JsonProperty("strength") int strength,
            @JsonProperty("endurance") int endurance,
            @JsonProperty("perception") int perception,
            @JsonProperty("speed") int speed,
            @JsonProperty("dexterity") int dexterity,
            @JsonProperty("luck") int luck
    ){}

