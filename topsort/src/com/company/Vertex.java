package com.company;

import java.awt.*;
import java.util.Comparator;
import java.util.LinkedList;

import static com.company.PAR_S.*;

public class Vertex {
    public int v;
    public Color color = BASE_VERTEX_COLOR;
    public int c = 0;
    public LinkedList<Integer> way = new LinkedList<>(); // смежные вершины
    public Vertex(int v) {
        this.v = v;
    }
}
