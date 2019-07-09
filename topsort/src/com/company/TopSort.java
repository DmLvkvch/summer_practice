package com.company;

import javax.swing.*;
import java.util.*;

public class TopSort {
    private LeftControlPanel leftControlPanel = new LeftControlPanel();
    private Graph graph;
    private int V;
    private int E;
    private boolean[] used;
    private final static int WHITE = 0;
    private final static int GREY = 1;
    private final static int BLACK = 2;
    private final static int RED = 3;
    public LinkedList<Integer> Edge = new LinkedList<>();
    private Stack<Integer> stack;
    public LinkedList<Integer> ans = new LinkedList<>();
    private Stack<DFSState> states = new Stack<>();
    private boolean CYCLE = false;
    private LinkedList<Integer> list = new LinkedList<>();
    //private int nextToVisit;


    public TopSort(Graph g){
        graph = g;
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
            CYCLE = true;
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

    public void stepDFS() {
        DFSState state = states.pop();
        System.out.println("шаг начат. состояние: " + state);
        System.out.println("стек состояний: " + states);

        if (state.nextChild == 0) { // не вернулись в вершину из следующей, а зашли из предыдущей
            if (graph.checkV(state.vertex).c == GREY) {
                leftControlPanel = new LeftControlPanel();
                leftControlPanel.commentsLabel.setText("CYCLE");
                System.out.println("CYCLE: " + state.vertex);
                int i = list.size()-1;
                graph.checkV(state.vertex).c = RED;
                while(list.get(i)!=state.vertex){
                    graph.checkV(list.get(i)).c = RED;
                    i--;
                }
                return;
            }
            if (graph.checkV(state.vertex).c == BLACK) {
                return;
            }
            Edge.add(state.vertex);
            graph.checkV(state.vertex).c = GREY;
            leftControlPanel = new LeftControlPanel();
            leftControlPanel.commentsLabel.setText("красим " + state.vertex + " в серый цвет");
            list.add(state.vertex);
            System.out.println("красим " + state.vertex + " в серый цвет");
            //return;
        }

        if (state.nextChild < graph.checkV(state.vertex).way.size()) { // вернулиись из следующей
            states.push(new DFSState(state.vertex, state.nextChild + 1));
            System.out.println("Pushing " + states.peek());
            states.push(new DFSState(graph.checkV(state.vertex).way.get(state.nextChild), 0));
            System.out.println("Pushing " + states.peek());
            return;
        }

        // все потомки посещены
        stack.push(state.vertex);
        graph.checkV(state.vertex).c = BLACK;
        leftControlPanel = new LeftControlPanel();
        leftControlPanel.commentsLabel.setText("красим " + state.vertex + " в черный цвет");
        System.out.println("красим " + state.vertex + " в черный цвет");
    }



    boolean step() {
        if (states.empty()) {
            for (int i = 0; i < graph.V(); i ++) {
                if (graph.checkV(i) != null && graph.checkV(i).c == 0) {
                    states.push(new DFSState(i, 0));
                    break;
                }
            }
        } // начинаем смотреть следующую компоненту сильной связности
        if (!states.empty()) {// делать шаг
            stepDFS();
        }
        else { // все вершины просмотрены, выводим ответ
            // разворачиваем стек, получаем ответ
            int k = stack.size();
            for(int i = 0;i < k; i++){
                ans.add(stack.peek());
                stack.pop();
            }
            System.out.println("Answer: " + ans);
            return false;
        }
        return true;
    }

    void to_start(){
        System.out.println("to start");
        states.clear();
        ans.clear();
        stack.clear();
        list.clear();
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