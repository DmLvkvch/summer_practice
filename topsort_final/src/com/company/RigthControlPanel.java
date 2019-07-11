package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class RigthControlPanel extends JPanel {
    //AbstractGraphField graphField;
    private String input = new String();
    private Graph graph;
    private JPanel parent;
    private SourceGraphField graphField;
    private SortedGraphField sortedGraphField;

    private JTextPane commentPane;
    public void setCommentPane(JTextPane commentPane) {
        this.commentPane = commentPane;
    }

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
        JButton stepBack = new JButton("Undo last action on graph field");
        JButton stepForward = new JButton("Redo last action on graph field");

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

        stepBack.setSize(buttonSize);
        stepBack.setLocation(runAlg.getX(), this.getPreferredSize().height - step.getHeight() - 10 - stepBack.getHeight() - 10 - runAlg.getHeight() - 10);

        stepForward.setSize(buttonSize);
        stepForward.setLocation(toStartButton.getX(), stepBack.getY());

        this.add(jsp);
        this.add(textFieldLabel);
        this.add(addEdge);
        this.add(step);
        this.add(runAlg);
        this.add(toStartButton);
        this.add(readFromFile);
        this.add(CreateGraph);
        this.add(stepBack);
        this.add(stepForward);
        //this.add(textArea);
        TopSort topSort = new TopSort(graph);
        CreateGraph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = textArea.getText();
                if (input.length() != 0) {
                    graph.clear();
                    graphField.repaint();
                    String[] parsed = input.split("[^0-9]");
                    for (int i = 0; i < parsed.length-1; i++) {
                        graph.addE(Integer.parseInt(parsed[i]), Integer.parseInt(parsed[i + 1]));
                        i+=2;
                    }
                    commentPane.setText(commentPane.getText());
                    graphField.repaint();
                }
                else{
                    commentPane.setText(commentPane.getText()+"Empty\n");
                }
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

        toStartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                topSort.to_start();
                graphField.repaint();
                sortedGraphField.setGraph(new Graph());
                commentPane.setText("");
            }
        });

        runAlg.addActionListener(e -> {
            doAllTheSteps();
            foo();
            commentPane.setText(topSort.alg());
            graphField.repaint();
        });

        step.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // commentLabel.setText(topSort.step());
                String comment = topSort.step() + "\r\n";
                commentPane.setText(commentPane.getText() + comment);
                if (topSort.ans != null) {
                    sortedGraphField.setGraph(graph);
                    sortedGraphField.repaint();
                }
                graphField.repaint();
            }
        });
        stepBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphField.undo();
            }
        });
        stepForward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphField.redo();
            }
        });
    }

    private void foo() {

        this.sortedGraphField.setGraph(graph);
        this.sortedGraphField.repaint();
    }

    private void doAllTheSteps() {
        //this.sortedGraphField.setGraph(graph);
        //this.sortedGraphField.repaint();

    }

}
