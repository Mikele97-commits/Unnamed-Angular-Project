package org.example.projectbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Monster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private int level;

    private int baseDamage;
    private int strength;
    private int endurance;
    private int perception;
    private int speed;
    private int dexterity;
    private int luck;

    private int hp;
    private int expReward;
    private int goldReward;
}
