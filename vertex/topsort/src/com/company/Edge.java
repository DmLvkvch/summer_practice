package com.company;

import java.awt.*;

import static com.company.PAR_S.*;

public class Edge {
        public Edge(int v1, int v2) {
            this.v1 = v1;
            this.v2 = v2;
        }
        public Color color = EDGE_LINE_COLOR;
        public int v1;
        public int v2;
}
