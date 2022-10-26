package com.javarush.island.parfenov.organisms.animals;

import com.javarush.island.parfenov.organisms.Animal;
import com.javarush.island.parfenov.organisms.Herbivore;
import com.javarush.island.parfenov.settings.Characteristics;

import java.util.Map;

public class Bison extends Animal implements Herbivore {

    private String pathToImg = "/animal_sprites/Bison.png";
    public Bison(Map<Characteristics, Number> characteristics, Map<String, Integer> chances) {
        super(characteristics, chances);
    }
}
