/*
 * Copyright (c) 2013-2016 GraphAware
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

import java.util.Comparator;

/**
 * A mutable {@link DegreeDistribution}.
 */
public interface MutableDegreeDistribution extends DegreeDistribution {

    /**
     * Set degree by index.
     *
     * @param index of the degree to set.
     * @param value to set.
     */
    void set(int index, int value);

    /**
     * Decrease the degree at the specified index by 1.
     *
     * @param index to decrease.
     */
    void decrease(int index);

    /**
     * Sort the degree distribution using the given comparator.
     *
     * @param comparator comparator.
     */
    void sort(Comparator<Integer> comparator);
}
