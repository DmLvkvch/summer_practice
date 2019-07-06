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
    GraphField graphField;
    private String input = new String();
    private Graph graph;
    private TopSort topSort;
    public RigthControlPanel(Graph graph, GraphField graphField, TopSort topSort) {
        this.graphField = graphField;
        this.graph = graph;
        this.topSort = topSort;
        this.setLayout(null);
        this.setPreferredSize(new Dimension(630,700));
        //this.setBackground(Color.BLUE);

        JLabel textFieldLabel = new JLabel("Enter data:", SwingConstants.CENTER);
        //JTextPane textField = new JTextPane();
        JTextPane textArea = new JTextPane();
        JButton addEdge = new JButton("add edge");
        //JButton CreateGraph = new JButton("make graph");
        JButton step = new JButton("next step");
        JButton runAlg = new JButton("run alg");
        JButton readFromFile = new JButton("read form file");
        JButton CreateGraph = new JButton("create graph");


        textFieldLabel.setSize(550, 25);
        textFieldLabel.setLocation((getPreferredSize().width - textFieldLabel.getSize().width) / 2,0);

        textArea.setSize(550, 100);
        textArea.setLocation((getPreferredSize().width - textArea.getSize().width) / 2, textFieldLabel.getSize().height);

        Dimension buttonSize = new Dimension((textArea.getWidth()- 10) / 2, 25);
        readFromFile.setSize(buttonSize);
        readFromFile.setLocation(textArea.getX(), textArea.getY() + textArea.getHeight() + 10);

        CreateGraph.setSize(buttonSize);
        CreateGraph.setLocation(textArea.getLocation().x + readFromFile.getWidth() + 10, textArea.getLocation().y + textArea.getSize().height + 10);

        step.setSize(buttonSize);
        runAlg.setSize(buttonSize);

        step.setLocation(textArea.getX(), 650 - step.getHeight() - 10);
        runAlg.setLocation(textArea.getX() + step.getWidth() + 10, 650 - step.getHeight() - 10);
        this.add(textFieldLabel);
        this.add(addEdge);
        //this.add(CreateGraph);
        this.add(step);
        this.add(runAlg);
        this.add(readFromFile);
        this.add(CreateGraph);
        //this.add(textField);
        this.add(textArea);

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
                            input+=line+' ';
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
                graphField.foo(true);
            }
        });
    }

}