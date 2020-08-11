package com.trident.hamming.correction;

import org.apache.commons.math3.util.Combinations;
import org.apache.commons.math3.util.MultidimensionalCounter;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class CombinationsTest {
    @Test
    void test() {
        var combinations = new Combinations(5, 2);
        var iterator = combinations.iterator();
        while (iterator.hasNext()) {
            System.out.println(Arrays.toString(iterator.next()));
        }
    }

    @Test
    void test2() {
        var multidimensionalCounter = new MultidimensionalCounter(3);
        var iterator = multidimensionalCounter.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
