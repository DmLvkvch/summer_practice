package com.company;

import static com.company.PAR_S.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.*;
import java.awt.*;
public class Main extends JFrame{

    public static void main(String[] args) {
	    Graph g = new Graph();
	    /*
	    try {
            g.addE(5, 5);
            g.addE(0, 1);
            g.addE(2, 1);
            g.addE(3, 1);
            g.addE(1, 4);
            g.addE(5, 4);
            g.addE(3, 5);
            g.addE(4, 5);
            g.print();

        }catch (RuntimeException e) {
	        System.out.println(e);
        }
        TopSort t = new TopSort(g);
        t.Alg();
        */
	    Window w = new Window();
	    w.setVisible(true);
    }
}
