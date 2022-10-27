package com.javarush.island.parfenov.view;

import com.javarush.island.parfenov.gameMechanics.GameInitializer;
import com.javarush.island.parfenov.settings.GameSettings;
import com.javarush.island.parfenov.statistics.Stat;

public class ViewInitializer implements Runnable{
    GameSettings gameSettings;
    GameInitializer gameInitializer;
    Stat stat;
    public ViewInitializer(GameSettings gameSettings, GameInitializer gameInitializer, Stat stat) {
        this.gameSettings = gameSettings;
        this.gameInitializer = gameInitializer;
        this.stat = stat;
    }

    @Override
    public void run() {
        ViewController viewController = new ViewController(gameSettings, gameInitializer, stat);
        viewController.gameLoop();
    }
}
