package com.javarush.island.parfenov.organisms.animals;

import com.javarush.island.parfenov.organisms.Animal;
import com.javarush.island.parfenov.organisms.Herbivore;
import com.javarush.island.parfenov.settings.Characteristics;

import java.util.Map;

public class Caterpillar extends Animal implements Herbivore {

    private String pathToImg = "/animal_sprites/Caterpillar.png";
    public Caterpillar(Map<Characteristics, Number> characteristics, Map<String, Integer> chances) {
        super(characteristics, chances);
    }
    public void changeWeightByTime() {
        super.setWeight(super.getWeight() + super.getPerc());
        if (super.getWeight() <= 0) {
            super.setAlive(false);
        }
    }
}
