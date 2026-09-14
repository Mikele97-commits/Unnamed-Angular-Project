package org.example.projectbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int templateId;

    private boolean equipped=false;


    private String name;
    private String type;

    private int finalMinDmg;
    private int finalMaxDmg;

    private int finalArmor;

    private int finalPlus=0;

    private int price;
    private int lvl;



    @ManyToOne
    @JoinColumn(name="player_id")
    @JsonIgnore
    private Player player;

}
