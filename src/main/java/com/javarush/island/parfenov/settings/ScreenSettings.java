package com.javarush.island.parfenov.settings;

import javax.naming.OperationNotSupportedException;
import java.awt.*;

public final class ScreenSettings {

    private final int CELL_SIZE = 48;
    private int widthOfField;
    private int heightOfField;
    private final int maxWidthOfScreen;
    private final int maxHeightOfScreen;
    private int realWidthOfScreen;
    private int realHeightOfScreen;
    private GraphicsDevice gd;
    private GameSettings gameSettings;
    private ViewTypes view;

    public ScreenSettings(ViewTypes view) {
        this.view = view;
        if(view.equals(ViewTypes.SWING)) {
            this.gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            maxWidthOfScreen = gd.getDisplayMode().getWidth();
            maxHeightOfScreen = gd.getDisplayMode().getHeight();
//            this.widthOfField = gameSettings.getRealFieldSizes().getWidth();
//            this.heightOfField = gameSettings.getRealFieldSizes().getHeight();
        } else {
            throw new RuntimeException(new OperationNotSupportedException("NO VIEW!"));
        }
    }
//    public void countRealSizesOfScreen() {
//        realWidthOfScreen
//    }

    public ViewTypes getView() {
        return view;
    }

    public int getMaxWidthOfScreen() {
        return maxWidthOfScreen;
    }

    public int getMaxHeightOfScreen() {
        return maxHeightOfScreen;
    }

    public int getCellSize() {
        return CELL_SIZE;
    }

    public int getWidthOfField() {
        return widthOfField;
    }

    public int getHeightOfField() {
        return heightOfField;
    }

    public int getRealWidthOfScreen() {
        return widthOfField * CELL_SIZE;
    }

    public int getRealHeightOfScreen() {
        return heightOfField * CELL_SIZE;
    }
}
