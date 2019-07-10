package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Stack;

import static com.company.PAR_S.*;

public class ActiveVertex extends JPanel implements MouseListener, MouseMotionListener {
    Graph graph;
    JPanel parent;
    Point point;
    final int v;
    static Stack<ActiveVertex> stack = new Stack<>();
    static Stack<Integer> color_stack = new Stack<>();
    private Point mouse = new Point();
    static Stack<ActiveVertex> cur_ver = new Stack<>();
    private boolean flagCanMove = false;


    ActiveVertex( JPanel parent, int v, int x, int y, Graph graph ) {
        this.graph = graph;
        this.parent = parent;
        this.v = v;

       // Random random = new Random();
        point = new Point(x, y);

        setSize(new Dimension( VERTEX_D, VERTEX_D));
        setLocation(point.x- VERTEX_R, point.y- VERTEX_R);

        addMouseMotionListener(this);
        addMouseListener(this);
    }

    // Движение вершины
    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println(mouse.x+" "+mouse.y);
        flagCanMove = true;
        mouse.x = e.getX();
        mouse.y = e.getY();
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        flagCanMove = false;
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        if (flagCanMove) {
            int dx = e.getX() - mouse.x;
            int dy = e.getY() - mouse.y;

            int x = point.x + dx;
            if (x- VERTEX_R < 0) point.x = VERTEX_R;
            else if (x+ VERTEX_R>900) point.x = 900- VERTEX_R;
            else point.x = x;

            int y = point.y + dy;
            if (y - VERTEX_R < 0) point.y = VERTEX_R;
            else if (y+ VERTEX_R>500) point.y = 500- VERTEX_R;
            else point.y = y;

            setLocation(point.x- VERTEX_R, point.y- VERTEX_R);
            parent.repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton()==MouseEvent.BUTTON1) {
            if(cur_ver.size()!=0){
                graph.checkV(cur_ver.peek().v).c = color_stack.peek();
                cur_ver.clear();
                color_stack.clear();
            }
            cur_ver.push(this);
            color_stack.push(graph.checkV(this.v).c);
            graph.checkV(this.v).c = 4;
            parent.repaint();
            stack.push(this);
            if (stack.size() == 2) {
                int k = stack.peek().v;
                stack.pop();
                if (k == stack.peek().v) {
                    graph.removeV(stack.peek().v);
                    cur_ver.clear();
                    color_stack.clear();
                    stack.pop();
                    parent.repaint();
                } else {
                    graph.addE(stack.peek().v, k);
                    stack.pop();
                    if(cur_ver.size()!=0){
                        graph.checkV(cur_ver.pop().v).c = color_stack.pop();
                        cur_ver.clear();
                        color_stack.clear();
                    }
                    parent.repaint();
                }
            }
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
