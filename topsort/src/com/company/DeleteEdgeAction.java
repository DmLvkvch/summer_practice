package com.company;

public class DeleteEdgeAction extends AbstractAction {
    private Edge edge;
    public DeleteEdgeAction(Graph graph, Edge edge) {
        super(graph);
        this.edge = edge;
    }

    @Override
    public void cancel() {
        this.graph.addE(edge.v1, edge.v2);
    }

    @Override
    public void redo() {
        this.graph.removeE(edge.v1, edge.v2);
    }
}
