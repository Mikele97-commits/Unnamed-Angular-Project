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

    private int minDamage;
    private int maxDamage;
    private int armor;
    private int strength;
    private int endurance;
    private int perception;
    private int speed;
    private int dexterity;
    private int luck;

    private int hp;
    private int expReward;
    private int goldReward;


    public Monster(String name, String location, int level, int minDamage, int maxDamage, int armor, int strength, int endurance, int perception, int speed, int dexterity, int luck, int hp, int expReward, int goldReward) {
        this.name = name;
        this.location = location;
        this.level = level;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.armor = armor;
        this.strength = strength;
        this.endurance = endurance;
        this.perception = perception;
        this.speed = speed;
        this.dexterity = dexterity;
        this.luck = luck;
        this.hp = hp;
        this.expReward = expReward;
        this.goldReward = goldReward;
    }

    public Monster() {
    }
}
