package org.example.projectbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.example.projectbackend.logs.FightLogEntry;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class FightResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String monsterName;
    private boolean playerWon;
    private int expGained;
    private int goldGained;
    private String foughtAt;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<FightLogEntry> log;
}
