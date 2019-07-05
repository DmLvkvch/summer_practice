package com.company;

import javax.swing.*;
import java.awt.*;

public class RigthControlPanel extends JPanel {
    public RigthControlPanel() {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(630,700));
        //this.setBackground(Color.BLUE);

        JLabel textFieldLabel = new JLabel("Enter data:", SwingConstants.CENTER);
        JTextPane textField = new JTextPane();
        JTextPane textArea = new JTextPane();
        JButton addEdge = new JButton("add edge");
        JButton CreateGraph = new JButton("make graph");
        JButton step = new JButton("next step");
        JButton runAlg = new JButton("run alg");
        JButton readFromFile = new JButton("read form file");
        JButton readFromField = new JButton("create graph");


        textFieldLabel.setSize(550, 25);
        textFieldLabel.setLocation((getPreferredSize().width - textFieldLabel.getSize().width) / 2,0);

        textArea.setSize(550, 100);
        textArea.setLocation((getPreferredSize().width - textArea.getSize().width) / 2, textFieldLabel.getSize().height);

        Dimension buttonSize = new Dimension((textArea.getWidth()- 10) / 2, 25);
        readFromFile.setSize(buttonSize);
        readFromFile.setLocation(textArea.getX(), textArea.getY() + textArea.getHeight() + 10);

        readFromField.setSize(buttonSize);
        readFromField.setLocation(textArea.getLocation().x + readFromFile.getWidth() + 10, textArea.getLocation().y + textArea.getSize().height + 10);

        step.setSize(buttonSize);
        runAlg.setSize(buttonSize);

        step.setLocation(textArea.getX(), 650 - step.getHeight() - 10);
        runAlg.setLocation(textArea.getX() + step.getWidth() + 10, 650 - step.getHeight() - 10);

        this.add(textFieldLabel);
        this.add(addEdge);
        this.add(CreateGraph);
        this.add(step);
        this.add(runAlg);
        this.add(readFromFile);
        this.add(readFromField);
        this.add(textField);
        this.add(textArea);
    }
}