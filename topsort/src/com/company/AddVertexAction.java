package com.company;

public class AddVertexAction extends AbstractAction{
    private ActiveVertex activeVertex;
    public AddVertexAction(Graph graph, ActiveVertex activeVertex) {
        super(graph);
        this.activeVertex = activeVertex;
    }

    @Override
    public void cancel() {
        graph.removeV(activeVertex.v);
        if (activeVertex.parent instanceof SourceGraphField) {
            ((SourceGraphField) activeVertex.parent).points.remove(activeVertex.v);
            activeVertex.parent.remove(activeVertex);
        }
    }

    @Override
    public void redo() {
        graph.addV(activeVertex.v);
        if (activeVertex.parent instanceof SourceGraphField) {
            ((SourceGraphField) activeVertex.parent).points.put(activeVertex.v, activeVertex);
            activeVertex.parent.add(activeVertex);
        }
    }
}
