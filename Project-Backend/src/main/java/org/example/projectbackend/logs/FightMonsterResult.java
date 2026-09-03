package org.example.projectbackend.logs;

import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
public class FightMonsterResult {
    private long id;
    private boolean playerWon;
    private int playerHpLeft;
    private int enemyHpLeft;
    private int expGained;
    private int goldGained;
    private List<FightLogEntry> log = new ArrayList<>();
}
