package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

    public class ActiveVertex extends JPanel implements MouseListener, MouseMotionListener {

        JPanel parent;
        Point point;
        final int v;
        final static int VERTEX_R = 25;
        final static int VERTEX_D = VERTEX_R*2;
        private Point mouse = new Point();

        private boolean flagCanMove = false;


        ActiveVertex( JPanel parent, int v ) {
            this.parent = parent;
            this.v = v;

            Random random = new Random();
            point = new Point(random.nextInt(600 - VERTEX_D)+ VERTEX_R,
                    random.nextInt(500- VERTEX_D)+ VERTEX_R);

            setSize(new Dimension( VERTEX_D, VERTEX_D));
            setLocation(point.x- VERTEX_R, point.y- VERTEX_R);

            addMouseMotionListener(this);
            addMouseListener(this);
        }

        // Движение вершины
        @Override
        public void mousePressed(MouseEvent e) {
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
                else if (x+ VERTEX_R>600) point.x = 600- VERTEX_R;
                else point.x = x;

                int y = point.y + dy;
                if (y- VERTEX_R < 0) point.y = VERTEX_R;
                else if (y+ VERTEX_R>500) point.y = 500- VERTEX_R;
                else point.y = y;

                setLocation(point.x- VERTEX_R, point.y- VERTEX_R);
                parent.repaint();
            }
        }

        public void mouseClicked(MouseEvent e) {
        }
        public void mouseEntered(MouseEvent e) {
        }
        public void mouseExited(MouseEvent e) {
        }
        public void mouseMoved(MouseEvent e) {

        }
    }
