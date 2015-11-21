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

package com.graphaware.module.algo.generator.config;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *  Unit test for {@link BarabasiAlbertConfig}.
 */
public class BarabasiAlbertConfigTest {

    @Test
    public void shouldCorrectlyEvaluateValidConfig() {
        assertFalse(new BarabasiAlbertConfig(-1, 2).isValid());
        assertFalse(new BarabasiAlbertConfig(0, 2).isValid());
        assertFalse(new BarabasiAlbertConfig(1, 2).isValid());
        assertFalse(new BarabasiAlbertConfig(2, 2).isValid());
        assertFalse(new BarabasiAlbertConfig(3, 0).isValid());
        assertFalse(new BarabasiAlbertConfig(3, -1).isValid());
        assertFalse(new BarabasiAlbertConfig(3000, 3000).isValid());
        assertTrue(new BarabasiAlbertConfig(3, 1).isValid());
        assertTrue(new BarabasiAlbertConfig(2, 1).isValid());
        assertTrue(new BarabasiAlbertConfig(3000, 2999).isValid());
        assertTrue(new BarabasiAlbertConfig(3, 2).isValid());
        assertTrue(new BarabasiAlbertConfig(4, 2).isValid());
        assertTrue(new BarabasiAlbertConfig(Integer.MAX_VALUE - 1, 2).isValid());
        assertTrue(new BarabasiAlbertConfig(Integer.MAX_VALUE, 2).isValid());
        //noinspection NumericOverflow
        assertFalse(new BarabasiAlbertConfig(Integer.MAX_VALUE + 1, 2).isValid());
    }
}
