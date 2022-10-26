package com.javarush.island.parfenov.view;

import com.javarush.island.parfenov.settings.GameSettings;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class RightPanel extends JPanel {
    GameSettings gameSettings;
    private JLabel label = new JLabel();
    private JLabel label2 = new JLabel();
    private final Map<JLabel, String> labelToName = new HashMap<>();
    private final Map<String, String> nameToUnicode = new HashMap<>();
    private Map<JLabel, String> labelToIcon = new HashMap<>();
    
    public RightPanel(GameSettings gameSettings) {
        this.gameSettings = gameSettings;

        setLayout(new GridLayout(0, 1));
        Class<?>[] organismClasses = gameSettings.getOrganismClasses();
        String[] icons = gameSettings.getIcons();
        for (int i = 0; i < organismClasses.length; i++) {
            JLabel jLabel = new JLabel();
            String name = organismClasses[i].getSimpleName();
            labelToName.put(jLabel, name);
            nameToUnicode.put(name, icons[i]);
            labelToIcon.put(jLabel, "0");
            jLabel.setText(icons[i]);
            jLabel.setFont (jLabel.getFont ().deriveFont (17.0f));
            add(jLabel);
        }

//        label.setText("Amount of Organisms: ");
//        add(label);
//        label2.setText("SECOND ONE");
//        add(label2);
    }
    private void init() {

    }

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    public void setGameSettings(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }

    public JLabel getLabel2() {
        return label2;
    }

    public void setLabel2(JLabel label2) {
        this.label2 = label2;
    }

    public Map<JLabel, String> getLabelToName() {
        return labelToName;
    }

    public Map<String, String> getNameToUnicode() {
        return nameToUnicode;
    }

    public Map<JLabel, String> getLabelToIcon() {
        return labelToIcon;
    }

    public void setLabelToIcon(Map<JLabel, String> labelToIcon) {
        this.labelToIcon = labelToIcon;
    }
}
