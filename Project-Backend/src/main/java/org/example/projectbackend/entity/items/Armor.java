package org.example.projectbackend.entity.items;

import jakarta.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Armor extends Item{

    private int armor;
    private int plus;

    protected Armor() {}
    public Armor(String name, int price, int lvl, int armor) {
        super(name, "armor", price, lvl);
        this.armor = armor;
        this.plus=0;
    }
}