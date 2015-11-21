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

package com.graphaware.module.algo.generator.distribution;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Simple {@link DegreeDistribution} where the distribution can be directly passed into the constructor.
 */
public class MutableSimpleDegreeDistribution extends SimpleDegreeDistribution implements MutableDegreeDistribution {

    public MutableSimpleDegreeDistribution(List<Integer> degrees) {
       super(degrees);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(int index, int value) {
        degrees.set(index, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decrease(int index) {
        degrees.set(index, degrees.get(index) - 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sort(Comparator<Integer> comparator) {
        Collections.sort(degrees, comparator);
    }
}
