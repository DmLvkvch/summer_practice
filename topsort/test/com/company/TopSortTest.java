package com.company;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TopSortTest {

    @BeforeEach
    void setUp() {
        // TODO: implement
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
    void alg() {
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