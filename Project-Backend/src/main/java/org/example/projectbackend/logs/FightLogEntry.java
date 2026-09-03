package org.example.projectbackend.logs;

import lombok.Setter;

@Setter
public class FightLogEntry {
    private int round;
    private String actor;
    private String doubleHit;
    private String message;
    private String doubleHitMessage;
    private String endOfFight;
}
