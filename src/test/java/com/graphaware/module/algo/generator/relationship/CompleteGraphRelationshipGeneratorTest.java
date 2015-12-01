package com.graphaware.module.algo.generator.relationship;

import com.graphaware.common.util.SameTypePair;
import com.graphaware.common.util.UnorderedPair;
import com.graphaware.module.algo.generator.config.NumberOfNodesBasedConfig;
import junit.framework.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CompleteGraphRelationshipGeneratorTest {

    private static final Logger LOG = LoggerFactory.getLogger(CompleteGraphRelationshipGeneratorTest.class);

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
