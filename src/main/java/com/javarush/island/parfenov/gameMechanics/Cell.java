package com.javarush.island.parfenov.gameMechanics;

import com.javarush.island.parfenov.organisms.Organism;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Cell {
    private int y;
    private int x;
    private Map<String, Set<Organism>> residents = new HashMap<>();
    private Map<String, Set<Organism>> migrants = new HashMap<>();
    private Map<String, Integer> amountOfMigrants = new HashMap<>();
    private Map<String, Integer> amountOfOrganisms = new HashMap<>();
    private List<Cell> cells = new ArrayList<>();
    private ReentrantLock lock = new ReentrantLock(true);
    public Cell(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public ReentrantLock getLock() {
        return lock;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public Map<String, Set<Organism>> getResidents() {
        return residents;
    }

    public void setResidents(Map<String, Set<Organism>> residents) {
        this.residents = residents;
    }

    public Map<String, Integer> getAmountOfMigrants() {
        return amountOfMigrants;
    }

    public Map<String, Set<Organism>> getMigrants() {
        return migrants;
    }

    public Map<String, Integer> getAmountOfOrganisms() {
        return amountOfOrganisms;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "residents=" + residents +
                "}\n";
    }
}
