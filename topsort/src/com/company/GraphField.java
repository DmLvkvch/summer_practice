package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import static com.company.PAR_S.*;

public class GraphField extends JPanel{
    private Graph g = new Graph();
    int maxV = 0;
    HashMap<Integer, ActiveVertex> points = new HashMap<Integer, ActiveVertex>();

    private int emptyPoint(){
        int i;
        for(i = 0; points.containsKey(i); i++){};
        return i;
    }

    GraphField() {

        setLayout(null);
        setPreferredSize( SIZE_OF_GRAPH_FIELD );    //Размер рамки
        fitchaForFastCreateGraphFromFile();
    }


    public void addV() {
        int n = emptyPoint();
        if (n > maxV){maxV = n;}
        g.addV(n);
        points.put(n, new ActiveVertex(this, n));
        add(points.get(n));

    }
    public void addE(Edge edge){
        try {
            g.addE(edge.v1, edge.v2);
        }
        catch (RuntimeException exception) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка: " + exception.getLocalizedMessage(),
                    "Ошибка добавления ребра",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        repaint();
    }

    public void removeV(Vertex v) {


            g.removeV(v.v);
            remove(points.get(v.v));
            points.remove(v.v);

        repaint();
    }
    public void removeE(Edge edge) {
        try {
            g.removeE(edge.v1, edge.v2);
        } catch (RuntimeException exception) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка: " + exception.getLocalizedMessage(),
                    "Ошибка удаления ребра",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        repaint();
    }

    @Override
    public void paint(Graphics g) {

        g.setColor( GRAPH_FIELD_BACKGROUND );
        g.fillRect(0,0,100000,100000);

        drawGraph(g);

        g.setColor( GRAPH_FIELD_BORDER );                // Цвет рамки
        ((Graphics2D)g).setStroke(new BasicStroke(4));  // Толщина рамки
        g.drawRect( 0, 0,
                SIZE_OF_GRAPH_FIELD.width,
                SIZE_OF_GRAPH_FIELD.height);        // Нарисовать рамку
    }

    // Отрисовка графа
    private void drawGraph(Graphics g) {
        Edge edge;

        ActiveVertex i;
        int av = 0;
        for (int w=0; w < points.size(); w++) {

            for ( ; !points.containsKey(av); av++) {};
            i = points.get(av);
            av++;
            boolean inRes = this.g.checkV(i.v) != null ? true : false;

            for (int j = i.v; j < maxV+1; j++) {
                if ( ( edge = this.g.checkE(i.v,j)) != null ) {
                    Color color;
                    if (inRes && (this.g.checkE(i.v,j)!= null) ) color = RESULT_EDGE_COLOR;
                    else color = BASE_EDGE_COLOR;
                    drawEdge(g, edge, color);
                }
                g.setColor(inRes ? RESULT_VERTEX_COLOR : BASE_VERTEX_COLOR);
                drawVertex(g, i.v);
            }
        }
    }

    //Отрисовка ребра
    private void drawEdge(Graphics g, Edge edge, Color color){

        Point v1 = new Point(points.get(edge.v1).point.x, points.get(edge.v1).point.y);
        Point v2 = new Point(points.get(edge.v2).point.x, points.get(edge.v2).point.y);

        ((Graphics2D)g).setStroke( EDGE_LINE_THIKNESS );  // Устанавливаем толщину ребра

        g.setColor( EDGE_LINE_COLOR );
        g.drawLine(v1.x, v1.y, v2.x, v2.y);

        int x = (v1.x+v2.x)/2;
        int y = (v1.y+v2.y)/2;

        g.setColor(color);
        g.fillOval(x-14, y-14, EDJE_CIRKLE_R, EDJE_CIRKLE_R);

        ((Graphics2D)g).setStroke( EDGE_CIRKLE_LINE_THKNESS);
        g.setColor( EDGE_CIRKLE_LINE_COLOR );
        g.drawOval(x-14, y-14,  EDJE_CIRKLE_R, EDJE_CIRKLE_R);

        drawInt(g, x, y, 0);
    }

    // Отрисовка вершины
    private void drawVertex(Graphics g, int v) {
        drawCircle(g, points.get(v).point.x,  points.get(v).point.y, VERTEX_R);
        drawInt(g, points.get(v).point.x, points.get(v).point.y, v);
    }

    // Пишет text в точку (x,y)
    private void drawInt(Graphics g, int x, int y, int text) {
        g.setColor(TEXT_COLOR);
        Font font = new Font("Default", Font.PLAIN, TEXT_SIZE);  //Шрифт

        g.setFont(font);

        FontMetrics fm = g.getFontMetrics(font);

        g.drawString(Integer.toString(text),
                x-fm.stringWidth(Integer.toString(text))/2,
                y+fm.getAscent()/2);
    }



    private void drawCircle(Graphics g, int cX, int cY, int rad) {
        g.fillOval(cX-rad, cY-rad, rad*2, rad*2);

        ((Graphics2D)g).setStroke(new BasicStroke(2));
        g.setColor( CIRCLE_BORDERLINE_COLOR );
        g.drawOval(cX-rad, cY-rad, rad*2, rad*2);
    }

    //Фича: найдя файл начнёт строить граф из него
    private void fitchaForFastCreateGraphFromFile()  {
        try (FileInputStream file = new FileInputStream("FastGraph.txt");) {
            Scanner scanner = new Scanner(file);

            int kolV = scanner.nextInt();

            HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

            for (int i=0; i < kolV; i++) {
                map.put( scanner.nextInt(), i);
                addV();
            }

            while (scanner.hasNext()) {
                int v1 = scanner.nextInt();
                int v2 = scanner.nextInt();
                int weight = scanner.nextInt();

                addE(new Edge(map.get(v1).intValue(), map.get(v2).intValue()));
            }
        } catch (IOException e) { }
    }
}
