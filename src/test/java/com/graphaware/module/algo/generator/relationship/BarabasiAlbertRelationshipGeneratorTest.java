package com.graphaware.module.algo.generator.relationship;

import com.graphaware.module.algo.generator.config.BarabasiAlbertConfig;
import com.graphaware.module.algo.generator.relationship.BarabasiAlbertRelationshipGenerator;
import junit.framework.TestCase;

public class BarabasiAlbertRelationshipGeneratorTest extends TestCase {

    public void testDoGenerateEdges() throws Exception {
        System.out.println(new BarabasiAlbertRelationshipGenerator(new BarabasiAlbertConfig(1000000, 2)).generateEdges().size());

    }
}