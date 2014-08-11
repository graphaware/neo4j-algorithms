package com.graphaware.module.algo.generator.relationship;

import com.graphaware.module.algo.generator.config.ErdosRenyiConfig;
import junit.framework.TestCase;
import org.junit.Ignore;
import org.junit.Test;


public class ErdosRenyiGraphRelationshipGeneratorTest extends TestCase {

    @Test(timeout = 5 * 60 * 1000)   //5 mins
    @Ignore
    public void testDoGenerateEdges() {
        ErdosRenyiConfig config = new ErdosRenyiConfig(1_000_000, 5_000_000);
        ErdosRenyiRelationshipGenerator er = new ErdosRenyiRelationshipGenerator(config);
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