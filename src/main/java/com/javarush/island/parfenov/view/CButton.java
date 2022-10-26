package com.javarush.island.parfenov.view;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Map;

public class CButton extends JButton {

    Map<String, ImageIcon> icons;
    ImageIcon imageIcon;
    public CButton(GameScreen gameScreen) {
        this.icons = gameScreen.getIcons();
        imageIcon = icons.get("Empty");
        setIcon(imageIcon);
    }

    public void changeImage(ImageIcon imageIcon) {
        this.setIcon(imageIcon);
        this.revalidate();
    }
}
