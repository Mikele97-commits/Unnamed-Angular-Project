package org.example.projectbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.projectbackend.logs.FightLogEntry;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Getter
@Setter
public class FightResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String attacker;
    private String defender;
    private boolean playerWon;
    private int expGained;
    private int goldGained;
    private String foughtAt;

    private String loot;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<FightLogEntry> log;

    @ManyToOne
    @JoinColumn(name="fightResult_id")
    Player player;
}
