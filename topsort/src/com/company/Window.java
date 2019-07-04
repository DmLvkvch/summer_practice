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

public class Window extends JFrame{
    private int z = 0;
    public String input = new String();
    private JTextPane textField;
    private JTextPane textArea;
    private JButton addEdge;
    private JButton CreateGraph;
    private JButton Step;
    private JButton RunAlg;
    private JButton readFromFile;
    private JButton readFromField;
    private JPanel rootPanel;
    HashMap<Integer, ActiveVertex> points = new HashMap<>();
    HashMap<Integer, ActiveVertex> sort_points = new HashMap<>();
    Graph graph;
    TopSort sort;
    private boolean flag = false;
    public Window(Graph graph, TopSort sort) {
        this.graph = graph;
        this.sort = sort;
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
        initActiveVertexesCords();
    }

    private void initActiveVertexesCords() {
        for (int i = 0; i < graph.VertexList().size(); i++) {
            Random r = new Random();
            points.put(i, new ActiveVertex(this, i, r.nextInt(600 - VERTEX_D) + VERTEX_R, r.nextInt(500 - VERTEX_D) + VERTEX_R));
        }
    }

    private int emptyPoint() {
        int i;
        for (i = 0; points.containsKey(i); i++) {
        }
        ;
        return i;
    }

    public void addV() {
        int n = emptyPoint();
        graph.addV(n);
        Random r = new Random();
        points.put(n, new ActiveVertex(this, n, r.nextInt(600- VERTEX_D)+ VERTEX_R, r.nextInt(500- VERTEX_D)+ VERTEX_R));
        add(points.get(n));
    }

    public void addE(Edge edge) {
        graph.addE(edge.v1, edge.v2);
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawRect(5, 50, 900, 450);

        drawGraph(g, points);
        int x = 100;
        LinkedList<Integer> l = sort.ans;
        for (int i = 0; i < sort.ans.size(); i++) {
                sort_points.put(l.get(i), new ActiveVertex(this, i, x, 550));
                x += 100;
        }
        flag = true;
        drawGraph(g, sort_points);
    }

    private void drawGraph(Graphics g, HashMap<Integer, ActiveVertex> points) {
        Edge edge;
        ActiveVertex i;
        int av = 0;
        for (int w = 0; w < points.size(); w++) {

            i = points.get(av);
            av++;

            boolean inRes = graph.checkV(i.v) != null;

            for (int j = i.v; j < graph.V(); j++) {
                if ((edge = graph.checkE(i.v, j)) != null) {
                    Color color;
                    if (inRes && (graph.checkE(i.v, j) != null)) color = RESULT_EDGE_COLOR;
                    else color = BASE_EDGE_COLOR;
                    drawEdge(g, edge, color, points);
                }
                g.setColor(inRes ? RESULT_VERTEX_COLOR : BASE_VERTEX_COLOR);
                drawVertex(g, i.v, points);
            }
        }
    }

    private void drawEdge(Graphics g, Edge edge, Color color, HashMap<Integer, ActiveVertex> points) {
        int x = 30;
        Point v1 = new Point(points.get(edge.v1).point.x, points.get(edge.v1).point.y);
        Point v2 = new Point(points.get(edge.v2).point.x, points.get(edge.v2).point.y);

        ((Graphics2D) g).setStroke(EDGE_LINE_THIKNESS);  // Устанавливаем толщину ребра
        if(flag==false) {
            g.setColor(EDGE_LINE_COLOR);
            g.drawLine(v1.x, v1.y, v2.x, v2.y);
        }else {
            g.setColor(EDGE_CIRKLE_LINE_COLOR);
            if(z%2==0) {
                g.drawArc(v1.x, 500, v2.x - v1.x, 100, 0, 180);
                z++;
            }
            else{
                g.drawArc(v1.x, 500, v2.x - v1.x, 100, 0, -180);
                z++;
            }

        }
        double beta = Math.atan2((v2.y)-(v1.y-9), v2.x-v1.x);
        double alpha = Math.PI/10;
    }

    // Отрисовка вершины
    private void drawVertex(Graphics g, int v,HashMap<Integer, ActiveVertex> points) {
        drawCircle(g, points.get(v).point.x, points.get(v).point.y, VERTEX_R);
        drawInt(g, points.get(v).point.x, points.get(v).point.y, v);
    }

    // Пишет text в точку (x,y)
    private void drawInt(Graphics g, int x, int y, int text) {
        g.setColor(TEXT_COLOR);
        Font font = new Font("Default", Font.PLAIN, TEXT_SIZE);  //Шрифт

        g.setFont(font);

        FontMetrics fm = g.getFontMetrics(font);

        g.drawString(Integer.toString(text),
                x - fm.stringWidth(Integer.toString(text)) / 2,
                y + fm.getAscent() / 2);
    }


    private void drawCircle(Graphics g, int cX, int cY, int rad) {
        g.fillOval(cX - rad, cY - rad, rad * 2, rad * 2);

        ((Graphics2D) g).setStroke(new BasicStroke(2));
        g.setColor(CIRCLE_BORDERLINE_COLOR);
        g.drawOval(cX - rad, cY - rad, rad * 2, rad * 2);
    }


}
