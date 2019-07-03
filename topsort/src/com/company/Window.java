package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import static com.company.PAR_S.*;

public class Window extends JFrame{
    public String input = new String();
    public JTextPane textField;
    public JTextPane textArea;
    public JButton addEdge;
    public JButton CreateGraph;
    public JButton Step;
    public JButton RunAlg;
    public JButton readFromFile;
    public JButton readFromField;
    public JPanel rootPanel;

    public Window() {
        this.setSize(1050,700);
        this.setResizable(false);
        this.setMinimumSize(new Dimension(1400,700));
        this.setTitle("TopSort");
        this.rootPanel = new JPanel();
        rootPanel.setLayout(null);      //абсолютное позиционирование
        rootPanel.setBounds(0,0,1400,700);

        this.setBounds(300,100,750,700);
        setContentPane(rootPanel);
        addEdge = new JButton("add edge");
        CreateGraph = new JButton("make graph");
        Step = new JButton("next step");
        RunAlg = new JButton("run alg");
        readFromFile = new JButton("read form file");
        textField = new JTextPane();
        readFromField = new JButton("read from field");
        textArea = new JTextPane();
        textArea.setBounds(100,500,563,100);
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
                            textField.setText(input);
                        }
                    }
                    catch(IOException ex){
                    }
                }
            }
        });
    }


}
