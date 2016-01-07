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

package com.graphaware.module.algo.generator.config;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NumberOfNodesBasedConfigTest {
    @Test
    public void shouldCorrectlyEvaluateValidConfig() {
        assertFalse(new NumberOfNodesBasedConfig(-1).isValid());
        assertFalse(new NumberOfNodesBasedConfig(0).isValid());
        assertFalse(new NumberOfNodesBasedConfig(1).isValid());

        assertTrue(new NumberOfNodesBasedConfig(3).isValid());
        assertTrue(new NumberOfNodesBasedConfig(5).isValid());

        assertTrue(new NumberOfNodesBasedConfig(Integer.MAX_VALUE - 1).isValid());
        assertTrue(new NumberOfNodesBasedConfig(Integer.MAX_VALUE).isValid());
        //noinspection NumericOverflow
        assertFalse(new NumberOfNodesBasedConfig(Integer.MAX_VALUE + 1).isValid());
    }
}
