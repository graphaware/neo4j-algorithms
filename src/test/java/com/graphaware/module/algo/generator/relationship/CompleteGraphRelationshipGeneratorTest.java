package com.graphaware.module.algo.generator.relationship;

import com.graphaware.module.algo.generator.config.NumberOfNodesBasedConfig;
import junit.framework.TestCase;

public class CompleteGraphRelationshipGeneratorTest extends TestCase {
    public void testDoGenerateEdges() {
        CompleteGraphRelationshipGenerator cg1 = new CompleteGraphRelationshipGenerator(new NumberOfNodesBasedConfig(3));
        System.out.println(cg1.generateEdges().toString());

        CompleteGraphRelationshipGenerator cg2 = new CompleteGraphRelationshipGenerator(new NumberOfNodesBasedConfig(4));
        System.out.println(cg2.generateEdges().toString());

        CompleteGraphRelationshipGenerator cg3 = new CompleteGraphRelationshipGenerator(new NumberOfNodesBasedConfig(5));
        System.out.println(cg3.generateEdges().toString());
    }
}