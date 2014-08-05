package com.graphaware.module.algo.generator.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WeightedReservoirSamplerTest {

    /**
     * Tests the weighted reservoir sampling
     * @throws Exception
     */
    @Test
    public void testRandomChoice() throws Exception {
        WeightedReservoirSampler sampler = new WeightedReservoirSampler();
        List<Integer> weights = new ArrayList<>(Arrays.asList(5,10,15,20));
        List<Integer> omit    = new ArrayList<>(Arrays.asList(0,2));

        for (int i = 0; i < 100; ++i)
            System.out.println("random choice: " + sampler.randomIndexChoice(weights, 2));

    }
}
