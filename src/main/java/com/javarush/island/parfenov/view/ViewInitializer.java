package com.javarush.island.parfenov.view;

import com.javarush.island.parfenov.gameMechanics.GameController;
import com.javarush.island.parfenov.settings.GameSettings;
import com.javarush.island.parfenov.settings.ScreenSettings;
import com.javarush.island.parfenov.statistics.Stat;

public class ViewInitializer implements Runnable{
    GameSettings gameSettings;
    ScreenSettings screenSettings;
    GameController gameController;
    Stat stat;
    public ViewInitializer(GameSettings gameSettings, ScreenSettings screenSettings, GameController gameController, Stat stat) {
        this.gameSettings = gameSettings;
        this.screenSettings = screenSettings;
        this.gameController = gameController;
        this.stat = stat;
    }

//    public void init() {
//
//
//    }

    @Override
    public void run() {
        ViewController viewController = new ViewController(gameSettings, screenSettings, gameController, stat);
        viewController.gameLoop();
    }
}
