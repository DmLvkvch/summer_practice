package com.company;

import static com.company.PAR_S.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.*;
import java.awt.*;
public class Main{

    public static void main(String[] args) {
        Graph g = new Graph();
        g.addV(0);
        Window w = new Window(g);
        w.setVisible(true);
    }
}
