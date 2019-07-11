package com.company;

public abstract class AbstractAction {
    protected Graph graph;

    public AbstractAction(Graph graph) {
        this.graph = graph;
    }

    abstract public void cancel();
}
