package com.company;

import java.util.LinkedList;

public class Vertex {
    public int v;
    public LinkedList<Integer> way = new LinkedList<>(); // смежные вершины
    public Vertex(int v) {
        this.v = v;
    }
}
