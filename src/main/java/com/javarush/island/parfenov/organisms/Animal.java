package com.javarush.island.parfenov.organisms;

import com.javarush.island.parfenov.settings.Characteristics;

import java.util.Map;

public abstract class Animal extends Organism implements Cloneable {
    public Animal(Map<Characteristics, Number> characteristics, Map<String, Integer> chances) {
        super(characteristics, chances);
    }

    private int maxCellByMove;

    public void move() {

    }
    public void eat() {

    }
    public Animal multiply(Animal animal) {
        return null;
    }

//    public Class<?> getMyClass() {
//        return myClass;
//    }
}
