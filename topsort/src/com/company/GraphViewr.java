package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Vector;

import static com.company.PAR_S.*;


public class GraphViewr extends JPanel {
    private JFrame parent;
    private Graph graph;
    Vector<ActiveVertex> vertexes;

    GraphViewr(JFrame parent){
        this.parent = parent;
        setBackground(Color.BLUE);
        setBounds(0,0, 600, 500);
    }


    GraphViewr(JFrame parent, Graph graph){
        this.parent = parent;
        this.graph = graph;
        setBackground(Color.BLUE);
        setBounds(0,0, 600, 500);

        vertexes = new Vector<>(graph.V());
        /*
        for (Integer vertex: graph.getVertexes()){
            Random r = new Random();
            vertexes.add(new ActiveVertex(this, vertex,
                    r.nextInt(600 - VERTEX_D) + VERTEX_R,
                    r.nextInt(500 - VERTEX_D) + VERTEX_R));
        }
        
         */
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        graphics.drawRect(5, 50, 900, 450);

        drawGraph(graphics);

    }

    private void drawGraph(Graphics graphics) {
        for (ActiveVertex vertex: vertexes) {
            vertex.paint(graphics);
        }
    }

    @Override
    public JFrame getParent() {
        return parent;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }
    public Graph getGraph() {
        return graph;
    }




}
