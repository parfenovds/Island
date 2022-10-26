package com.javarush.island.parfenov.organisms.animals;

import com.javarush.island.parfenov.organisms.Animal;
import com.javarush.island.parfenov.organisms.Carnivore;
import com.javarush.island.parfenov.settings.Characteristics;

import java.util.Map;

public class Bear extends Animal implements Carnivore {
    private String pathToImg = "/animal_sprites/Bear.png";

    public Bear(Map<Characteristics, Number> characteristics, Map<String, Integer> chances) {
        super(characteristics, chances);
    }
}
