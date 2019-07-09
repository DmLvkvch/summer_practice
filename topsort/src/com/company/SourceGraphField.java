package com.company;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import static com.company.PAR_S.*;

public class SourceGraphField extends AbstractGraphField implements MouseListener, MouseMotionListener {
    private TopSort topSort;
    SourceGraphField(Graph graph) {
        super(graph);
        //this.topSort = topSort;
        setPreferredSize(new Dimension(900, 500));
        addMouseMotionListener(this);
        addMouseListener(this);;
        for (int i = 0; i < graph.VertexList().size(); i++){
            Random r = new Random();
            points.put(graph.VertexList().get(i), new ActiveVertex(this, graph.VertexList().get(i), r.nextInt(600 - VERTEX_D) + VERTEX_R, r.nextInt(500 - VERTEX_D) + VERTEX_R, graph));
            add(points.get(graph.VertexList().get(i)));
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(new Color(255,255,255));
        g.fillRect(0,0, 900,600);
        //добавление вершин в мапу вершин
        for (int i = 0; i < graph.VertexList().size(); i++) {
            Random r = new Random();
            if (!points.containsKey(graph.VertexList().get(i))) {
                points.put(graph.VertexList().get(i), new ActiveVertex(this, graph.VertexList().get(i), r.nextInt(600 - VERTEX_D) + VERTEX_R, r.nextInt(500 - VERTEX_D) + VERTEX_R, graph));
                add(points.get(graph.VertexList().get(i)));
            }
        }
        //если удалили в графе вершину, то и из мапы даляем, чтоб не рисовалась
        while(points.size()>graph.VertexList().size())
            if (points.size() > graph.VertexList().size()) {
                for (Map.Entry<Integer, ActiveVertex> k:
                        points.entrySet()) {
                    if(!graph.VertexList().contains(k.getValue().v)) {
                        this.remove(points.get(k.getKey()));
                        points.remove(k.getKey());
                        break;
                    }
                }
            }
        drawGraph(g, points);
    }

    @Override
    protected void drawEdge(Graphics g, Edge edge, Color color, HashMap<Integer, ActiveVertex> points){
        Point v1 = new Point(points.get(edge.v1).point.x, points.get(edge.v1).point.y);
        Point v2 = new Point(points.get(edge.v2).point.x, points.get(edge.v2).point.y);
        ((Graphics2D)g).setStroke( EDGE_LINE_THIKNESS );  // Устанавливаем толщину ребра
        if(graph.checkV(edge.v1).c == 0 & graph.checkV(edge.v2).c == 0)
                g.setColor(new Color(0,0,0));
        else if((graph.checkV(edge.v1).c == 1 & graph.checkV(edge.v2).c == 1))
            g.setColor(new Color(40,80,200));

        drawArrow(g, v1, v2);
    }

    @Override
    void drawArrow(Graphics g, Point source, Point drain){
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



    @Override
    public void mousePressed(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseDragged(MouseEvent e) {

    }
    public int max(Graph graph) {
        int max = -1;
        for (int i = 0; i < graph.VertexList().size(); i++) {
            if (max < graph.VertexList().get(i)) {
                max = graph.VertexList().get(i);
            }
        }
        return max;
    }

    boolean onEdge(int x, int y){
        return  true;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int source = 0, stock = 0;
        if (e.getButton() == MouseEvent.BUTTON3){
            Point p = e.getPoint();
            loop:
            for(int j = 0; j < points.size(); j++){
                for (int i = 0; i < /*Graph.graph.get(points.get(j).v).way.size()*/graph.checkV(points.get(j).v).way.size(); i++) {
                    double ans1 = (p.x - points.get(j).point.x) * (points.get(graph.checkV(points.get(j).v).way.get(i)).point.y - points.get(j).point.y);
                    double ans2 = (p.y - points.get(j).point.y) * (points.get(graph.checkV(points.get(j).v).way.get(i)).point.x - points.get(j).point.x);

                    System.out.println(points.get(j).point.x);
                    System.out.println(points.get(j).point.y);
                    if (Math.abs(ans1 - ans2) < 1000) {
                        source = points.get(j).v;
                        stock = graph.checkV(points.get(j).v).way.get(i);
                        break loop;
                    }
                }
            }
            if(graph.checkE(source, stock)!=null)
                graph.removeE(source, stock);
            repaint();
        }else {
            int k = max(graph) + 1;
            graph.addV(k);
            points.put(k, new ActiveVertex(this, k, e.getX(), e.getY(), graph));
            add(points.get(k));
            repaint();
        }

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
}