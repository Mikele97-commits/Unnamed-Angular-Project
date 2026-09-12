package org.example.projectbackend.entity.items;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Weapon extends Item {


    private int lvl;
    private int minDmg;
    private int maxDmg;

    private int plus;

    public Weapon(String name, int price, int lvl, int minDmg, int maxDmg) {
        super(name, "weapon", price,lvl);
        this.minDmg = minDmg;
        this.maxDmg = maxDmg;
        this.plus = 0;
    }

    protected Weapon() {}
}