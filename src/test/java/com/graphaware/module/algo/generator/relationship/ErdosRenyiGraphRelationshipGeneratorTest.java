package com.graphaware.module.algo.generator.relationship;

import com.graphaware.module.algo.generator.config.ErdosRenyiConfig;
import junit.framework.TestCase;


public class ErdosRenyiGraphRelationshipGeneratorTest extends TestCase {

    public void testDoGenerateEdges() {
        ErdosRenyiConfig config = new ErdosRenyiConfig(1_000_000, 5_000_000);
        ErdosRenyiRelationshipGenerator er = new ErdosRenyiRelationshipGenerator(config);
        System.out.println("Number of edges generated: " + er.generateEdges().size());
    }


//    public void testBijection() {
//        ErdosRenyiConfig config = new ErdosRenyiConfig(1000, 5000);
//        ErdosRenyiRelationshipGenerator er = new ErdosRenyiRelationshipGenerator(config);
//
//        for (int i = 0; i < 10; ++i)
//            System.out.println(er.indexToEdgeBijection(i, 5).toString()); // Change this guy to logger
//
//    }
}