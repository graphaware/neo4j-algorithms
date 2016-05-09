/*
 * Copyright (c) 2013-2016 GraphAware
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.graphaware.module.algo.generator;

import com.graphaware.module.algo.generator.config.BasicGeneratorConfig;
import com.graphaware.module.algo.generator.config.DistributionBasedConfig;
import com.graphaware.module.algo.generator.config.GeneratorConfiguration;
import com.graphaware.module.algo.generator.config.InvalidConfigException;
import com.graphaware.module.algo.generator.node.NodeCreator;
import com.graphaware.module.algo.generator.node.SocialNetworkNodeCreator;
import com.graphaware.module.algo.generator.relationship.RelationshipCreator;
import com.graphaware.module.algo.generator.relationship.SimpleGraphRelationshipGenerator;
import com.graphaware.module.algo.generator.relationship.SocialNetworkRelationshipCreator;
import com.graphaware.test.integration.DatabaseIntegrationTest;
import com.graphaware.test.integration.EmbeddedDatabaseIntegrationTest;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import java.util.Arrays;

import static com.graphaware.common.util.IterableUtils.count;
import static org.junit.Assert.assertEquals;

/**
 * Smoke test for {@link com.graphaware.module.algo.generator.Neo4jGraphGenerator}.
 */
public class Neo4jGraphGeneratorTest extends EmbeddedDatabaseIntegrationTest {

    @Test
    public void validDistributionShouldGenerateGraph() {
        NodeCreator nodeCreator = SocialNetworkNodeCreator.getInstance();
        RelationshipCreator relationshipCreator = SocialNetworkRelationshipCreator.getInstance();
        DistributionBasedConfig distribution = new DistributionBasedConfig(Arrays.asList(2, 2, 2, 2));
        SimpleGraphRelationshipGenerator relationshipGenerator = new SimpleGraphRelationshipGenerator(distribution);

        GeneratorConfiguration config = new BasicGeneratorConfig(relationshipGenerator, nodeCreator, relationshipCreator);

        new Neo4jGraphGenerator(getDatabase()).generateGraph(config);

        try (Transaction tx = getDatabase().beginTx()) {
            assertEquals(4, count(getDatabase().getAllNodes()));
            assertEquals(4, count(getDatabase().getAllRelationships()));

            for (Node node : getDatabase().getAllNodes()) {
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

        GeneratorConfiguration config = new BasicGeneratorConfig(relationshipGenerator, nodeCreator, relationshipCreator);

        new Neo4jGraphGenerator(getDatabase()).generateGraph(config);
    }
}
