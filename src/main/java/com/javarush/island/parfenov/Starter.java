package com.javarush.island.parfenov;

import com.javarush.island.parfenov.gameMechanics.GameInitializer;
import com.javarush.island.parfenov.gameMechanics.PoolController;
import com.javarush.island.parfenov.settings.GameSettings;
import com.javarush.island.parfenov.statistics.Stat;
import com.javarush.island.parfenov.view.ViewInitializer;

public class Starter {

    public static void main(String[] args) {
        GameSettings gameSettings = new GameSettings();
        GameInitializer gameInitializer = new GameInitializer(gameSettings);
        Stat stat = new Stat(gameInitializer);
        ViewInitializer viewInitializer = new ViewInitializer(gameSettings, gameInitializer, stat);
        PoolController poolController = new PoolController(gameInitializer, viewInitializer, stat);
        poolController.begin();
    }
}
