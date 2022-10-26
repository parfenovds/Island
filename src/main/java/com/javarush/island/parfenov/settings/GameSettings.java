package com.javarush.island.parfenov.settings;

import com.javarush.island.parfenov.organisms.animals.*;
import com.javarush.island.parfenov.organisms.plants.Plant;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class GameSettings {
    //    private int widthOfField;
//    private int heightOfField;
    private ScreenSettings screenSettings;
    private final FieldSizes defaultFieldSizes = new FieldSizes(20, 20);
    private FieldSizes realFieldSizes;
    private Map<String, HashMap<String, Integer>> chances = new HashMap<>();
    private Map<String, HashMap<Characteristics, Number>> standardCharacteristics = new HashMap<>();
    private Class<?>[] organismClasses = {
            Wolf.class,

            Snake.class,
            Fox.class,
            Bear.class,
            Eagle.class,
            Horse.class,
            Deer.class,

            Rabbit.class,

            Mouse.class,
            Goat.class,
            Sheep.class,
            Boar.class,
            Bison.class,
            Duck.class,
            Caterpillar.class,
            Plant.class
            };
    private Integer[][] defaultChances = {
            {null, 0, 0, 0, 0, 10, 15, 60, 80, 60, 70, 15, 10, 40, 0, 0},

            {0, null, 15, 0, 0, 0, 0, 20, 40, 0, 0, 0, 0, 10, 0, 0},
            {0, 0, null, 0, 0, 0, 0, 70, 90, 0, 0, 0, 0, 60, 40, 0},
            {0, 80, 0, null, 0, 40, 80, 80, 90, 70, 70, 50, 20, 10, 0, 0},
            {0, 0, 10, 0, null, 0, 0, 90, 90, 0, 0, 0, 0, 80, 0, 0},
            {0, 0, 0, 0, 0, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, null, 0, 0, 0, 0, 0, 0, 0, 0, 100},

            {0, 0, 0, 0, 0, 0, 0, null, 0, 0, 0, 0, 0, 0, 0, 100},

            {0, 0, 0, 0, 0, 0, 0, 0, null, 0, 0, 0, 0, 0, 90, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, null, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 50, 0, 0, null, 0, 0, 90, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, 90, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, 100},
            {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
    };
    private Number[][] defaultCharacteristics = {
            {50.0, 30, 3, 8.0, 2},

            {15.0, 30, 1, 3.0, 2},
            {8.0, 30, 2, 2.0, 2},
            {500.0, 5, 2, 80.0, 10},
            {6.0, 20, 3, 1.0, 3},
            {400.0, 20, 4, 60.0, 3},

            {300.0, 20, 4, 50.0, 3},

            {2.0, 150, 2, 0.45, 1},
            {0.05, 500, 1, 0.01, 1},
            {60.0, 140, 3, 10.0, 2},
            {70.0, 140, 3, 15.0, 2},
            {400.0, 50, 2, 50.0, 3},
            {700.0, 10, 3, 100.0, 5},
            {1.0, 200, 4, 0.15, 1},
            {0.01, 1000, 0, 0.0, 0},
            {1.0, 200, null, null, 0},
    };

    private String[] icons = {
            "\uD83D\uDC3A",
            "\uD83D\uDC0D",
            "\uD83E\uDD8A",
            "\uD83D\uDC3B",
            "\uD83E\uDD85",
            "\uD83D\uDC0E",
            "\uD83E\uDD8C",
            "\uD83D\uDC07",
            "\uD83D\uDC2D",
            "\uD83D\uDC10",
            "\uD83D\uDC0F",
            "\uD83D\uDC17",
            "\uD83D\uDC2E",
            "\uD83E\uDD86",
            "\uD83D\uDC1B",
            "\uD83C\uDF32",
    };

    public GameSettings(ScreenSettings screenSettings) {
        this.screenSettings = screenSettings;
        prepareFieldSize();
        prepareChancesMap();
        prepareStandardCharacteristics();
    }

    private void prepareFieldSize() {
        countFieldSizes();
    }


    private void countFieldSizes() {
        int maxWidthOfScreen = screenSettings.getMaxWidthOfScreen();
        int maxHeightOfScreen = screenSettings.getMaxHeightOfScreen();
        int cellSize = screenSettings.getCellSize();
        int maxWidthOfField = (maxWidthOfScreen - maxWidthOfScreen / 4) / cellSize;
        int maxHeightOfField = (maxHeightOfScreen - maxHeightOfScreen / 4) / cellSize;
        int desiredWidthOfField = Math.max(defaultFieldSizes.getWidth(), 1);
        int desiredHeightOfField = Math.max(defaultFieldSizes.getHeight(), 1);
        //realFieldSizes = new FieldSizes(Math.min(maxWidthOfField, desiredWidthOfField), Math.min(maxHeightOfField, desiredHeightOfField));
        realFieldSizes = new FieldSizes(desiredWidthOfField, desiredHeightOfField);
//            if(screenSettings.getView().equals(ViewTypes.SWING)) {
//                screenSettings.countRealSizesOfScreen();
//            }
    }

    private void prepareStandardCharacteristics() {
        for (int i = 0; i < organismClasses.length; i++) {
            String name = organismClasses[i].getSimpleName();
            standardCharacteristics.put(name,
                    new HashMap<>());
            for (int j = 0; j < defaultCharacteristics[0].length; j++) {
                standardCharacteristics.get(name).put(Characteristics.values()[j], defaultCharacteristics[i][j]);
            }
        }
    }

    public Map<String, HashMap<String, Integer>> getChances() {
        return chances;
    }

    public void setChances(Map<String, HashMap<String, Integer>> chances) {
        this.chances = chances;
    }

    public Map<String, HashMap<Characteristics, Number>> getStandardCharacteristics() {
        return standardCharacteristics;
    }

    public void setStandardCharacteristics(Map<String, HashMap<Characteristics, Number>> standardCharacteristics) {
        this.standardCharacteristics = standardCharacteristics;
    }

    public void setOrganismClasses(Class<?>[] organismClasses) {
        this.organismClasses = organismClasses;
    }

    public Integer[][] getDefaultChances() {
        return defaultChances;
    }

    public void setDefaultChances(Integer[][] defaultChances) {
        this.defaultChances = defaultChances;
    }

    public Number[][] getDefaultCharacteristics() {
        return defaultCharacteristics;
    }

    public void setDefaultCharacteristics(Number[][] defaultCharacteristics) {
        this.defaultCharacteristics = defaultCharacteristics;
    }

    private void prepareChancesMap() {
        for (int outerClassIndex = 0; outerClassIndex < organismClasses.length; outerClassIndex++) {
            String organismActor = organismClasses[outerClassIndex].getSimpleName();
            chances.put(organismActor, new HashMap<>());
            HashMap<String, Integer> organismChances = chances.get(organismActor);
            for (int j = 0; j < defaultChances.length; j++) {
                String organismVictim = organismClasses[j].getSimpleName();
                organismChances.put(organismVictim, defaultChances[outerClassIndex][j]);
            }
        }
    }

    public Class<?>[] getOrganismClasses() {
        return organismClasses;
    }

    public ScreenSettings getScreenSettings() {
        return screenSettings;
    }

    public void setScreenSettings(ScreenSettings screenSettings) {
        this.screenSettings = screenSettings;
    }

    public FieldSizes getRealFieldSizes() {
        return realFieldSizes;
    }

    public int getRealFieldWidth() {
        return realFieldSizes.getWidth();
    }

    public int getRealFieldHeight() {
        return realFieldSizes.getHeight();
    }

    public String[] getIcons() {
        return icons;
    }
}
