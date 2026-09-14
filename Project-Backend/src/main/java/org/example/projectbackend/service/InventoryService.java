package org.example.projectbackend.service;

import org.example.projectbackend.entity.Inventory;
import org.example.projectbackend.entity.Player;
import org.example.projectbackend.entity.User;
import org.example.projectbackend.entity.items.Armor;
import org.example.projectbackend.entity.items.ItemTemplate;
import org.example.projectbackend.entity.items.Weapon;
import org.example.projectbackend.repository.ItemTemplateRepository;
import org.example.projectbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
        UserRepository userRepository;
        ItemTemplateRepository itemTemplateRepository;
        public InventoryService(UserRepository userRepository, ItemTemplateRepository itemTemplateRepository) {
            this.userRepository = userRepository;
            this.itemTemplateRepository = itemTemplateRepository;
        }

        public List<Inventory> getInventory(String username){
            User user = userRepository.findByUsername(username).orElse(null);
            Player player = user.getPlayer();
            return player.getInventory();
        }

        public void addItem(String username, int templateId){
            System.out.println("Username:"+username);
            System.out.println("Item ID:"+ templateId);
            User user = userRepository.findByUsername(username).orElse(null);
            Player player = user.getPlayer();
            Inventory inventoryItem = new Inventory();
            ItemTemplate itemTemplate = itemTemplateRepository.findById(templateId).orElse(null);
            inventoryItem.setTemplateId(templateId);
            inventoryItem.setPlayer(player);
            inventoryItem.setName(itemTemplate.getName());
            inventoryItem.setType(itemTemplate.getType());
            inventoryItem.setLvl(itemTemplate.getLvl());
            inventoryItem.setPrice(itemTemplate.getPrice());
            if(itemTemplate instanceof Armor){
                inventoryItem.setFinalArmor(((Armor) itemTemplate).getArmor());
            }
            if(itemTemplate instanceof Weapon){
                inventoryItem.setFinalMinDmg(((Weapon) itemTemplate).getMinDmg());
                inventoryItem.setFinalMaxDmg(((Weapon) itemTemplate).getMaxDmg());
            }
            player.getInventory().add(inventoryItem);
            userRepository.save(user);
        }
}
