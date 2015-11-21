/*
 * Copyright (c) 2013-2015 GraphAware
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

package com.graphaware.module.algo.generator;

import com.graphaware.module.algo.generator.config.BasicGeneratorConfig;
import com.graphaware.module.algo.generator.config.GeneratorConfiguration;
import com.graphaware.module.algo.generator.config.WattsStrogatzConfig;
import com.graphaware.module.algo.generator.node.SocialNetworkNodeCreator;
import com.graphaware.module.algo.generator.relationship.SocialNetworkRelationshipCreator;
import com.graphaware.module.algo.generator.relationship.WattsStrogatzRelationshipGenerator;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.test.TestGraphDatabaseFactory;
import org.neo4j.unsafe.batchinsert.BatchInserter;
import org.neo4j.unsafe.batchinsert.BatchInserters;

import java.io.IOException;

import static com.graphaware.common.util.IterableUtils.count;
import static org.junit.Assert.assertEquals;
import static org.neo4j.tooling.GlobalGraphOperations.at;

/**
 * Integration test for {@link Neo4jGraphGenerator} and {@link BatchGraphGenerator} with
 * {@link com.graphaware.module.algo.generator.relationship.WattsStrogatzRelationshipGenerator}.
 */
public class WattsStrogatzGeneratorTest {

    @Test
    public void shouldGenerateCorrectNumberOfNodesAndRelationships() throws Exception {
        assertUsingDatabase(100, 4, 0.1);
        assertUsingDatabase(100, 6, 0.85);
        assertUsingDatabase(100, 8, 0.5);
        assertUsingDatabase(100, 10, 0.5);
        assertUsingDatabase(1000, 50, 0.5);

        assertUsingBatchInserter(100, 10, 0.2);
    }

    @Test(timeout = 2 * 60 * 1000)
    @Ignore
    public void shouldGenerateRelationshipsForLargeGraphInAReasonableAmountOfTime() {
        new WattsStrogatzRelationshipGenerator(new WattsStrogatzConfig(1_000_000, 10, 0.5)).generateEdges();
    }

    @Test(timeout = 5 * 60 * 1000)   //5 mins
    @Ignore
    public void shouldGenerateLargeGraphInAReasonableAmountOfTime() throws IOException {
        TemporaryFolder folder = new TemporaryFolder();
        folder.create();

        BatchInserter batchInserter = BatchInserters.inserter(folder.getRoot().getAbsolutePath());

        new BatchGraphGenerator(batchInserter).generateGraph(getGeneratorConfiguration(100_000, 50, 0.5));

        folder.delete();
    }

    private void assertUsingDatabase(int numberOfNodes, int meanDegree, double beta) {
        GraphDatabaseService database = new TestGraphDatabaseFactory().newImpermanentDatabase();

        new Neo4jGraphGenerator(database).generateGraph(getGeneratorConfiguration(numberOfNodes, meanDegree, beta));

        assertCorrectNumberOfNodesAndRelationships(database, numberOfNodes, meanDegree);

        database.shutdown();
    }

    private void assertUsingBatchInserter(int numberOfNodes, int meanDegree, double beta) throws IOException {
        TemporaryFolder folder = new TemporaryFolder();
        folder.create();

        BatchInserter batchInserter = BatchInserters.inserter(folder.getRoot().getAbsolutePath());

        new BatchGraphGenerator(batchInserter).generateGraph(getGeneratorConfiguration(numberOfNodes, meanDegree, beta));

        GraphDatabaseService database = new GraphDatabaseFactory().newEmbeddedDatabase(folder.getRoot().getAbsolutePath());

        assertCorrectNumberOfNodesAndRelationships(database, numberOfNodes, meanDegree);

        database.shutdown();

        folder.delete();
    }

    private void assertCorrectNumberOfNodesAndRelationships(GraphDatabaseService database, int numberOfNodes, int meanDegree) {
        try (Transaction tx = database.beginTx()) {
            assertEquals(numberOfNodes, count(at(database).getAllNodes()));
            assertEquals((meanDegree * numberOfNodes) / 2, count(at(database).getAllRelationships()));

            tx.success();
        }
    }

    private GeneratorConfiguration getGeneratorConfiguration(int numberOfNodes, int meanDegree, double beta) {
        return new BasicGeneratorConfig(
                new WattsStrogatzRelationshipGenerator(new WattsStrogatzConfig(numberOfNodes, meanDegree, beta)),
                SocialNetworkNodeCreator.getInstance(),
                SocialNetworkRelationshipCreator.getInstance()
        );
    }

}
