package com.graphaware.module.algo.generator.relationship;

import com.graphaware.module.algo.generator.config.ErdosRenyiConfig;
import com.graphaware.module.algo.generator.relationship.ErdosRenyiRelationshipGenerator;
import junit.framework.TestCase;
import org.junit.Ignore;

@Ignore
public class ErdosRenyiGraphRelationshipGeneratorTest extends TestCase {

    public void testDoGenerateEdges() {
        // EXPERIMENTAL:
        ErdosRenyiConfig config = new ErdosRenyiConfig(10000, 50000); // Works fine up to ~ 1000000
        ErdosRenyiRelationshipGenerator er = new ErdosRenyiRelationshipGenerator(config);
        System.out.println(er.generateEdges().size());

//        ErdosRenyiConfig config;
//        config = new ErdosRenyiConfig(20, 190);
//        System.out.println(er.generateEdges(config));
    }
}