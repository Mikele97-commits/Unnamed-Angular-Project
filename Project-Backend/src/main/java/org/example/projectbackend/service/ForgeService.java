package org.example.projectbackend.service;

import org.example.projectbackend.dto.ForgingInfoDto;
import org.example.projectbackend.entity.Inventory;
import org.example.projectbackend.entity.Player;
import org.example.projectbackend.entity.User;
import org.example.projectbackend.entity.items.Armor;
import org.example.projectbackend.entity.items.ItemTemplate;
import org.example.projectbackend.entity.items.Weapon;
import org.example.projectbackend.repository.*;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ForgeService {

    private final UserRepository userRepository;
    InventoryRepository inventoryRepository;
    ItemTemplateRepository itemTemplateRepository;
    WeaponTemplateRepository weaponTemplateRepository;
    ArmorTemplateRepository armorTemplateRepository;

    public ForgeService(InventoryRepository inventoryRepository, ItemTemplateRepository itemTemplateRepository, WeaponTemplateRepository weaponTemplateRepository, ArmorTemplateRepository armorTemplateRepository, UserRepository userRepository) {
        this.inventoryRepository = inventoryRepository;
        this.itemTemplateRepository = itemTemplateRepository;
        this.weaponTemplateRepository = weaponTemplateRepository;
        this.armorTemplateRepository = armorTemplateRepository;
        this.userRepository = userRepository;
    }

    public ForgingInfoDto getForgingInfo(Long itemId){

        int[] forgingInfo = new int[6]; //[0]=type (0 for weapon, 1 for armor), [1]=Forging price, [2/3]=min/max dmg after forging(for weapon), [4]=armor after forging(for armor),[5]= plus after forging
        Inventory inventory= inventoryRepository.getInventoryById(itemId);
        String name=inventory.getName();
        int basicPrice=inventory.getPrice();
        int plus=inventory.getFinalPlus();
        forgingInfo[1]=basicPrice*(plus+1)*10;
        forgingInfo[5]=plus+1;
        String type ="";
        int templateId=inventory.getTemplateId();
        Long templateIdForRepo= (long) templateId;
        ItemTemplate itemTemplate=itemTemplateRepository.getItemTemplateById(templateIdForRepo);
        if(itemTemplate instanceof Weapon){
            type="weapon";
            Weapon weapon= weaponTemplateRepository.getWeaponById(templateIdForRepo);
            int afterForgingMinDmg= inventory.getFinalMinDmg()+ weapon.getMinDmg();
            int afterForgingMaxDmg=inventory.getFinalMaxDmg()+ weapon.getMaxDmg();
            forgingInfo[2]=afterForgingMinDmg;
            forgingInfo[3]=afterForgingMaxDmg;
            forgingInfo[4]=0;
        }

        if (itemTemplate instanceof Armor){
            type="armor";
            Armor armor=armorTemplateRepository.getArmorById(templateIdForRepo);
            int afterForgingArmor=inventory.getFinalArmor()+ armor.getArmor();
            forgingInfo[4]=afterForgingArmor;
            forgingInfo[2]=0;
            forgingInfo[3]=0;
        }
        return new ForgingInfoDto(name, type,forgingInfo[1], forgingInfo[2], forgingInfo[3], forgingInfo[4],forgingInfo[5] );
    }

    public String upgrade(Long itemId, String username){
        User user= userRepository.findByUsername(username).orElse(null);
        Player player=user.getPlayer();
        int moneys=player.getGold();

        ForgingInfoDto forgingInfoDto = getForgingInfo(itemId);
        if(moneys< forgingInfoDto.cost()){
            return "Not enough money!";
        }
        Inventory item =  inventoryRepository.getInventoryById(itemId);

        Random rand=new Random();
        int chance=100-item.getFinalPlus()*10;
        if(rand.nextInt(100)>=chance){
            return "Upgrade unsuccessful";
        }

        item.setFinalMaxDmg(forgingInfoDto.maxDmg());
        item.setFinalMinDmg(forgingInfoDto.minDmg());
        item.setFinalArmor(forgingInfoDto.armor());
        item.setFinalPlus(forgingInfoDto.finalPlus());
        item.setPrice(item.getPrice()*2);
        player.setGold(player.getGold()- forgingInfoDto.cost());
        userRepository.save(user);
        return "Item upgraded successfully!";
    }

}
