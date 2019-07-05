package com.company;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;

public class Main {




    public static void main(String[] args) {

        Graph g = new Graph();
        g.addE(0, 1);
        g.addE(1, 2);
        g.addE(2, 3);
        g.addE(3, 4);
        g.addE(3, 5);
        g.addE(3, 6);
        g.addE(6, 7);
        g.addE(7, 8);
        g.addE(8 , 9);
        g.addE(9 , 10);
        g.addE(10 , 11);


        TopSort sort = new TopSort(g);
        sort.Alg();
        Di w = new Di(g, sort);

        w.setVisible(true);
    }


}
