package com.company;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;



public class Main extends JFrame { //Наследуя от JFrame мы получаем всю функциональность окна
    Graph<String, DefaultEdge> g;

    public Main(){
        super("My First Window"); //Заголовок окна
        setBounds(100, 100, 1000, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Scanner sc = new Scanner(System.in);

        GraphE(sc);
    }

    public static void main(String[] args) { //эта функция может быть и в другом классе
        Main app = new Main(); //Создаем экземпляр нашего приложения


        app.setVisible(true); //С этого момента приложение запущено!
    }

    void GraphE(Scanner sc){
        g = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        while(sc.hasNextInt()) {
            Integer x1 = sc.nextInt();
            Integer x2 = sc.nextInt();
            g.addVertex(x1.toString());
            g.addVertex(x2.toString());
            g.addEdge(x1.toString(), x2.toString());
        }
        JGraphXAdapter<String, DefaultEdge> graphAdapter =
                new JGraphXAdapter<String, DefaultEdge>(g);
        mxGraphComponent component = new mxGraphComponent(graphAdapter);
        component.setConnectable(false);
        component.getGraph().setAllowDanglingEdges(false);
        getContentPane().add(component);
        mxCircleLayout layout = new mxCircleLayout(graphAdapter);

        // center the circle
        int radius = 100;
        layout.setX0(radius);
        layout.setY0((900 / 2.0) - 4*radius);
        layout.setRadius(radius);
        layout.setMoveCircle(true);
        

        layout.execute(graphAdapter.getDefaultParent());

    }


}
