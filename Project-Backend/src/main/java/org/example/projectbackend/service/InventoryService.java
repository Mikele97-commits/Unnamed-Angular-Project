package org.example.projectbackend.service;

import org.example.projectbackend.entity.Inventory;
import org.example.projectbackend.entity.Player;
import org.example.projectbackend.entity.items.Armor;
import org.example.projectbackend.entity.items.ItemTemplate;
import org.example.projectbackend.entity.items.Weapon;
import org.example.projectbackend.repository.InventoryRepository;
import org.example.projectbackend.repository.ItemTemplateRepository;
import org.example.projectbackend.repository.PlayerRepository;
import org.example.projectbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    private final PlayerRepository playerRepository;
    UserRepository userRepository;
        ItemTemplateRepository itemTemplateRepository;
        InventoryRepository inventoryRepository;
        PlayerService playerService;
        public InventoryService(PlayerService playerService, InventoryRepository inventoryRepository, UserRepository userRepository, ItemTemplateRepository itemTemplateRepository, PlayerRepository playerRepository) {
            this.inventoryRepository = inventoryRepository;
            this.userRepository = userRepository;
            this.itemTemplateRepository = itemTemplateRepository;
            this.playerService = playerService;
            this.playerRepository = playerRepository;
        }

        public List<Inventory> getInventory(String username){
            return playerService.getPlayer(username).getInventory();
        }

        public void equipItem(String username,int itemId){
            Player player = playerService.getPlayer(username);
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
            } else if (type.equals("weapon")) {
                player.setEquippedWeapon(inventory);
            }
            playerRepository.save(player);

        }

        public void unequipItem(String username,int itemId){
            Player player = playerService.getPlayer(username);

            Inventory inventory = inventoryRepository.findById(itemId).orElse(null);
            String type = inventory.getType();
            if(type.equals("armor")){
                player.setEquippedArmor(null);
            } else if (type.equals("weapon")) {
                player.setEquippedWeapon(null);
            }
            inventory.setEquipped(false);
            playerRepository.save(player);
        }

        public String addItem(String username, int templateId){
            Player player = playerService.getPlayer(username);
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
            playerRepository.save(player);
            return itemTemplate.getName()+" +0";
        }
}
