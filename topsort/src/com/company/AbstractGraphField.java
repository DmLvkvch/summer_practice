package com.company;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import static com.company.PAR_S.*;

public abstract class AbstractGraphField extends JPanel  {
    private int z = 0;
    protected HashMap<Integer, ActiveVertex> points = new HashMap<>();
    protected Graph graph;

    AbstractGraphField(Graph graph) {
        setLayout(null);
        this.graph = graph;
    }
    //максимальный номер вершины ищем ( для добавления например)
    private int max(Graph graph) {
        int max = -1;
        for (int i = 0; i < graph.VertexList().size(); i++) {
            if (max < graph.VertexList().get(i)) {
                max = graph.VertexList().get(i);
            }
        }
        return max;
    }

    protected void drawGraph(Graphics g, HashMap<Integer, ActiveVertex> points) {
        Edge edge;

        ActiveVertex i;
        int av = 0;
        for (int w = 0; w < points.size(); w++) {

            for ( ; !points.containsKey(av); av++) {};
            //выбираем из мапы вершин первую, которая там содержится
            i = points.get(av);
            av++;

            // boolean inRes = graph.checkV(i.v)!=null ? true : false;
            //проходим по всем вершинам
            for (int j = 0; j < max(graph)+1; j++) {
                if ( ( edge = graph.checkE(i.v, j)) != null ) {
                    Color color;
                    color = BASE_EDGE_COLOR;
                    drawEdge(g, edge, color, points);
                }
                g.setColor(/*inRes ? RESULT_VERTEX_COLOR :*/ BASE_VERTEX_COLOR);
                drawVertex(g, i.v, points);
            }
        }
    }
    protected abstract void drawEdge(Graphics g, Edge edge, Color color,HashMap<Integer, ActiveVertex> points);
    abstract void drawArrow(Graphics g, Point source, Point drain);
    protected void drawVertex(Graphics g, int v, HashMap<Integer, ActiveVertex> points) {
        drawCircle(g, points.get(v).point.x,  points.get(v).point.y, VERTEX_R);
        drawInt(g, points.get(v).point.x, points.get(v).point.y, v);
    }

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