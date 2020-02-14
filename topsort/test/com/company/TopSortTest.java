package com.company;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TopSortTest {
    private static Graph graph;

    @BeforeEach
    void setUp() {
        graph = new Graph(); // сбросили состояние графа, которое могло остаться после предыдущих тестов
        // TODO: complete implementation
        /*
         Выполняется перед запуском каждого теста. Напрмер, здесь может быть задан граф,
         на котором будет выполняться тест, если он один и тот же для всех тестов.
         */
    }

    @AfterEach
    void tearDown() {
        // TODO: implement
        // выполняется после запуска каждого теста
    }

    @Test
    void algTest1() {
        // TODO: implement
        /*
        Тест метода alg.
        Тестов нужно столько, сколько покроет все сценарии работы метода.
        То есть для каждой пары стуаций, где выполнение может пойти по разным веткам (напрмер, в if),
        нужно как минимум два разных теста.
        В тесте определяются значения полей класса, на которых будет выполняться метод, после этого он вызывается.
        После выполнения метода нужно сравнить поля, которые могли быть изменены, с ожидаемыми значениями
        с помощью метода AssertEquals(expected, actual). Еще надо сравнить возвращаемое значение с фактическим.
        Если  actual != expected, проект не соберется.
         */
        assertTrue( 1 == 1);
        assertEquals(1,1);
    }
    @Test
    void algTest2() {
        graph.addV(0);
        graph.addV(1);
        graph.addV(2);
        graph.addE(0, 1);
        graph.addE(0, 2);
        graph.addE(1, 2);
        TopSort topSort = new TopSort(graph);
        topSort.alg();
        List<Integer> actual = topSort.ans;

        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(1);
        expected.add(2);

        // проверили ответ
        assertEquals(expected, actual);


        actual = topSort.getCycle();
        expected = new LinkedList<Integer>();
        // проверили поле цикл
        assertEquals(expected, actual);


        // остальные поля не проверяем, ибо они не могли быть изменены тестируемым методом
    }

    @Test
    void step() {
        // TODO: implement
    }

    @Test
    void to_start() {
        // TODO: implement
    }

    @Test
    void alg1() {
        // TODO: implement
    }
}