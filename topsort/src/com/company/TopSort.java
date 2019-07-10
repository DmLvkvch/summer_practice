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
    private LinkedList<Integer> cycle = new LinkedList<>();
    private int exit_num = 0;
    public TopSort(Graph g) {
        graph = g;
        init();
    }

    private void init() {
        stack = new Stack<>();
    }

    boolean alg() {
        this.exit_num = 0;
        boolean Cycle = false;
        for (int i = 0; i < graph.V(); i++) {
            Cycle = DFS(graph.VertexList().get(i));
            if (Cycle) {
                return false;
            }
        }
        int k = stack.size();
        for (int i = 0; i < k; i++) {
            ans.add(stack.peek());
            stack.pop();
        }
        return true;
    }

    private boolean DFS(int v) {
        if (graph.checkV(v).c == GREY) {
            boolean CYCLE = true;
            graph.checkV(v).c = RED;
            int i = list.size() - 1;
            while (list.get(i) != v) {
                graph.checkV(list.get(i)).c = RED;
                i--;
            }
            System.out.println("CYCLE: " + v);
            return true;
        }
        if (graph.checkV(v).c == BLACK) {
            return false;
        }
        list.add(v);
        graph.checkV(v).c = GREY;
        System.out.println("красим " + v + " в серый цвет");
        for (int i = 0; i < graph.checkV(v).way.size(); i++) {
            if (DFS(graph.checkV(v).way.get(i))) {
                return true;
            }
        }
        stack.push(v);
        graph.checkV(v).c = BLACK;
        graph.checkV(v).exit_num = this.exit_num++;
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

    private LinkedList<Integer> reverse(LinkedList<Integer> list){
        LinkedList<Integer> new_list = new LinkedList<>();
        for(int i = 0;i<list.size();i++)
            new_list.add(list.get(list.size()-i-1));
        return new_list;
    }

    private String stepDFS() {
        DFSState state = states.pop();
        if (graph.checkV(state.vertex).c == RED) {
            ans = null;
            return "Шаг невозможен: цикл ";
        }
        if (graph.checkV(state.vertex).c == BLACK) {
            return "зашли в черную вершину " + state.vertex;
        }

        if (state.nextChild == 0) { // не вернулись в вершину из следующей, а зашли из предыдущей
            if (graph.checkV(state.vertex).c == GREY) { // цикл
                System.out.println("CYCLE: " + state.vertex);
                graph.checkV(state.vertex).c = RED;
                cycle.add(state.vertex);
                int i = list.size() - 1;
                while (list.get(i) != state.vertex) {
                    graph.checkV(list.get(i)).c = RED;
                    cycle.add(list.get(i));
                    i--;
                }
                cycle = reverse(cycle);
                return "Цикл, так как перешли в вершину "+graph.checkV(state.vertex).v+", которая окрашена в серый цвет.\nЦикл: "+
                        cycle;
            }

            graph.checkV(state.vertex).c = GREY;
            list.add(state.vertex);

            if (graph.checkV(state.vertex).way.size() == 0) { // нет потомков
                states.push(new DFSState(state.vertex, 1)); // на следующем шаге покрасим в черный
                System.out.println("Pushing " + states.peek());
                return "красим " + state.vertex + " в серый цвет";
            } else {
                states.push(new DFSState(state.vertex, state.nextChild + 1));
                System.out.println("Pushing " + states.peek());
                states.push(new DFSState(graph.checkV(state.vertex).way.get(state.nextChild), 0));
                System.out.println("Pushing " + states.peek());
                System.out.println("красим " + state.vertex + " в серый цвет");
                return "красим " + state.vertex + " в серый цвет";
            }


            //return builder.toString();
            //return;
        }

        // вернулиись из следующей
        if (state.nextChild < graph.checkV(state.vertex).way.size()) {

            states.push(new DFSState(state.vertex, state.nextChild + 1));
            System.out.println("Pushing " + states.peek());

            states.push(new DFSState(graph.checkV(state.vertex).way.get(state.nextChild), 0));
            System.out.println("Pushing " + states.peek());
            return "На этом шаге красить вершину " + state.vertex + " в зеленый нельзя, потому что у нее есть непросмотренные из нее потомки";

        }

        // все потомки посещены
        stack.push(state.vertex);
        graph.checkV(state.vertex).c = BLACK;
        list.remove((Integer)state.vertex);
        graph.checkV(state.vertex).exit_num = this.exit_num++;
        if (graph.checkV(state.vertex).way.size() == 0) {
            return "красим вершину " + state.vertex + " в зеленый цвет, потому что у нее нет потомков. В отсортированном графе она будет "+(exit_num-1)+" справа.";
        } else {
            return "красим вершину " + state.vertex + " в зеленый цвет, потому что все ее потомки посещены. В отсортированном графе она будет "+(exit_num-1)+" справа.";
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
            for (Integer i : graph.VertexList()) {
                if (graph.checkV(i) != null && graph.checkV(i).c == WHITE) {
                    states.push(new DFSState(i, 0));
                    break;
                }
            }
        } // начинаем смотреть следующую компоненту сильной связности
        if (!states.empty()) {// делать шаг
            retVal = stepDFS();
            return retVal;
        } else { // все вершины просмотрены, выводим ответ
            // разворачиваем стек, получаем ответ
            ans = new LinkedList<>();
            int k = stack.size();
            for (int i = 0; i < k; i++) {
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
        this.exit_num = 0;
        for (Integer vertex : graph.VertexList()) {
            graph.checkV(vertex).c = WHITE;
            graph.checkV(vertex).exit_num = -1;
        }
    }

    void alg(Graph g) {
        graph = g;
        ans = new LinkedList<>();
        stack.clear();
        for (int i = 0; i < graph.V(); i++) {
            graph.checkV(graph.VertexList().get(i)).c = 0;
        }
        alg();
    }
}
/*
package com.company;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import java.util.*;
public class TopSort {
    private Graph graph;
    private int V;
    private int E;
    private boolean[] used;
    int i =0;
    boolean flag = false;
    private Stack<Integer> stack;
    private  Stack<Integer> for_use = new Stack<>();
    public LinkedList<Integer> ans = new LinkedList<>();
    private Stack<Integer> nextToVisit = new Stack<>();
    private int lastVertex = 0;
    //private int nextToVisit;
    public Graph Processing_Graph(){
        return this.graph;
    }
    public TopSort(Graph g){
        this.graph = g;
        init();
    }
    private void init(){
        V = graph.V();
        E = graph.E();
        used = new boolean[graph.VertexList().size()+1];
        stack = new Stack<>();
        for(int i = 0;i<graph.VertexList().size()+1;i++)
            used[i] = false;
    }
    void DFS(int v){
        if(stack.size()==graph.V())
            return;
        if(graph.checkV(v).c == 1){
            flag = true;
           return;
        }
        if(graph.checkV(v).c == 2){
            stack.push(v);
            return;
        }
        System.out.println("красим вершину в серый цвет "+v);
        graph.checkV(v).c = 1;
        if(graph.checkV(v).way.size() == 0){
            for(int i = 0;i<graph.VertexList().size();i++){
                if(graph.checkV(graph.VertexList().get(i)).c == 0){
                    System.out.println("push for_use "+graph.VertexList().get(i));
                    for_use.push(graph.VertexList().get(i));
                    this.i = graph.VertexList().get(i);
                    return;
                }
            }
            for
            System.out.println("красим вершину в черный цвет "+v);
            graph.checkV(v).c = 2;
            System.out.println("push stack "+v);
            stack.push(v);
            for_use.pop();
            this.i = for_use.pop();
        }
        else {
            for(int i = 0;i<graph.checkV(v).way.size();i++){
                if(graph.checkV(graph.checkV(v).way.get(i)).c == 0){
                    System.out.println("push for_use prev "+graph.checkV(v).way.get(i));
                    for_use.push(graph.checkV(v).way.get(i));
                    int j = 0;
                    while(graph.VertexList().get(j)!=graph.checkV(v).way.get(i))
                        j++;
                    this.i = j;
                    return;
                }
            }
        }
    }
    void alg(){
        if(graph.VertexList().size()==0)
            return;
        if(i>=graph.VertexList().size())
            return;
        DFS(graph.VertexList().get(i));
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
 */
/*
package com.company;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.*;

public class TopSort {
    private Graph graph;
    private int V;
    private int E;
    private boolean[] used;
    int i =0;
    boolean flag = false;
    private Stack<Integer> stack;
    private  Stack<Integer> for_use = new Stack<>();
    public LinkedList<Integer> ans = new LinkedList<>();

    private Stack<Integer> nextToVisit = new Stack<>();

    private int lastVertex = 0;

    //private int nextToVisit;

    public Graph Processing_Graph(){
        return this.graph;
    }

    public TopSort(Graph g){
        this.graph = g;
        init();
    }

    private void init(){
        V = graph.V();
        E = graph.E();
        used = new boolean[graph.VertexList().size()+1];
        stack = new Stack<>();
        for(int i = 0;i<graph.VertexList().size()+1;i++)
            used[i] = false;
    }

    void DFS(int v){
        if(stack.size()==graph.V())
            return;
        if(graph.checkV(v).c == 1){
            flag = true;
           return;
        }
        if(graph.checkV(v).c == 2){
            stack.push(v);
            return;
        }
        System.out.println("красим вершину в серый цвет "+v);
        graph.checkV(v).c = 1;
        if(graph.checkV(v).way.size() == 0){
            for(int i = 0;i<graph.VertexList().size();i++){
                if(graph.checkV(graph.VertexList().get(i)).c == 0){
                    System.out.println("push for_use "+graph.VertexList().get(i));
                    for_use.push(graph.VertexList().get(i));
                    this.i = graph.VertexList().get(i);
                    return;
                }
            }
            for
            System.out.println("красим вершину в черный цвет "+v);
            graph.checkV(v).c = 2;
            System.out.println("push stack "+v);
            stack.push(v);
            for_use.pop();
            this.i = for_use.pop();
        }
        else {
            for(int i = 0;i<graph.checkV(v).way.size();i++){
                if(graph.checkV(graph.checkV(v).way.get(i)).c == 0){
                    System.out.println("push for_use prev "+graph.checkV(v).way.get(i));
                    for_use.push(graph.checkV(v).way.get(i));
                    int j = 0;
                    while(graph.VertexList().get(j)!=graph.checkV(v).way.get(i))
                        j++;
                    this.i = j;
                    return;
                }
            }
        }

    }

    void alg(){
        if(graph.VertexList().size()==0)
            return;
        if(i>=graph.VertexList().size())
            return;
        DFS(graph.VertexList().get(i));
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

 */