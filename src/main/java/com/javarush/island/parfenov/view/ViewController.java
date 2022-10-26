package com.javarush.island.parfenov.view;

import com.javarush.island.parfenov.gameMechanics.Cell;
import com.javarush.island.parfenov.gameMechanics.GameController;
import com.javarush.island.parfenov.gameMechanics.Vault;
import com.javarush.island.parfenov.organisms.Organism;
import com.javarush.island.parfenov.settings.GameSettings;
import com.javarush.island.parfenov.settings.ScreenSettings;
import com.javarush.island.parfenov.statistics.Stat;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Set;

public class ViewController extends JFrame {

    public static final Color BACKGROUND_COLOR = new Color(48, 80, 55);
    public static final Color BORDER_COLOR = new Color(80, 48, 73);
    private ScreenSettings screenSettings;
    private GameSettings gameSettings;
    private GameController gameController;
    private GameScreen gameScreen;
    private RightPanel rightPanel;
    private double timePerFrame;
    private long lastFrame;
    private int height;
    private int width;
    private Stat stat;
    public ViewController(GameSettings gameSettings, ScreenSettings screenSettings, GameController gameController, Stat stat) {
        this.gameController = gameController;
        this.screenSettings = screenSettings;
        this.stat = stat;
        this.height = gameSettings.getRealFieldHeight();
        this.width = gameSettings.getRealFieldWidth();
        int widthOfScreen = gameSettings.getRealFieldWidth() * screenSettings.getCellSize();
        int heightOfScreen = gameSettings.getRealFieldHeight() * screenSettings.getCellSize();
        setSize(widthOfScreen + 200, heightOfScreen + 200);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        gameScreen = new GameScreen(gameController, gameSettings, screenSettings);
        timePerFrame = 1_000_000_000.0 / 10.0;
        rightPanel = new RightPanel(gameSettings);
        System.out.println(gameSettings.getRealFieldHeight());
        JPanel panel = new JPanel(new GridLayout(gameSettings.getRealFieldHeight(), gameSettings.getRealFieldWidth()));
        makeButtons(panel);
        add(panel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        pack();
        setVisible(true);

    }

    private void makeButtons(JPanel panel) {
        Map<Cell, CButton> buttons = gameScreen.getButtons();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                CButton valueButton = new CButton(gameScreen);
                Cell key = gameController.getField()[y][x];
                buttons.put(key, valueButton);
                valueButton.setBackground(BACKGROUND_COLOR);
                valueButton.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
                panel.add(valueButton);
            }
        }
    }

    public void gameLoop() {
        Cell[][] field = gameScreen.getField();
        Map<Cell, CButton> buttons = gameScreen.getButtons();

        Map<String, BufferedImage> images = gameScreen.getImages();
        Map<String, ImageIcon> icons = gameScreen.getIcons();
        ImageIcon emptyImage = icons.get("Empty");
        while(true) {
            for (int y = 0; y < field.length; y++) {
                for (int x = 0; x < field[y].length; x++) {
                    String groupName = getBiggestGroup(field[y][x].getPersons());
                    if (!groupName.isEmpty()) {
                        buttons.get(field[y][x]).changeImage(icons.get(groupName));
                    } else {
                        buttons.get(field[y][x]).changeImage(emptyImage);
                    }
                }
            }
//            for (Map.Entry<JLabel, String> entry : rightPanel.getLabelTo().entrySet()) {
//
//            }
            if (System.nanoTime() - lastFrame >= timePerFrame) {
                lastFrame = System.nanoTime();
                repaint();
            }
        }
    }

    private String getBiggestGroup(Map<String, Vault> persons) {
        String result = "";
        int amount = 0;
        for (Map.Entry<String, Vault> entry : persons.entrySet()) {
//            int sizeOfGroup = entry.getValue().size();
            int sizeOfGroup = entry.getValue().getAmountOfOrganisms();
            if (sizeOfGroup > amount) {
                amount = sizeOfGroup;
                result = entry.getKey();
            }
        }
        return result;
    }



}
