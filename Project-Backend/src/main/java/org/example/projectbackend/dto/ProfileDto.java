package org.example.projectbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProfileDto(
@JsonProperty("username") String username,
@JsonProperty("lvl") int lvl,
@JsonProperty("equippedWeapon") String equippedWeapon,
@JsonProperty("equippedArmor") String equippedArmor,
@JsonProperty("currentHp") int currentHp,
@JsonProperty("maxHP") int maxHp

) {}
