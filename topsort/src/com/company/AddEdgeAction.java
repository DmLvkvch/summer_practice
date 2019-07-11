package com.company;

public class AddEdgeAction extends AbstractAction{
    private Edge edge;
    public AddEdgeAction(Graph graph, Edge edge) {
        super(graph);
        this.edge = edge;
    }

    @Override
    public void cancel() {
        this.graph.removeE(edge.v1, edge.v2);
    }
}
