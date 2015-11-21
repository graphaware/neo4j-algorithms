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

package com.graphaware.module.algo.generator.relationship;

import com.graphaware.common.util.SameTypePair;
import com.graphaware.module.algo.generator.config.ErdosRenyiConfig;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertEquals;


public class ErdosRenyiGraphRelationshipGeneratorTest {

    private static final Logger LOG = LoggerFactory.getLogger(ErdosRenyiGraphRelationshipGeneratorTest.class);

    @Test
    public void testErdosRenyiGeneratorValidity() {
        LOG.info("Starting ER performance test (timeout 5 mins)");
        doGenerateEdges(20, 190); // Uses simple generator
        doGenerateEdges(10, 15); // Uses index <-> edge mapping
    }

    public void doGenerateEdges(int numberOfNodes, int numberOfEdges) {
        LOG.info("Generating edges for " + numberOfNodes + " noded and " + numberOfEdges + " edged graph");
        ErdosRenyiConfig config = new ErdosRenyiConfig(numberOfNodes, numberOfEdges);
        ErdosRenyiRelationshipGenerator er = new ErdosRenyiRelationshipGenerator(config);
        List<SameTypePair<Integer>> edges = er.doGenerateEdges(); // Integer may not be enough here!

        assertCorrectNumberOfEdgesGenerated(numberOfEdges, edges);
    }

    /**
     * Checks the length of edgeList and compares to the expected number of edges to be generated
     *
     * @param numberOfEdges number of edges in the graph
     * @param edges list of edges as SameTypePair<Integer>
     */
    private void assertCorrectNumberOfEdgesGenerated(long numberOfEdges, List<SameTypePair<Integer>> edges) {
        assertEquals(numberOfEdges, edges.size());
    }

}