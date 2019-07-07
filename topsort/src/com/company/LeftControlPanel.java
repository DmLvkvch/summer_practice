package com.company;

import javax.swing.*;
import java.awt.*;

public class LeftControlPanel extends JPanel {

    public LeftControlPanel() {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(900, 150));
        //this.setBackground(Color.BLUE);
        JButton addEdgeButton = new JButton("add edge");
        addEdgeButton.setSize(new Dimension(100, 25));
        addEdgeButton.setLocation(this.getPreferredSize().width /*- addEdgeButton.getWidth() - 10*/, 10);

        JLabel commentsLabel = new JLabel("comments field", SwingConstants.CENTER);
        commentsLabel.setSize(new Dimension(this.getPreferredSize().width, 90));
        commentsLabel.setLocation(0, 10/*addEdgeButton.getHeight() + 20*/);
        commentsLabel.setOpaque(true);
        commentsLabel.setBackground(new Color(192, 192, 192));
        // this.add(addEdgeButton);
        this.add(commentsLabel);
    }
}