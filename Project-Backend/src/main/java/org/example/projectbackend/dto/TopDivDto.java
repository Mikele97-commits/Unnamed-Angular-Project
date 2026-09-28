package org.example.projectbackend.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public record TopDivDto(
        @JsonProperty("minDmg") int minDmg,
        @JsonProperty("maxDmg") int maxDmg,
        @JsonProperty("armor") int armor,
        @JsonProperty("name")  String name,
        @JsonProperty("level") int level,
        @JsonProperty("currEnergy") int currEnergy,
        @JsonProperty("maxEnergy") int maxEnergy,
        @JsonProperty("currentHp") int currentHp,
        @JsonProperty("maxHp") int maxHp,
        @JsonProperty("currentExp") int currentExp,
        @JsonProperty("nxtLvlExp") int nxtLvlExp,
        @JsonProperty("questPoints") int questPoints,
        @JsonProperty("gold") int gold,
        @JsonProperty("regenTime") int[] regenTime

) {}
