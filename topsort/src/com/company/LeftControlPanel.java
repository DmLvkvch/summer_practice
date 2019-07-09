package com.company;

import javax.swing.*;
import java.awt.*;

public class LeftControlPanel extends JPanel {
    public JLabel commentsLabel = new JLabel("comments field", SwingConstants.CENTER);
    public LeftControlPanel() {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(900, 150));


        commentsLabel.setSize(new Dimension(this.getPreferredSize().width, 90));
        commentsLabel.setLocation(0, 10/*addEdgeButton.getHeight() + 20*/);
        commentsLabel.setOpaque(true);
        commentsLabel.setBackground(new Color(192, 192, 192));
        //commentsLabel.setText("werwe");
        // this.add(addEdgeButton);
        this.add(commentsLabel);

    }
}