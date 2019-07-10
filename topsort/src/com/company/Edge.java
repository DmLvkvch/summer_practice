package com.company;

import java.util.Objects;

public class Edge {
        public Edge(int v1, int v2) {
            this.v1 = v1;
            this.v2 = v2;
        }
        public  Edge(int v1, int v2, int color){
            this.v1 = v1;
            this.v2 = v2;
            this.c = color;
        }
        public int c = 0;
        public int v1;
        public int v2;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return v1 == edge.v1 &&
                v2 == edge.v2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(v1, v2);
    }
}
