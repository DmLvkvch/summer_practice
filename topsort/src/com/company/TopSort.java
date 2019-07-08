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
        used = new boolean[graph.VertexList().size()+1];
        stack = new Stack<>();
        for(int i = 0;i<graph.VertexList().size()+1;i++)
            used[i] = false;
    }
/*
    private void DFS(int pos){
        used[pos] = true;
        System.out.println(pos);
        for(int i = 0;i<graph.checkV(pos).way.size();i++){
            if(!used[graph.checkV(pos).way.get(i)])
                DFS(graph.checkV(pos).way.get(i));
        }
        stack.push(pos);
    }
    void alg() {
        init();
        stack.clear();
        ans.clear();
        for(int i = 0;i<graph.VertexList().size();i++){
            if(!used[graph.VertexList().get(i)]) {
                DFS(graph.VertexList().get(i));
            }
        }
        int k = stack.size();
        for(int i = 0;i<k;i++) {
            ans.add(stack.peek());
            System.out.println(stack.peek());
            stack.pop();
        }
    }

 */


    boolean alg(){
        boolean Cycle = false;
        for(int i = 0;i<graph.V();i++){
            Cycle = DFS(graph.VertexList().get(i));
            if(Cycle) {
                return false;
            }
        }
        int k = stack.size();
        for(int i = 0;i<k;i++){
            ans.add(stack.peek());
            stack.pop();
        }
        return true;
    }



    boolean DFS(int v){
        if(graph.checkV(v).c == 1) {
            System.out.println("CYCLE: "+v);
            return true;
        }
        if(graph.checkV(v).c == 2) {
            return false;
        }
        graph.checkV(v).c = 1;
        System.out.println("красим "+v+" в серый цвет");
        for(int i = 0;i<graph.checkV(v).way.size();i++){
            if(DFS(graph.checkV(v).way.get(i))) {
                return true;
            }
        }
        stack.push(v);
        graph.checkV(v).c = 2;
        System.out.println("красим "+v+" в черный цвет");
        return false;
    }

    boolean dfs(int v){
        
    }

    void alg(Graph g){
        graph = g;
        ans.clear();
        stack.clear();
        for(int i = 0;i<graph.V();i++){
            graph.checkV(graph.VertexList().get(i)).c = 0;
        }
        alg();
    }
}