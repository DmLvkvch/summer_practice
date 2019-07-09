package com.company;

import javax.swing.*;
import java.util.*;

public class TopSort {
    private LeftControlPanel leftControlPanel = new LeftControlPanel();
    private Graph graph;
    private final static int WHITE = 0;
    private final static int GREY = 1;
    private final static int BLACK = 2;
    private final static int RED = 3;
    private Stack<Integer> stack;
    public LinkedList<Integer> ans = null;
    private Stack<DFSState> states = new Stack<>();
    private LinkedList<Integer> list = new LinkedList<>();
    private List <Integer> cycle = new ArrayList<>();


    public TopSort(Graph g){
        graph = g;
        init();
    }

    private void init(){
        stack = new Stack<>();
    }

    boolean alg(){
        boolean Cycle = false;
        for(int i = 0; i < graph.V();i++){
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

    private boolean DFS(int v){
        if(graph.checkV(v).c == GREY) {
            boolean CYCLE = true;
            graph.checkV(v).c = RED;
            int i = list.size()-1;
            while(list.get(i)!=v){
                graph.checkV(list.get(i)).c = RED;
                i--;
            }
            System.out.println("CYCLE: " + v);
            return true;
        }
        if(graph.checkV(v).c == BLACK) {
            return false;
        }
        list.add(v);
        graph.checkV(v).c = GREY;
        System.out.println("красим " + v + " в серый цвет");
        for (int i = 0; i < graph.checkV(v).way.size(); i++){
            if(DFS(graph.checkV(v).way.get(i))) {
                return true;
            }
        }
        stack.push(v);
        graph.checkV(v).c = BLACK;
        System.out.println("красим " + v + " в черный цвет");
        return false;
    }

    private boolean allVisited(Collection<Integer> vertexes) {
        boolean answ = true;
        for (Integer integer : vertexes) {
            if (graph.checkV(integer).c == 0) {
                answ = false;
            }
        }
        return answ;
    }

    private String stepDFS() {
        DFSState state = states.pop();
        if (graph.checkV(state.vertex).c == RED) {
            ans = null;
            return "Шаг невозможен: цикл " + cycle;
        }
        if (graph.checkV(state.vertex).c == BLACK) {
            return "зашли в черную вершину";
        }

        if (state.nextChild == 0) { // не вернулись в вершину из следующей, а зашли из предыдущей
            if (graph.checkV(state.vertex).c == GREY) { // цикл
                System.out.println("CYCLE: " + state.vertex);
                graph.checkV(state.vertex).c = RED;
                cycle.add(state.vertex);
                int i = list.size() - 1;
                while(list.get(i)!= state.vertex){
                    graph.checkV(list.get(i)).c = RED;
                    cycle.add(list.get(i));
                    i--;
                }
                return "Цикл: " + cycle;
            }

            graph.checkV(state.vertex).c = GREY;
            list.add(state.vertex);

            if (graph.checkV(state.vertex).way.size() == 0) { // нет потомков
                states.push(new DFSState(state.vertex, 1)); // на следующем шаге покрасим в черный
                System.out.println("Pushing " + states.peek());
                return ("красим " + state.vertex + " в серый цвет" + "\n");
            }
            else {
                states.push(new DFSState(state.vertex, state.nextChild + 1));
                System.out.println("Pushing " + states.peek());

                states.push(new DFSState(graph.checkV(state.vertex).way.get(state.nextChild), 0));
                System.out.println("Pushing " + states.peek());
                System.out.println("красим " + state.vertex + " в серый цвет");
                return ("красим " + state.vertex + " в серый цвет" + "\n");
            }


            //return builder.toString();
            //return;
        }

        // вернулиись из следующей
        if (state.nextChild < graph.checkV(state.vertex).way.size() ) {

            states.push(new DFSState(state.vertex, state.nextChild + 1));
            System.out.println("Pushing " + states.peek());

            states.push(new DFSState(graph.checkV(state.vertex).way.get(state.nextChild), 0));
            System.out.println("Pushing " + states.peek());
            return "На этом шаге красить вершину " + state.vertex + " в черный нельзя, потому что у нее есть нечерные потомки";

        }

        // все потомки посещены
        stack.push(state.vertex);
        graph.checkV(state.vertex).c = BLACK;
        if (graph.checkV(state.vertex).way.size() == 0) {
            return "красим вершину " + state.vertex + " в черный цвет, потому что у нее нет потомков" + "\n";
        }
        else {
            return "красим вершину " + state.vertex + " в черный цвет, потому что все ее потоки посещены" + "\n";
        }
    }

    private boolean allBlack(Collection<Integer> collection) {
        for (Integer i : collection) {
            if (graph.checkV(i).c != BLACK) {
                return false;
            }
        }
        return true;
    }


    String step() {
        String retVal;
        while (!states.empty() && graph.checkV(states.peek().vertex).c == BLACK) {
            states.pop();
        } // пропустили все черные вершин, не будем в них заходить
        if (states.empty()) {
            for (Integer i: graph.VertexList()) {
                if (graph.checkV(i) != null && graph.checkV(i).c == WHITE) {
                    states.push(new DFSState(i, 0));
                    break;
                }
            }
        } // начинаем смотреть следующую компоненту сильной связности
        if (!states.empty()) {// делать шаг
            retVal = stepDFS();
            return retVal;
        }
        else { // все вершины просмотрены, выводим ответ
            // разворачиваем стек, получаем ответ
            ans = new LinkedList<>();
            int k = stack.size();
            for(int i = 0;i < k; i++){
                ans.add(stack.peek());
                stack.pop();
            }
            System.out.println("Answer: " + ans);
            return "Алгоритм выполнен";
        }
    }



    void to_start() {
        System.out.println("to start");
        states.clear();
        ans = null;
        stack.clear();
        list.clear();
        for (Integer vertex : graph.VertexList()) {
            graph.checkV(vertex).c = WHITE;
        }
    }

    void alg(Graph g){
        graph = g;
        ans = new LinkedList<>();
        stack.clear();
        for(int i = 0;i<graph.V();i++){
            graph.checkV(graph.VertexList().get(i)).c = 0;
        }
        alg();
    }
}