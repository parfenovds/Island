package com.javarush.island.parfenov.gameMechanics;

import com.javarush.island.parfenov.organisms.Organism;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class Vault {
    private Set<Organism> residents = new HashSet<>();
    private Set<Organism> migrants = new HashSet<>();
    private int amountOfMigrants;
    private int amountOfOrganisms;
    private ReentrantLock lock = new ReentrantLock(true);

    public Set<Organism> getResidents() {
        return residents;
    }

    public void setResidents(Set<Organism> residents) {
        this.residents = residents;
    }

    public Set<Organism> getMigrants() {
        return migrants;
    }

    public void setMigrants(Set<Organism> migrants) {
        this.migrants = migrants;
    }

    public int getAmountOfMigrants() {
        return amountOfMigrants;
    }

    public int getAmountOfOrganisms() {
        return amountOfOrganisms;
    }

    public void setAmountOfMigrants(int amountOfMigrants) {
        this.amountOfMigrants = amountOfMigrants;
    }

    public void setAmountOfOrganisms(int amountOfOrganisms) {
        this.amountOfOrganisms = amountOfOrganisms;
    }

    public ReentrantLock getLock() {
        return lock;
    }

    public void setLock(ReentrantLock lock) {
        this.lock = lock;
    }
}
