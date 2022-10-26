package com.javarush.island.parfenov.statistics;

import com.javarush.island.parfenov.gameMechanics.GameController;
import com.javarush.island.parfenov.organisms.Organism;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Stat {
    private GameController gameController;
    private Map<String, Integer> amountOfOrganisms = new HashMap<>();
    private AtomicInteger fullSize = new AtomicInteger(0);

    public Stat(GameController gameController) {
        this.gameController = gameController;
        prepareMapForStat();
    }

    private void prepareMapForStat() {
        for (Map.Entry<String, Organism> entry : gameController.getPrototypes().entrySet()) {
            amountOfOrganisms.put(entry.getKey(), 0);
        }
    }

    public Map<String, Integer> getAmountOfOrganisms() {
        return amountOfOrganisms;
    }

    public void setAmountOfOrganisms(Map<String, Integer> amountOfOrganisms) {
        this.amountOfOrganisms = amountOfOrganisms;
    }

    public AtomicInteger getFullSize() {
        return fullSize;
    }

    public void setFullSize(int size) {
        this.fullSize.set(size);
    }
}
