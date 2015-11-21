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
import com.graphaware.module.algo.generator.config.ErdosRenyiConfig;
import com.graphaware.module.algo.generator.config.GeneratorConfiguration;
import com.graphaware.module.algo.generator.node.SocialNetworkNodeCreator;
import com.graphaware.module.algo.generator.relationship.ErdosRenyiRelationshipGenerator;
import com.graphaware.module.algo.generator.relationship.SocialNetworkRelationshipCreator;
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
 * Integration test for {@link com.graphaware.module.algo.generator.Neo4jGraphGenerator} and {@link com.graphaware.module.algo.generator.BatchGraphGenerator} with
 * {@link com.graphaware.module.algo.generator.relationship.ErdosRenyiRelationshipGenerator}.
 */
public class ErdosRenyiGeneratorTest {

    @Test
    public void shouldGenerateCorrectNumberOfNodesAndRelationships() throws Exception {
        assertUsingDatabase(100, 200);
        assertUsingDatabase(100, 300);
        assertUsingDatabase(100, 1000);
        assertUsingDatabase(100, 5);
        assertUsingDatabase(10, 11);
        assertUsingDatabase(10, 23);
        assertUsingDatabase(20, 190);

        assertUsingBatchInserter(100, 1000);
    }

    @Test(timeout = 60 * 1000)
    @Ignore
    public void shouldGenerateRelationshipsForLargeGraphInAReasonableAmountOfTime() {
        new ErdosRenyiRelationshipGenerator(new ErdosRenyiConfig(500_000, 10_000_000)).generateEdges();
    }

    @Test(timeout = 60 * 1000)
    @Ignore
    public void shouldGenerateRelationshipsForLargeGraphInAReasonableAmountOfTime2() {
        new ErdosRenyiRelationshipGenerator(new ErdosRenyiConfig(10000, 25_000_000)).generateEdges();
    }

    @Test(timeout = 5 * 60 * 1000)
    @Ignore
    public void shouldGenerateLargeGraphInAReasonableAmountOfTime() throws IOException {
        TemporaryFolder folder = new TemporaryFolder();
        folder.create();

        BatchInserter batchInserter = BatchInserters.inserter(folder.getRoot().getAbsolutePath());

        new BatchGraphGenerator(batchInserter).generateGraph(getGeneratorConfiguration(100_000, 5_000_000));

        folder.delete();
    }

    private void assertUsingDatabase(int numberOfNodes, int numberOfEdges) {
        GraphDatabaseService database = new TestGraphDatabaseFactory().newImpermanentDatabase();

        new Neo4jGraphGenerator(database).generateGraph(getGeneratorConfiguration(numberOfNodes, numberOfEdges));

        assertCorrectNumberOfNodesAndRelationships(database, numberOfNodes, numberOfEdges);

        database.shutdown();
    }

    private void assertUsingBatchInserter(int numberOfNodes, int numberOfEdges) throws IOException {
        TemporaryFolder folder = new TemporaryFolder();
        folder.create();

        BatchInserter batchInserter = BatchInserters.inserter(folder.getRoot().getAbsolutePath());

        new BatchGraphGenerator(batchInserter).generateGraph(getGeneratorConfiguration(numberOfNodes, numberOfEdges));

        GraphDatabaseService database = new GraphDatabaseFactory().newEmbeddedDatabase(folder.getRoot().getAbsolutePath());

        assertCorrectNumberOfNodesAndRelationships(database, numberOfNodes, numberOfEdges);

        database.shutdown();

        folder.delete();
    }

    private void assertCorrectNumberOfNodesAndRelationships(GraphDatabaseService database, int numberOfNodes, int numberOfEdges) {
        try (Transaction tx = database.beginTx()) {
            assertEquals(numberOfNodes, count(at(database).getAllNodes()));
            assertEquals(numberOfEdges, count(at(database).getAllRelationships()));

            tx.success();
        }
    }

    private GeneratorConfiguration getGeneratorConfiguration(int numberOfNodes, int numberOfEdges) {
        return new BasicGeneratorConfig(
                new ErdosRenyiRelationshipGenerator(new ErdosRenyiConfig(numberOfNodes, numberOfEdges)),
                SocialNetworkNodeCreator.getInstance(),
                SocialNetworkRelationshipCreator.getInstance()
        );
    }
}
