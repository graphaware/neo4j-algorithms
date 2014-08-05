/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.graphaware.module.algo.generator;

import com.graphaware.module.algo.generator.config.BasicGeneratorConfiguration;
import com.graphaware.module.algo.generator.config.DistributionBasedConfig;
import com.graphaware.module.algo.generator.config.GeneratorConfiguration;
import com.graphaware.module.algo.generator.config.InvalidConfigException;
import com.graphaware.module.algo.generator.node.NodeCreator;
import com.graphaware.module.algo.generator.node.SocialNetworkNodeCreator;
import com.graphaware.module.algo.generator.relationship.RelationshipCreator;
import com.graphaware.module.algo.generator.relationship.SimpleGraphRelationshipGenerator;
import com.graphaware.module.algo.generator.relationship.SocialNetworkRelationshipCreator;
import com.graphaware.test.integration.DatabaseIntegrationTest;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import java.util.Arrays;

import static com.graphaware.common.util.IterableUtils.count;
import static org.junit.Assert.assertEquals;
import static org.neo4j.tooling.GlobalGraphOperations.at;

/**
 * Smoke test for {@link com.graphaware.module.algo.generator.Neo4jGraphGenerator}.
 */
public class Neo4jGraphGeneratorTest extends DatabaseIntegrationTest {

    @Test
    public void validDistributionShouldGenerateGraph() {
        NodeCreator nodeCreator = SocialNetworkNodeCreator.getInstance();
        RelationshipCreator relationshipCreator = SocialNetworkRelationshipCreator.getInstance();
        DistributionBasedConfig distribution = new DistributionBasedConfig(Arrays.asList(2, 2, 2, 2));
        SimpleGraphRelationshipGenerator relationshipGenerator = new SimpleGraphRelationshipGenerator(distribution);

        GeneratorConfiguration config = new BasicGeneratorConfiguration(relationshipGenerator, nodeCreator, relationshipCreator);

        new Neo4jGraphGenerator(getDatabase()).generateGraph(config);

        try (Transaction tx = getDatabase().beginTx()) {
            assertEquals(4, count(at(getDatabase()).getAllNodes()));
            assertEquals(4, count(at(getDatabase()).getAllRelationships()));

            for (Node node : at(getDatabase()).getAllNodes()) {
                assertEquals(2, node.getDegree());
            }

            tx.success();
        }
    }

    @Test(expected = InvalidConfigException.class)
    public void invalidDistributionShouldThrowException() {
        NodeCreator nodeCreator = SocialNetworkNodeCreator.getInstance();
        RelationshipCreator relationshipCreator = SocialNetworkRelationshipCreator.getInstance();
        DistributionBasedConfig distribution = new DistributionBasedConfig(Arrays.asList(3, 2, 2, 2));
        SimpleGraphRelationshipGenerator relationshipGenerator = new SimpleGraphRelationshipGenerator(distribution);

        GeneratorConfiguration config = new BasicGeneratorConfiguration(relationshipGenerator, nodeCreator, relationshipCreator);

        new Neo4jGraphGenerator(getDatabase()).generateGraph(config);
    }
}
