package com.graphaware.module.algo.generator.relationship;

import com.graphaware.common.util.SameTypePair;
import com.graphaware.module.algo.generator.config.ErdosRenyiConfig;
import junit.framework.TestCase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class ErdosRenyiGraphRelationshipGeneratorTest extends TestCase {

    private static final Logger LOG = LoggerFactory.getLogger(ErdosRenyiGraphRelationshipGeneratorTest.class);

    @Test
    public void testErdosRenyiGeneratorValidity() {
        LOG.info("Starting ER validity check");
        doGenerateEdges(1000, 2000);   // Uses simple generator
        doGenerateEdges(1000, 400000); // Uses index <-> edge mapping to avoid already added edges
    }


    @Test(timeout = 5 * 60 * 1000)   //5 mins
    public void testErdosRenyiGeneratorPerformance() {
        LOG.info("Starting ER performance test (timeout 5 mins)");
        doGenerateEdges(1_000_000, 2_000_000); // Uses simple generator
        doGenerateEdges(4_000, 4_000_000); // Uses index <-> edge mapping
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
     * @param numberOfEdges
     * @param edges
     */
    private void assertCorrectNumberOfEdgesGenerated(long numberOfEdges, List<SameTypePair<Integer>> edges) {
         assertEquals(numberOfEdges, edges.size());
    }

}