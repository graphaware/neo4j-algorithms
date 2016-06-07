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

package com.graphaware.module.algo.generator.relationship;

import com.graphaware.common.log.LoggerFactory;
import com.graphaware.common.util.SameTypePair;
import com.graphaware.common.util.UnorderedPair;
import com.graphaware.module.algo.generator.config.NumberOfNodesBasedConfig;
import junit.framework.Assert;
import org.junit.Test;
import org.neo4j.logging.Log;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CompleteGraphRelationshipGeneratorTest {

    private static final Log LOG = LoggerFactory.getLogger(CompleteGraphRelationshipGeneratorTest.class);

    @Test
    public void testCompleteGraphGenerator() {
        int numberOfNodes = 6;

        NumberOfNodesBasedConfig num = new NumberOfNodesBasedConfig(numberOfNodes);
        CompleteGraphRelationshipGenerator cg = new CompleteGraphRelationshipGenerator(num);

        List<SameTypePair<Integer>> edges = cg.doGenerateEdges();

        assertIsComplete(edges, numberOfNodes);
    }

    private void assertIsComplete(List<SameTypePair<Integer>> edges, int numberOfNodes) {
        assertEquals(edges.size(), (int)(.5 * numberOfNodes * (numberOfNodes - 1)));

        for(Integer i = 0; i < numberOfNodes; i++) {
            for(Integer j = i + 1; j < numberOfNodes; j++) {
                assertTrue(edges.contains(new UnorderedPair<Integer>(i, j)));
            }
        }
    }
}
