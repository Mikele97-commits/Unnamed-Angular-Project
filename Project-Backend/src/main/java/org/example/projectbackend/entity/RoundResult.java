package org.example.projectbackend.entity;

public class RoundResult {
    public boolean endOfFight;
    public int attackerHP;
    public int defenderHP;

    public RoundResult(boolean endOfFight, int attackerHP, int defenderHP) {
        this.endOfFight = endOfFight;
        this.attackerHP = attackerHP;
        this.defenderHP = defenderHP;
    }
}
