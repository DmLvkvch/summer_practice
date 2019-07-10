package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.TimeUnit;

import static com.company.PAR_S.*;

public class GraphField extends JPanel implements MouseListener, MouseMotionListener {
    private int z = 0;
    HashMap<Integer, ActiveVertex> points = new HashMap<>();
    HashMap<Integer, ActiveVertex> sort_points = new HashMap<>();
    Graph graph;
    TopSort sort;
    private boolean flag = false;
    GraphField(Graph graph, TopSort sort){
        flag = false;
        addMouseMotionListener(this);
        addMouseListener(this);
        setLayout(null);
        setPreferredSize(new Dimension(900, 600));
        this.graph = graph;
        this.sort = sort;
        for(int i = 0;i<graph.VertexList().size();i++){
            Random r = new Random();
            points.put(i, new ActiveVertex(this, i, r.nextInt(600 - VERTEX_D) + VERTEX_R, r.nextInt(500 - VERTEX_D) + VERTEX_R, graph));
            add(points.get(i));
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        graph.addV(graph.V());
        points.put(graph.V() - 1, new ActiveVertex(this,graph.V() - 1, e.getX(), e.getY(), graph));
        add(points.get(graph.V()-1));
        repaint();
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public void addE(Edge edge){
        graph.addE(edge.v1, edge.v2);
        repaint();
    }

    void foo(boolean bool){
        if(bool){
            flag = true;
            repaint();
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(new Color(255,255,255));
        g.fillRect(0,0, 900,600);
        if (points.size() > graph.VertexList().size()) {
            int k = points.size();
            for (int i = 0; i < k; i++) {
                if (!graph.VertexList().contains((Integer) i)) {
                    points.remove(i);
                }
            }
        }
        for (int i = 0; i < graph.VertexList().size(); i++) {
            Random r = new Random();
            if (!points.containsKey(i)) {
                points.put(i, new ActiveVertex(this, i, r.nextInt(600 - VERTEX_D) + VERTEX_R, r.nextInt(500 - VERTEX_D) + VERTEX_R, graph));
                add(points.get(i));
            }

        }
        drawGraph(g, points);

        if(flag) {
            // g.setColor(new Color(0, 0, 0));                // Цвет рамки
            //((Graphics2D) g).setStroke(new BasicStroke(4));  // Толщина рамки
            //g.drawRect(0, 0, 900, 600);

            drawGraph(g, sort_points);
            sort.alg(graph);
            int x = 100;
            LinkedList<Integer> l = sort.ans;
            for (int i = 0; i < sort.ans.size(); i++) {
                sort_points.put(l.get(i), new ActiveVertex(this, i, x, 550, graph));
                x += 100;
            }
            drawGraph(g, sort_points);
            flag = false;
        }

    }

    // Отрисовка графа
    private void drawGraph(Graphics g,HashMap<Integer, ActiveVertex> points) {
        Edge edge;

        ActiveVertex i;
        int av = 0;
        for (int w=0; w < points.size(); w++) {

            for ( ; !points.containsKey(av); av++) {};
            i = points.get(av);
            av++;

            // boolean inRes = graph.checkV(i.v)!=null ? true : false;
            for (int j = 0; j < graph.V()+1; j++) {
                if ( ( edge = graph.checkE(i.v,j)) != null ) {
                    Color color;
                    //if (inRes && (graph.checkE(i.v,j)!= null) ) color = RESULT_EDGE_COLOR;
                    //else
                    color = BASE_EDGE_COLOR;
                    drawEdge(g, edge, color, points);
                }
                g.setColor(/*inRes ? RESULT_VERTEX_COLOR :*/ BASE_VERTEX_COLOR);
                drawVertex(g, i.v, points);
            }
        }
    }

    //Отрисовка ребра
    private void drawEdge(Graphics g, Edge edge, Color color,HashMap<Integer, ActiveVertex> points){
        Point v1 = new Point(points.get(edge.v1).point.x, points.get(edge.v1).point.y);
        Point v2 = new Point(points.get(edge.v2).point.x, points.get(edge.v2).point.y);

        ((Graphics2D)g).setStroke( EDGE_LINE_THIKNESS );  // Устанавливаем толщину ребра

        g.setColor( EDGE_LINE_COLOR );
        if(!flag) {
            g.drawLine(v1.x, v1.y, v2.x, v2.y);
            drawArrow(g, v1, v2);
        } else {
            if(z%2==0) {
                g.drawArc(v1.x, 500, v2.x - v1.x, 100, 0, 180);
                z++;
            }
            else{
                g.drawArc(v1.x, 500, v2.x - v1.x, 100, 0, -180);
                z++;
            }

        }
    }

    private double computeEdgeAngle(Point sourse, Point drain){
        return Math.atan2((double)(drain.y - sourse.y), (double)(drain.x - sourse.x));
    }
    private Point computeA(Point sourse, Point drain) {
        double edgeAngle = computeEdgeAngle(sourse, drain);
        return new Point((int)(drain.x - VERTEX_R * Math.cos(edgeAngle)),
                (int)(drain.y - VERTEX_R * Math.sin(edgeAngle)));
    }

    private Point computeB(Point sourse, Point drain, double angle, double length) {
        Point arrowTop = computeA(sourse, drain);
        double edgeAngle = computeEdgeAngle(sourse, drain);
        return new Point((int)(arrowTop.x - length * Math.cos(edgeAngle + angle)),
                (int)(arrowTop.y - length * Math.sin( edgeAngle + angle)));
    }
    private Point computeC(Point sourse, Point drain, double angle, double length) {
        Point arrowTop = computeA(sourse, drain);
        double edgeAngle = computeEdgeAngle(sourse, drain);
        return new Point((int)(arrowTop.x - length * Math.cos(edgeAngle - angle)),
                (int)(arrowTop.y - length * Math.sin( edgeAngle - angle)));
    }

    private void drawArrow(Graphics g, Point source, Point drain) {
        double angle = Math.PI / 10;
        double length = 40;
        Point arrowTop = computeA(source, drain);
        Point arrowEnd1 = computeB(source, drain, angle, length);
        Point arrowEnd2 = computeC(source, drain, angle, length);
        g.drawLine(arrowTop.x, arrowTop.y, arrowEnd1.x, arrowEnd1.y);
        g.drawLine(arrowTop.x, arrowTop.y, arrowEnd2.x, arrowEnd2.y);
    }

    // Отрисовка вершины
    private void drawVertex(Graphics g, int v,HashMap<Integer, ActiveVertex> points) {
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