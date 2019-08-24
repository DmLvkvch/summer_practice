package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static com.company.PAR_S.*;

public class Di extends JFrame {
    private JPanel contentPane;
    private JButton buttonFile;
    private JButton buttonDraw;
    private JButton topSortButton;
    private JButton stepButton;
    private JTextField textField1;
    private JLabel label1;


    public Di(Graph graph, TopSort sort) {
        setTitle("TopSort");
        this.graph = graph;
        this.sort = sort;
        this.setBounds(100, 100, 1050, 700);
        setContentPane(contentPane);
        //setModal(true);
        getRootPane().setDefaultButton(buttonFile);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        buttonFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onFile();
            }
        });

        buttonDraw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onDraw();
            }
        });

        // call onCancel() when cross is clicked
        //setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onDraw();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onDraw();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onFile() {
        JFileChooser fileopen = new JFileChooser();
        String vertexE = "";
        String path = "";
        label1.setText("Read from file");
        int ret = fileopen.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileopen.getSelectedFile();
            path = file.getAbsolutePath();
            try {
                Scanner sc = null;
                sc = new Scanner(new File(path));
                while (sc.hasNext())
                    vertexE += sc.nextLine() + " ";
                sc.close();
                String[] data = vertexE.split(" ");
            } catch (FileNotFoundException e1) {
                System.err.println(e1);
            }
            textField1.setText(vertexE);
            System.out.println(vertexE);
            label1.setText("Comments");
        }


    }

    private void onDraw() {
        String input = textField1.getText();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            if (Character.isDigit(input.charAt(i))) {
                list.add(Character.getNumericValue(input.charAt(i)));
            }
        }
        for (int i = 0; i < list.size() - 1; i++) {
            graph.addE(list.get(i), list.get(i + 1));
            i++;
        }
        String ans = textField1.getText();
        System.out.println(ans);
        label1.setText(" was drawn");
    }


    private void createUIComponents() {
        label1 = new JLabel("Comments");
    }

    private int z = 0;
    HashMap<Integer, ActiveVertex> points = new HashMap<>();
    HashMap<Integer, ActiveVertex> sort_points = new HashMap<>();
    Graph graph;
    TopSort sort;
    private boolean flag = false;


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
        points.put(n, new ActiveVertex(this, n, r.nextInt(600 - VERTEX_D) + VERTEX_R, r.nextInt(500 - VERTEX_D) + VERTEX_R));
        add(points.get(n));
    }

    public void addE(Edge edge) {
        graph.addE(edge.v1, edge.v2);
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Vector <Integer> Xs = new Vector<>();
        Vector <Integer> Ys = new Vector<>();

        super.paint(g);
        int j = 0;
        for (int i = 0; i < graph.VertexList().size(); i++) {
            int absc = rnd(VERTEX_D, 800 - VERTEX_D);
            int ord = rnd(2*VERTEX_D, 350 - VERTEX_D);
            for (j = 0; j < Xs.size(); j++){
                if (Xs.get(j) - 2*VERTEX_D < absc || Xs.get(j) + 2*VERTEX_D > absc)
                    absc = rnd(VERTEX_D, 800 - VERTEX_D);
                Xs.add(absc);
            }
            for (j = 0; j < Ys.size(); j++){
                if (Ys.get(j) - 2*VERTEX_D < ord || Ys.get(j) + 2*VERTEX_D > ord)
                    ord = rnd(2*VERTEX_D, 359 - VERTEX_D);
                Ys.add(ord);
            }
            points.put(i, new ActiveVertex(this, i, absc, ord));

        }
        drawGraph(g, points);
        int x = 100;
        LinkedList<Integer> l = sort.ans;
        for (int i = 0; i < sort.ans.size(); i++) {
            sort_points.put(l.get(i), new ActiveVertex(this, i, x, 550));
            if(sort.ans.size() < 9)
                x += 100;
            else
                x += 50;
        }
        flag = true;
        drawGraph(g, sort_points);
    }

    public static int rnd(int min, int max)
    {
        Random r = new Random();
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    private void drawGraph(Graphics g, HashMap<Integer, ActiveVertex> points) {
        Edge edge;
        ActiveVertex i;
        int av = 0;
        for (int w = 0; w < points.size(); w++) {

            for (; !points.containsKey(av); av++) {
            }
            ;
            i = points.get(av);
            av++;

            boolean inRes = graph.checkV(i.v) != null ? true : false;

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
        if (flag == false) {
            g.setColor(EDGE_LINE_COLOR);
            g.drawLine(v1.x, v1.y, v2.x, v2.y);
        } else {
            g.setColor(EDGE_CIRKLE_LINE_COLOR);
            if (z % 2 == 0) {
                g.drawArc(v1.x, 500, v2.x - v1.x, 100, 0, 180);
                z++;
            } else {
                g.drawArc(v1.x, 500, v2.x - v1.x, 100, 0, -180);
                z++;
            }

        }
        double beta = Math.atan2((v2.y) - (v1.y - 9), v2.x - v1.x);
        double alpha = Math.PI / 10;
    }

    // Отрисовка вершины
    private void drawVertex(Graphics g, int v, HashMap<Integer, ActiveVertex> points) {
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
