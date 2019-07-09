package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Stack;

import static com.company.PAR_S.*;

public class ActiveVertex extends JComponent implements MouseListener, MouseMotionListener {
    private JPanel parent;
    private Color fillColor = Color.WHITE;

    Graph graph;

    private Point center;
    final int vertex;
    private static Stack<ActiveVertex> stack = new Stack<>();

    private Point mouse = new Point();
    private boolean flagCanMove = false;

    ActiveVertex(JPanel parent, int vertex, int x, int y, Graph graph ) {
        this.graph = graph;
        this.parent = parent;
        this.vertex = vertex;
        this.center = new Point(x, y);

        setSize(new Dimension( VERTEX_D + 2, VERTEX_D + 2));
        setLocation(x - VERTEX_R - 1, y - VERTEX_R - 1);
        parent.add(this);

        setClickable(true);
        setMoveable(true);
    }

    public void setClickable(boolean clickable) {
        if (clickable) {
            addMouseListener(this);
        }
        else {
            removeMouseListener(this);
        }
    }

    public void setMoveable(boolean moveable){
        if (moveable) {
            addMouseMotionListener(this);
        }
        else {
            removeMouseMotionListener(this);
        }
    }

    public Point getCenter() {
        return center;
    }

    public void setColor(Color color) {
        this.fillColor = color;
        this.repaint();
        parent.repaint(this.getBounds());
    }

    @Override
    public void paintComponent(Graphics g) {
        drawVertex(g);
        parent.repaint();
    }

    private void drawVertex(Graphics g) {
        g.setColor(fillColor);
        g.fillOval(0, 0, VERTEX_D, VERTEX_D);
        ((Graphics2D)g).setStroke(EDGE_LINE_THIKNESS); // VERTEX_LINE_THIKNESS
        g.setColor( CIRCLE_BORDERLINE_COLOR );
        g.drawOval(0, 0, VERTEX_D, VERTEX_D);

        g.setColor(TEXT_COLOR);
        Font font = new Font("Default", Font.PLAIN, TEXT_SIZE);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics(font);
        g.drawString(Integer.toString(vertex),
                VERTEX_R - fm.stringWidth(Integer.toString(vertex)) / 2,
                VERTEX_R + fm.getAscent() / 2);
    }

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

            int x = center.x + dx;
            if (x - VERTEX_R < 0) center.x = VERTEX_R;
            else if (x + VERTEX_R>900) center.x = 900- VERTEX_R;
            else center.x = x;

            int y = center.y + dy;
            if (y - VERTEX_R < 0) center.y = VERTEX_R;
            else if (y + VERTEX_R>500) center.y = 500- VERTEX_R;
            else center.y = y;

            setLocation(center.x- VERTEX_R, center.y- VERTEX_R);
            parent.repaint();
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        stack.push(this);
        if(stack.size()==2) {
            int k = stack.peek().vertex;
            stack.pop();
            if (k == stack.peek().vertex) {
                graph.removeV(stack.peek().vertex);
                stack.pop();
                parent.repaint();
            } else {
                graph.addE(stack.peek().vertex, k);
                stack.pop();
                parent.repaint();
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
