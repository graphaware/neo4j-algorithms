package com.graphaware.module.algo.generator.relationship;

import com.graphaware.module.algo.generator.config.WattsStrogatzConfig;
import com.graphaware.module.algo.generator.relationship.WattsStrogatzRelationshipGenerator;
import junit.framework.TestCase;
import org.junit.Ignore;

@Ignore
public class WattsStrogatzRelationshipGeneratorTest extends TestCase {

    public void testDoGenerateEdges() throws Exception {
        WattsStrogatzRelationshipGenerator generator = new WattsStrogatzRelationshipGenerator(new WattsStrogatzConfig(100, 4, 0.5));

        for (int i = 0; i < 10; ++i)
            System.out.println(generator.generateEdges().toString());
            System.out.println("-------------------------------------");
    }
}