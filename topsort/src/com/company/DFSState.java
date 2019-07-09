package com.company;

class DFSState {
    int vertex;
    int nextChild;

    public DFSState(int vertex, int nextChild) {
        this.vertex = vertex;
        this.nextChild = nextChild;
    }

    @Override
    public String toString() {
        return "(" + vertex + ", " + nextChild + ")";
    }
}

