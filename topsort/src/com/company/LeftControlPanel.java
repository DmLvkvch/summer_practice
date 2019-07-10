package com.company;

import javax.swing.*;
import java.awt.*;

public class LeftControlPanel extends JPanel {
    public JTextPane commentArea = new JTextPane();
    public LeftControlPanel() {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(900, 150));


        commentArea.setEditable(false);
        JScrollPane jsp = new JScrollPane(commentArea);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jsp.setSize(900, 100);
        jsp.setLocation(0,0);
        this.add(jsp);

    }
}