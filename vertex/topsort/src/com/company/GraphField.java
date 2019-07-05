package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import static com.company.PAR_S.*;

public class GraphField extends JPanel {
    private int z = 0;
    HashMap<Integer, ActiveVertex> points = new HashMap<>();
    HashMap<Integer, ActiveVertex> sort_points = new HashMap<>();
    Graph graph;
    TopSort sort;
    private boolean flag = false;
    GraphField(Graph graph, TopSort sort){
        setLayout(null);
        setPreferredSize( SIZE_OF_GRAPH_FIELD );
        this.graph = graph;
        this.sort = sort;
        for(int i = 0;i<graph.VertexList().size();i++){
            Random r = new Random();
            points.put(i, new ActiveVertex(this, i));
            add(points.get(i));
        }

    }

    public void addE(Edge edge){
        graph.addE(edge.v1, edge.v2);
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(new Color(255,255,255));
        g.fillRect(0,0, 900,600);
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

            boolean inRes = graph.checkV(i.v)!=null ? true : false;

            for (int j = i.v; j < graph.V()+1; j++) {
                if ( ( edge = graph.checkE(i.v,j)) != null ) {
                    Color color;
                    if (inRes && (graph.checkE(i.v,j)!= null) ) color = RESULT_EDGE_COLOR;
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
}