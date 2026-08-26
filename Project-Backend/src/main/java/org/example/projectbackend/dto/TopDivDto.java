package org.example.projectbackend.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public record TopDivDto(
        @JsonProperty("currEnergy") int currEnergy,
        @JsonProperty("maxEnergy") int maxEnergy,
        @JsonProperty("currentHp") int currentHp,
        @JsonProperty("maxHp") int maxHp,
        @JsonProperty("currentExp") int currentExp,
        @JsonProperty("nxtLvlExp") int nxtLvlExp,
        @JsonProperty("questPoints") int questPoints

) {}
