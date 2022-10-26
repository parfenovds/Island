package com.javarush.island.parfenov.gameMechanics;

import com.javarush.island.parfenov.organisms.Organism;

import java.util.Map;
import java.util.Set;

public class FieldController {
    private Cell[][] Field;
    public FieldController() {

    }

    public Cell[][] getField() {
        return Field;
    }

    public void setField(Cell[][] field) {
        Field = field;
    }
}
