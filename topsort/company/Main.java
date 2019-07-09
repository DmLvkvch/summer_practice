package com.company;

import static com.company.PAR_S.*;
import static com.sun.glass.ui.Cursor.setVisible;
import static com.sun.javafx.fxml.expression.Expression.add;
import static javax.swing.JFrame.*;
import javax.swing.*;
import java.awt.*;
public class Main extends JFrame {
    public static void main(String[] args) {
        Graph g = new Graph();
        g.addE(0, 1);
        g.addE(0, 2);
        g.addE(0, 3);
        g.addE(0, 4);
        g.addE(1, 2);
        g.addE(2, 4);
        g.addE(3, 2);
        g.addE(3, 4);
        TopSort sort = new TopSort(g);
        sort.Alg();
        Window w = new Window(g, sort);
        w.setVisible(true);
    }
}
