package org.example.projectbackend.service;

import org.example.projectbackend.entity.Inventory;
import org.example.projectbackend.entity.Player;
import org.example.projectbackend.entity.User;
import org.example.projectbackend.entity.items.Armor;
import org.example.projectbackend.entity.items.ItemTemplate;
import org.example.projectbackend.entity.items.Weapon;
import org.example.projectbackend.repository.InventoryRepository;
import org.example.projectbackend.repository.ItemTemplateRepository;
import org.example.projectbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
        UserRepository userRepository;
        ItemTemplateRepository itemTemplateRepository;
        InventoryRepository inventoryRepository;
        public InventoryService(InventoryRepository inventoryRepository, UserRepository userRepository, ItemTemplateRepository itemTemplateRepository) {
            this.inventoryRepository = inventoryRepository;
            this.userRepository = userRepository;
            this.itemTemplateRepository = itemTemplateRepository;
        }

        public List<Inventory> getInventory(String username){
            User user = userRepository.findByUsername(username).orElse(null);
            Player player = user.getPlayer();
            return player.getInventory();
        }

        public void equipItem(String username,int itemId){
            User user = userRepository.findByUsername(username).orElse(null);
            Player player = user.getPlayer();
            Inventory inventory = inventoryRepository.findById(itemId).orElse(null);
            String type = inventory.getType();
            if(inventoryRepository.findByPlayerAndTypeAndEquippedTrue(player,type).isEmpty()){
                inventory.setEquipped(true);
                inventoryRepository.save(inventory);
            }else {
                Inventory nowEquipped=inventoryRepository.findByPlayerAndTypeAndEquippedTrue(player,type).get();
                nowEquipped.setEquipped(false);
                inventory.setEquipped(true);
                inventoryRepository.save(nowEquipped);
                inventoryRepository.save(inventory);
            }
            if(type.equals("armor")){
                player.setEquippedArmor(inventory);
                userRepository.save(user);
            } else if (type.equals("weapon")) {
                player.setEquippedWeapon(inventory);
                userRepository.save(user);
            }

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
