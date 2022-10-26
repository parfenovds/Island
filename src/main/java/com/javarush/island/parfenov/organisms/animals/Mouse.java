package com.javarush.island.parfenov.organisms.animals;

import com.javarush.island.parfenov.organisms.Animal;
import com.javarush.island.parfenov.organisms.Herbivore;
import com.javarush.island.parfenov.settings.Characteristics;

import java.util.Map;

public class Mouse extends Animal implements Herbivore {

    public Mouse(Map<Characteristics, Number> characteristics, Map<String, Integer> chances) {
        super(characteristics, chances);
    }
}
