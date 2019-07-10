package com.company;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;

import static com.company.PAR_S.*;

public class SortedGraphField extends AbstractGraphField  {
    private HashMap<Integer, ActiveVertex> sort_points = new HashMap<>();
    private TopSort sort;
    private int z = 0;
    int h = 20;
    int height = 150;
    int width = 90;
    public SortedGraphField(Graph graph) {
        super(graph);
        width = 900;
        height = 15;
        sort = new TopSort(graph);
        LinkedList<Integer> sorted = sort.ans;
        setPreferredSize(new Dimension(900, 150));
        if (sort.ans != null) {
            for (int i = 0; i < sort.ans.size(); i++) {
                sort_points.put(sorted.get(i), new ActiveVertex(this, i, i * 100,
                        this.getPreferredSize().height / 2, graph));
            }
        }
    }

    public void setGraph(Graph graph) {
        z = 0;
        this.graph = graph;
        sort.alg(graph);
        LinkedList<Integer> sorted = sort.ans;
        sort_points.clear();
        for (int i = 0; i < sorted.size(); i++) {
            sort_points.put(sorted.get(i), new ActiveVertex(this, sorted.get(i), i * 100 + VERTEX_D,
                    this.getPreferredSize().height / 2, graph));
        }
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        z = 0;
        h = 20;
        //setPreferredSize(new Dimension(width*sort_points.size(), 150));
        g.setColor(new Color(171,174,181));


        while (sort_points.size() > this.getPreferredSize().width / 100) {
            this.setPreferredSize(new Dimension(this.getPreferredSize().width + 100,
                    this.getPreferredSize().height));
        }
        while (sort_points.size() < this.getPreferredSize().width / 100) {
            this.setPreferredSize(new Dimension(this.getPreferredSize().width - 100,
                    this.getPreferredSize().height));
        }
        if (this.getPreferredSize().width < 900) {
            this.setPreferredSize(new Dimension(900, this.getPreferredSize().height));
        }
        this.setSize(this.getPreferredSize());
        g.fillRect(0, 0, this.getPreferredSize().width, this.getPreferredSize().height);
        drawGraph(g, sort_points);

    }

    @Override
    protected void drawEdge(Graphics g, Edge edge, Color color, HashMap<Integer, ActiveVertex> points) {
        Point v1 = new Point(points.get(edge.v1).point.x, points.get(edge.v1).point.y);
        Point v2 = new Point(points.get(edge.v2).point.x, points.get(edge.v2).point.y);
        ((Graphics2D)g).setStroke( EDGE_LINE_THIKNESS );  // Устанавливаем толщину ребра
        g.setColor( Color.BLACK );
        drawArrow(g, v1, v2);

    }

    @Override
    void drawArrow(Graphics g, Point source, Point drain) {
        int length = 10;
        double angle = Math.PI / 12;
        ((Graphics2D) g).setStroke(new BasicStroke(2));
        if(Math.abs(source.x-drain.x)<=100){
            drawarrow(g, source, drain);
        }
        else {
            if (z % 2 == 0) {
                g.drawArc(source.x, source.y - 50 - VERTEX_R, Math.abs(source.x - drain.x), 100, 0, 180);
                Point A = new Point(drain.x, drain.y - VERTEX_R);
                Point B = new Point(A.x - (int) (length * Math.cos(angle)), A.y - (int) (length * Math.cos(angle)));
                Point C = new Point(A.x + (int) (length * Math.cos(angle)), A.y - (int) (length * Math.cos(angle)));
                g.drawLine(A.x, A.y, B.x, B.y);
                g.drawLine(A.x, A.y, C.x, C.y);
                h += 5;
                z++;
            } else {
//            h+=5;
                g.drawArc(source.x, source.y - 50 + VERTEX_R, Math.abs(source.x - drain.x), 100, 0, -180);
                Point A = new Point(drain.x, drain.y + VERTEX_R);
                Point B = new Point(A.x - (int) (length * Math.cos(angle)), A.y + (int) (length * Math.cos(angle)));
                Point C = new Point(A.x + (int) (length * Math.cos(angle)), A.y + (int) (length * Math.cos(angle)));
                g.drawLine(A.x, A.y, B.x, B.y);
                g.drawLine(A.x, A.y, C.x, C.y);
                z++;
            }
        }
    }

    void drawarrow(Graphics g, Point source, Point drain){
        double arrowAngle = Math.PI / 10;
        double length = 20;
        Point arrowTop = computeA(source, drain);
        Point arrowEnd1 = computeB(source, drain, arrowAngle, length);
        Point arrowEnd2 = computeC(source, drain, arrowAngle, length);
        g.drawLine(arrowTop.x, arrowTop.y, arrowEnd1.x, arrowEnd1.y);
        g.drawLine(arrowTop.x, arrowTop.y, arrowEnd2.x, arrowEnd2.y);

        double edgeAngle = computeEdgeAngle(source, drain);
        g.drawLine(source.x + (int)(VERTEX_R * Math.cos(edgeAngle)),
                source.y + (int)(VERTEX_R * Math.sin(edgeAngle)), arrowTop.x, arrowTop.y);
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
}