package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import static com.company.PAR_S.*;

public class Window extends JPanel{
    private GraphField graphField;
    public Window(){
        add( createButtons() );
        graphField = new GraphField();
        add(graphField);
    }

    private JPanel createButtons() {
        JPanel panel = new JPanel( );
        Box box = Box.createVerticalBox();
        panel.setBackground( BUTTENS_BORDER );

        box.add(createGraphButtons());

        panel.add(box, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createGraphButtons() {
        JPanel graphButtons = new JPanel();
        graphButtons.setBackground( CREATE_BUTTONS_BG );

        graphButtons.add(createAddEdge());
        graphButtons.add(createAddVertex());

        graphButtons.setPreferredSize( CREATE_GRAPH_PANEL_SIZE );

        return graphButtons;
    }

    private JPanel createAddEdge(){

        JTextField vertexName1 = new JTextField(); vertexName1.setPreferredSize( SIZE_OF_INPUT_FIELD );
        JTextField vertexName2 = new JTextField(); vertexName2.setPreferredSize( SIZE_OF_INPUT_FIELD );
        JTextField edgeWeight  = new JTextField();  edgeWeight.setPreferredSize( SIZE_OF_INPUT_FIELD );

        JButton addEdjeButton = new JButton(new AbstractAction("Добавить ребро") {
            @Override
            public void actionPerformed(ActionEvent e) {
                createEdge(vertexName1, vertexName2);
            }
        });

        JPanel addEdge = new JPanel( new GridLayout(5,1) );
        addEdge.add(addEdjeButton);
        addEdge.add( glueParametrs(new JLabel("Верш.1"),vertexName1 ) );
        addEdge.add( glueParametrs(new JLabel("Верш.2"),vertexName2 ) );
        addEdge.add( glueParametrs(new JLabel("Вес"   ), edgeWeight ) );

        return addEdge;
    }

    private void createEdge( JTextField vertexName1, JTextField vertexName2) {
        Edge edge = new Edge(-1,-1);
        // Считывание параметров добавляемого ребра
            edge.v1 = Integer.parseInt(vertexName1.getText());
            edge.v2 = Integer.parseInt(vertexName2.getText());
        graphField.addE(edge);
    }

    private JPanel glueParametrs(JLabel f1, JTextField f2) {
        JPanel parametr = new JPanel();
        parametr.add(f1);
        parametr.add(f2);
        return parametr;
    }

    private JPanel createAddVertex(){

        JTextField vertexName = new JTextField(); vertexName.setPreferredSize( SIZE_OF_INPUT_FIELD );

        JButton addVertexButton = new JButton(new AbstractAction("Добавить вершину") {
            @Override
            public void actionPerformed(ActionEvent e) { addVertex(); }
        });

        JPanel addVertex = new JPanel( new GridLayout(3,1) );

        addVertex.add(addVertexButton);
        addVertex.add( glueParametrs(new JLabel("Верш."),vertexName ) );

        return addVertex;
    }

    private void addVertex(){
        graphField.addV();
        repaint();
    }
}
