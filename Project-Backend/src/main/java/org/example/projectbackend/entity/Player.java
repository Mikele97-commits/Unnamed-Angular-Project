package org.example.projectbackend.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int currEnergy=100;
    private int maxEnergy=100;

    private int baseDmg=5;

    private int strength =5;
    private int endurance =5;
    private int perception =5;
    private int speed =5;
    private int dexterity =5;
    private int luck=5;

    private int baseHP=50;
    private int finalHP=baseHP+5*endurance;
    private int currentHP=finalHP;

    private int lvl=1;
    private int currentExp=0;
    private int nxtLvlExp = 100*(int)Math.pow(lvl, 1.5);

    private int questPoints = 12;
    private int dungPoints= 12;

    private int gold=0;


    @OneToOne(mappedBy = "player")
    User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="inventory_id")
    Inventory inventory;


}


