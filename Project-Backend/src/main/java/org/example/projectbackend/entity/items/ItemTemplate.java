package org.example.projectbackend.entity.items;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ItemTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private int price;
    private int lvl;

    protected ItemTemplate() {}

    public ItemTemplate(String name, String type, int price, int lvl) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.lvl = lvl;
    }
}