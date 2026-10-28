package org.example.projectbackend.entity;

public class Combatant {
    public String name;
    public int level;
    public int perception;
    public int dexterity;
    public int speed;
    public int luck;
    public int armor;
    public int currentHp;
    public int[] damageRange;

    public Combatant(String name, int level, int perception, int dexterity, int speed, int luck, int armor, int currentHp, int[] damageRange) {
        this.name = name;
        this.level = level;
        this.perception = perception;
        this.dexterity = dexterity;
        this.speed = speed;
        this.luck = luck;
        this.armor = armor;
        this.currentHp = currentHp;
        this.damageRange = damageRange;
    }
}