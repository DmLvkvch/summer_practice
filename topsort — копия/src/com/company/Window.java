package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import static com.company.PAR_S.*;

public class Window extends JPanel{
    public String input = new String();
    HashMap<Integer, ActiveVertex> points = new HashMap<>();
    HashMap<Integer, ActiveVertex> sort_points = new HashMap<>();
    Graph graph;
    TopSort sort;
    private boolean flag = false;
    private GraphField graphField;
    ///////////////////////////////

    public Window(Graph graph, TopSort sort) {
        this.sort = sort;
        this.graph = graph;
        graphField = new GraphField(graph, sort);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;
        constraints.gridy   = 0  ;
        constraints.gridx = 0;
        contentPanel.add(graphField, constraints);


       /*
        JLabel commentsLabel = new JLabel("Comments", SwingConstants.CENTER);
        commentsLabel.setOpaque(true);
        commentsLabel.setBackground(Color.cyan);
        commentsLabel.setForeground(Color.red);
        */
        LeftControlPanel leftPanel = new LeftControlPanel();

        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.weightx   = 0.0;
        constraints.gridwidth = 1;
        constraints.gridx     = 0;
        constraints.gridy     = 1;
        contentPanel.add(leftPanel, constraints);

        RigthControlPanel cp  = new RigthControlPanel(this.graph, graphField, sort);
        constraints.gridx = 1;      // первая ячейка таблицы по горизонтали
        constraints.gridy = 0;
        constraints.gridheight = 2;
        contentPanel.add(cp, constraints);


        add(contentPanel);
    }

    /*
    public Window(Graph graph, TopSort sort) {
        this.graph = graph;
        this.sort = sort;
        GraphField graphField= new GraphField(graph, sort);
        add(graphField);
        /*
        this.graph = graph;
        this.sort = sort;
        this.setSize(1050,700);
        //this.setResizable(false);
        this.setMinimumSize(new Dimension(1400,700));
        //this.setTitle("TopSort");
        this.rootPanel = new JPanel();
        rootPanel.setLayout(null);      //абсолютное позиционирование
        rootPanel.setBounds(0,0,1400,700);
        this.setBounds(300,100,750,700);
        //setContentPane(rootPanel);
        addEdge = new JButton("add edge");
        CreateGraph = new JButton("make graph");
        Step = new JButton("next step");
        RunAlg = new JButton("run alg");
        readFromFile = new JButton("read form file");
        textField = new JTextPane();
        readFromField = new JButton("read from field");
        textArea = new JTextPane();
        textArea.setBounds(500,550, 363,100);
        textField.setBounds(1000,100,363,100);
        this.addEdge.setBounds(1200,350,163,24);
        this.CreateGraph.setBounds(1200,400,163,24);
        this.Step.setBounds(1200,450,163,24);
        this.RunAlg.setBounds(1200,500,163,24);
        this.readFromField.setBounds(1200,550,163,24);
        this.readFromFile.setBounds(1200,600,163,24);
        rootPanel.add(addEdge);
        rootPanel.add(CreateGraph);
        rootPanel.add(Step);
        rootPanel.add(RunAlg);
        rootPanel.add(readFromFile);
        rootPanel.add(readFromField);
        rootPanel.add(textField);
        rootPanel.add(textArea);
        readFromFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fd = new JFileChooser();//диалоговое окно
                int ret = fd.showDialog(null, "Open file");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fd.getSelectedFile();       //получение выбранного файла

                    try(BufferedReader reader =new BufferedReader(new FileReader(file)))
                    {
                        // читаем из файла построчно
                        String line;
                        while ((line = reader.readLine()) != null) {
                            input+=line+' ';
                        }
                        textField.setText(input);
                    }
                    catch(IOException ex){
                    }
                }
            }
        });

        readFromField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input = textField.getText();
            }
        });

        CreateGraph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input = textField.getText();
                ArrayList<Integer> list = new ArrayList<>();
                for(int i = 0;i<input.length();i++){
                    if(Character.isDigit(input.charAt(i))){
                        list.add(Character.getNumericValue(input.charAt(i)));
                    }
                }
                for(int i = 0;i<list.size()-1;i++){
                    graph.addE(list.get(i), list.get(i+1));
                    i++;
                }
            }
        });

         */
    }

