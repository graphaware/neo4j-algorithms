/*
 * Copyright (c) 2013-2015 GraphAware
 *
 * This file is part of the GraphAware Framework.
 *
 * GraphAware Framework is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received a copy of
 * the GNU General Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

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

//        for (int i = 0; i < 100; ++i)
//            System.out.println("random choice: " + sampler.randomIndexChoice(weights, 2));

    }
}
