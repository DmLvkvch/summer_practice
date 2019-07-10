package com.company;

import javax.swing.*;
import java.awt.*;

public class LeftControlPanel extends JPanel {
    public JLabel commentsLabel = new JLabel("comments field", SwingConstants.CENTER);
    public JTextPane commentArea = new JTextPane();
    public LeftControlPanel() {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(900, 150));
        commentsLabel.setSize(new Dimension(this.getPreferredSize().width, 90));
        commentsLabel.setLocation(0, 10/*addEdgeButton.getHeight() + 20*/);
        commentsLabel.setOpaque(true);
        commentsLabel.setBackground(new Color(192, 192, 192));
        commentArea.setEditable(false);
        JScrollPane jsp = new JScrollPane(commentArea);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jsp.setSize(900, 100);
        jsp.setLocation(0,0);
        this.add(jsp);

    }
}