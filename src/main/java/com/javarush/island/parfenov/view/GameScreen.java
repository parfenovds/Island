package com.javarush.island.parfenov.view;

import com.javarush.island.parfenov.gameMechanics.Cell;
import com.javarush.island.parfenov.gameMechanics.GameController;
import com.javarush.island.parfenov.organisms.Organism;
import com.javarush.island.parfenov.settings.GameSettings;
import com.javarush.island.parfenov.settings.ScreenSettings;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class GameScreen extends JPanel {
    private Random random;
    private ScreenSettings screenSettings;
    private GameSettings gameSettings;
    private GameController gameController;
    private Cell[][] field;
    private Map<Cell, CButton> buttons = new HashMap<>();
    private long lastTime;
    private int frames;
//    private MouseEvent mouseEvent = new MouseEvent();
    private int X = 0;
    private int Y = 48 * 2;
    private int counter = 0;
    private Map<String, BufferedImage> images = new HashMap<>();
    private Map<String, ImageIcon> icons = new HashMap<>();


    public GameScreen(GameController gameController, GameSettings gameSettings, ScreenSettings screenSettings) {
        this.gameController = gameController;
        this.field = gameController.getField();
        this.gameSettings = gameSettings;
        this.screenSettings = screenSettings;
        random = new Random();
        imgInit(gameController);
    }

    private void imgInit(GameController gameController) {
        for (String name : gameController.getPrototypes().keySet()) {
            try (InputStream inputStream = getClass().getResourceAsStream("/animal_sprites/" + name + ".png")) {
                BufferedImage read = ImageIO.read(inputStream);
                images.put(name, read);
                icons.put(name, new ImageIcon(images.get(name).getSubimage(0, 48, 48, 48)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        icons.put("Empty", new ImageIcon(images.get("Plant").getSubimage(48*5, 48*5, 48, 48)));
    }

//    @Override
//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        for (int y = 0; y < field.length; y++) {
//            for (int x = 0; x < field[y].length; x++) {
//                String groupName = getBiggestGroup(field[y][x].getResidents());
//                if (!groupName.isEmpty()) {
//                    buttons.get(field[y][x]).changeImage(new ImageIcon(images.get(groupName).getSubimage(X, 48, 48, 48)));
////                    g.drawImage(images.get(groupName).getSubimage(X, 48, 48, 48), x*48, y*48, null);
//                    //buttons.get(field[y][x]).setBackground(Color.RED);
//
//
////                    String pathToImg = gameController.getPrototypes().get(groupName).getPathToImg();
////                    g.drawImage(img.getSubimage(X, 0, 48, 48), 0, 0, null);
////                    BufferedImage bufferedImage = new BufferedImage(pathToImg)
////                    g.drawImage()
//                }
//            }
//        }
////        counter += 16;
////        if (counter % 48 == 0) {
////            X = counter;
////        }
////        if (X > 96) {
////            X = 0;
////            counter = 0;
////        }
//
//        frames++;
//        for (int y = 0; y < gameSettings.getRealFieldHeight(); y++) {
//            for (int x = 0; x < gameSettings.getRealFieldWidth(); x++) {
////                if()
////                g.setColor(Color.RED);
//
//                g.drawRect(x * screenSettings.getCellSize(), y * screenSettings.getCellSize(), screenSettings.getCellSize(), screenSettings.getCellSize());
//            }
//        }
////        printFPS();
//
//    }

    private String getBiggestGroup(Map<String, Set<Organism>> residents) {
        String result = "";
        int amount = 0;
        System.out.println("****************");
        for (Map.Entry<String, Set<Organism>> entry : residents.entrySet()) {
            int sizeOfGroup = entry.getValue().size();
            System.out.println(entry.getKey() + " = " + sizeOfGroup);
            if (sizeOfGroup > amount) {
                amount = sizeOfGroup;
                result = entry.getKey();
            }
        }
        System.out.println("****************");
        return result;
    }

    private void printFPS() {
        if (System.currentTimeMillis() - lastTime >= 1000) {
            System.out.println("FPS: " + frames);
            frames = 0;
            lastTime = System.currentTimeMillis();
        }
    }

    private Color getRndColor() {
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r, g, b);
    }

    public Map<Cell, CButton> getButtons() {
        return buttons;
    }

    public void setButtons(Map<Cell, CButton> buttons) {
        this.buttons = buttons;
    }

    public Map<String, BufferedImage> getImages() {
        return images;
    }

    public Cell[][] getField() {
        return field;
    }

    public Map<String, ImageIcon> getIcons() {
        return icons;
    }
}
