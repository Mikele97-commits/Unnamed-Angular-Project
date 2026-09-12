package org.example.projectbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.projectbackend.entity.items.Item;

@Entity
@Getter
@Setter
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;


    @OneToOne(mappedBy = "inventory")
    Player player;
}
