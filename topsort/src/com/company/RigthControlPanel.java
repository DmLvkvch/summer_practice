package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class RigthControlPanel extends JPanel {
    //AbstractGraphField graphField;
    private String input = new String();
    private Graph graph;
    private JPanel parent;
    private SourceGraphField graphField;
    private SortedGraphField sortedGraphField;
    TopSort topSort ;
    public RigthControlPanel(Graph graph, JPanel parent, SourceGraphField graphField, SortedGraphField sortedGraphField ) {
        this.graphField = graphField;
        this.sortedGraphField = sortedGraphField;
        this.parent = parent;
        this.graph = graph;
        this.setLayout(null);
        this.setPreferredSize(new Dimension(630,700));
        //this.setBackground(Color.BLUE);



        JLabel textFieldLabel = new JLabel("Enter data:", SwingConstants.CENTER);
        JTextPane textArea = new JTextPane();
        JScrollPane jsp = new JScrollPane(textArea);
        JButton addEdge = new JButton("add edge");
        JButton step = new JButton("next step");
        JButton runAlg = new JButton("run alg");
        JButton readFromFile = new JButton("read form file");
        JButton CreateGraph = new JButton("create graph");
        JButton toStartButton = new JButton("Go back to start");



        textFieldLabel.setSize(550, 25);
        textFieldLabel.setLocation((getPreferredSize().width - textFieldLabel.getSize().width) / 2,0);


        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jsp.setSize(550, 250);
        jsp.setLocation((getPreferredSize().width - jsp.getSize().width) / 2, textFieldLabel.getSize().height);

        //textArea.setSize(550, 100);
        //textArea.setLocation((getPreferredSize().width - textArea.getSize().width) / 2, textFieldLabel.getSize().height);

        Dimension buttonSize = new Dimension((jsp.getWidth()- 10) / 2, 25);
        readFromFile.setSize(buttonSize);
        readFromFile.setLocation(jsp.getX(), jsp.getY() + jsp.getHeight() + 10);

        CreateGraph.setSize(buttonSize);
        CreateGraph.setLocation(jsp.getX() + readFromFile.getWidth() + 10, jsp.getY() + jsp.getSize().height + 10);


        runAlg.setSize(jsp.getWidth(), buttonSize.height);
        runAlg.setLocation(jsp.getX(), this.getPreferredSize().height - runAlg.getHeight() - 10);

        step.setSize(buttonSize);
        step.setLocation(runAlg.getX(), this.getPreferredSize().height - step.getHeight() - 10 - runAlg.getHeight() - 10);

        toStartButton.setSize(buttonSize);
        toStartButton.setLocation(step.getX() + step.getWidth() + 10, step.getY());


        this.add(jsp);
        this.add(textFieldLabel);
        this.add(addEdge);
        this.add(step);
        this.add(runAlg);
        this.add(toStartButton);
        this.add(readFromFile);
        this.add(CreateGraph);
        //this.add(textArea);
        TopSort topSort = new TopSort(graph);
        CreateGraph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = textArea.getText();
                graph.clear();
                graphField.repaint();
                LinkedList<Integer> list = new LinkedList<>();
                for(int i = 0;i<input.length();i++){
                    if(Character.isDigit(input.charAt(i))){
                        list.add(Character.getNumericValue(input.charAt(i)));
                    }
                }
                for(int i = 0;i<list.size()-1;i++){
                    graph.addE(list.get(i), list.get(i+1));
                    i++;
                }
                graphField.repaint();
            }
        });

        readFromFile.addActionListener(new ActionListener() {
            @Override
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
                            input+=line+"\n";
                        }
                        textArea.setText(input);
                    }
                    catch(IOException ex){
                    }
                }
            }
        });

        runAlg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doAllTheSteps();
                foo();
            }
        });

        step.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(topSort.step() == false){
                    foo();
                }
                graphField.repaint();
                //foo();
            }
        });
    }

    private void foo() {
        System.out.println("foo called");
        this.sortedGraphField.setGraph(graph);
        this.sortedGraphField.repaint();

    }

    private void doAllTheSteps() {


        //this.sortedGraphField.setGraph(graph);
        //this.sortedGraphField.repaint();

    }

}