package org.example.projectbackend.battle;

import org.example.projectbackend.entity.Monster;
import org.example.projectbackend.repository.MonsterRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MonsterSeeder implements ApplicationRunner {
    MonsterRepository monsterRepository;

    public MonsterSeeder(MonsterRepository monsterRepository) {
        this.monsterRepository = monsterRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if(monsterRepository.count() == 0) {
            monsterRepository.save(createMonster("Rat", "Sewer", 1, 2, 2, 3, 5, 5, 7, 2, 30, 5, 20));
        }
        }

    private Monster createMonster(String name, String location, int level, int baseDamage,
                                  int strength, int endurance, int perception, int speed,
                                  int dexterity, int luck, int hp, int exp, int gold) {
        Monster monster = new Monster();
        monster.setName(name);
        monster.setLocation(location);
        monster.setLevel(level);
        monster.setBaseDamage(baseDamage);
        monster.setStrength(strength);
        monster.setEndurance(endurance);
        monster.setPerception(perception);
        monster.setSpeed(speed);
        monster.setDexterity(dexterity);
        monster.setLuck(luck);
        monster.setHp(hp);
        monster.setExpReward(exp);
        monster.setGoldReward(gold);
        return monster;
    }
}