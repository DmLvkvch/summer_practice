package com.company;
import javax.swing.*;
import java.awt.*;

import static com.company.PAR_S.WINDOW_SIZE;

public class Main extends JFrame {
    public static void main(String[] args) {
        Graph g = new Graph();
        g.addE(0, 1);
        g.addE(1, 2);
        TopSort sort = new TopSort(g);
        sort.Alg(g);
        new Main(g, sort);
    }
    Main(Graph g, TopSort sort){
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setTitle("Алгоритм Яника-Прима-Дейкстры");      //Имя окна
        setSize( WINDOW_SIZE );                            //Размер окна

        setResizable(false);

        add( new Window(g, sort), BorderLayout.WEST );  // Размещение панельки с кнопками и изображением графа слева

        setVisible(true);
    }
}
