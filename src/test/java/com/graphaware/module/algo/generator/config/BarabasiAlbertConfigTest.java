package com.graphaware.module.algo.generator.config;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

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
        assertFalse(new BarabasiAlbertConfig(2, 1).isValid());
        assertFalse(new BarabasiAlbertConfig(3, 1).isValid());
        assertFalse(new BarabasiAlbertConfig(3, 0).isValid());
        assertFalse(new BarabasiAlbertConfig(3, -1).isValid());
        assertFalse(new BarabasiAlbertConfig(3000, 3000).isValid());
        assertTrue(new BarabasiAlbertConfig(3000, 2999).isValid());
        assertTrue(new BarabasiAlbertConfig(3, 2).isValid());
        assertTrue(new BarabasiAlbertConfig(4, 2).isValid());
        assertTrue(new BarabasiAlbertConfig(Integer.MAX_VALUE - 1, 2).isValid());
        assertTrue(new BarabasiAlbertConfig(Integer.MAX_VALUE, 2).isValid());
        //noinspection NumericOverflow
        assertFalse(new BarabasiAlbertConfig(Integer.MAX_VALUE + 1, 2).isValid());
    }
}
