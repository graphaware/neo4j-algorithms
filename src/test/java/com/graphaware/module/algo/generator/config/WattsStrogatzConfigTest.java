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

/**
 * Unit test for {@link WattsStrogatzConfig}.
 */
public class WattsStrogatzConfigTest {

    @Test
    public void shouldCorrectlyEvaluateValidConfig() {
        assertFalse(new WattsStrogatzConfig(-1, 4, 0.5).isValid());
        assertFalse(new WattsStrogatzConfig(0, 4, 0.5).isValid());
        assertFalse(new WattsStrogatzConfig(1, 4, 0.5).isValid());
        assertFalse(new WattsStrogatzConfig(2, 4, 0.5).isValid());
        assertFalse(new WattsStrogatzConfig(3, 4, 0.5).isValid());
        assertFalse(new WattsStrogatzConfig(4, 4, 0.5).isValid());
        assertTrue(new WattsStrogatzConfig(5, 4, 0.5).isValid());
        assertTrue(new WattsStrogatzConfig(6, 4, 0.5).isValid());

        assertFalse(new WattsStrogatzConfig(6, 3, 0.5).isValid());
        assertFalse(new WattsStrogatzConfig(6, 2, 0.5).isValid());
        assertFalse(new WattsStrogatzConfig(6, 1, 0.5).isValid());
        assertFalse(new WattsStrogatzConfig(6, 0, 0.5).isValid());
        assertFalse(new WattsStrogatzConfig(6, -1, 0.5).isValid());
        assertFalse(new WattsStrogatzConfig(6, 5, 0.5).isValid());
        assertFalse(new WattsStrogatzConfig(6, 6, 0.5).isValid());
        assertFalse(new WattsStrogatzConfig(6, 7, 0.5).isValid());
        assertFalse(new WattsStrogatzConfig(6, 8, 0.5).isValid());
        assertFalse(new WattsStrogatzConfig(6, 111, 0.5).isValid());

        assertFalse(new WattsStrogatzConfig(6, 4, -0.01).isValid());
        assertTrue(new WattsStrogatzConfig(6, 4, 0).isValid());
        assertTrue(new WattsStrogatzConfig(6, 4, 0.01).isValid());
        assertTrue(new WattsStrogatzConfig(6, 4, 0.99).isValid());
        assertTrue(new WattsStrogatzConfig(6, 4, 1.00).isValid());
        assertFalse(new WattsStrogatzConfig(6, 4, 1.01).isValid());
    }
}
