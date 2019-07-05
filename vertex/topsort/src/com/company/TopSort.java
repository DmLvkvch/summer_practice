package com.company;

import java.util.LinkedList;
import java.util.Stack;

public class TopSort {
    private Graph graph;
    private int V;
    private int E;
    private boolean[] used;
    private Stack<Integer> stack;
    public LinkedList<Integer> ans = new LinkedList<>();
    public TopSort(Graph g){
        graph = g;
        init();
    }

    void init(){
        V = graph.V();
        E = graph.E();
        used = new boolean[V+1];
        stack = new Stack<>();
        for(int i = 0;i<V+1;i++)
            used[i] = false;
    }

    private void DFS(int pos){
        used[pos] = true;
        System.out.println(pos);
        for(int i = 0;i<graph.checkV(pos).way.size();i++){
            if(!used[graph.checkV(pos).way.get(i)])
                DFS(graph.checkV(pos).way.get(i));
        }
        stack.push(pos);
    }

    void Alg(Graph g){
        graph = g;
        init();
        for(int i = 0;i<V;i++){
            if(!used[i])
                DFS(i);
        }
        int k = stack.size();
        for(int i = 0;i<k;i++) {
            ans.add(stack.peek());
            System.out.println(stack.peek());
            stack.pop();
        }
    }
}
