package org.example.projectbackend.repository;

import org.example.projectbackend.entity.Monster;
import org.example.projectbackend.entity.User;
import org.example.projectbackend.entity.items.Armor;
import org.example.projectbackend.entity.items.Weapon;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class FillTemplates implements ApplicationRunner {

    MonsterRepository monsterRepository;
    WeaponTemplateRepository weaponTemplateRepository;
    ArmorTemplateRepository armorTemplateRepository;
    UserRepository userRepository;


    public FillTemplates(UserRepository userRepository, ArmorTemplateRepository armorTemplateRepository, MonsterRepository monsterRepository,WeaponTemplateRepository weaponTemplateRepository){
        this.monsterRepository = monsterRepository;
        this.weaponTemplateRepository=weaponTemplateRepository;
        this.userRepository = userRepository;
        this.armorTemplateRepository=armorTemplateRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if(monsterRepository.count() == 0) {
            monsterRepository.save(new Monster("Rat", "Sewer", 1, 2, 2, 3, 5, 5, 7, 2, 30, 5, 20));
        }

        if(weaponTemplateRepository.count() == 0) {
            weaponTemplateRepository.save(new Weapon("Dagger", 5, 1,1,2));
            weaponTemplateRepository.save(new Weapon("Long dagger", 10, 3,3,5));
        }

        if(armorTemplateRepository.count() == 0) {
            armorTemplateRepository.save(new Armor("Rags", 5 ,1,5));
        }

        if(userRepository.count() == 0) {
            userRepository.save(new User("admin", "admin", "admin@o2.pl","ADMIN"));
        }
    }

}
