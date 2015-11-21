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

import com.graphaware.module.algo.generator.config.BarabasiAlbertConfig;
import com.graphaware.module.algo.generator.config.BasicGeneratorConfig;
import com.graphaware.module.algo.generator.config.GeneratorConfiguration;
import com.graphaware.module.algo.generator.node.SocialNetworkNodeCreator;
import com.graphaware.module.algo.generator.relationship.BarabasiAlbertRelationshipGenerator;
import com.graphaware.module.algo.generator.relationship.SocialNetworkRelationshipCreator;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.test.TestGraphDatabaseFactory;
import org.neo4j.tooling.GlobalGraphOperations;
import org.neo4j.unsafe.batchinsert.BatchInserter;
import org.neo4j.unsafe.batchinsert.BatchInserters;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.graphaware.common.util.IterableUtils.count;
import static org.junit.Assert.assertEquals;
import static org.neo4j.tooling.GlobalGraphOperations.at;

/**
 * Integration test for {@link Neo4jGraphGenerator} and {@link BatchGraphGenerator} with
 * {@link com.graphaware.module.algo.generator.relationship.BarabasiAlbertRelationshipGenerator}.
 */
public class BarabasiAlbertGeneratorTest {

    @Test
    public void shouldGenerateCorrectNumberOfNodesAndRelationships() throws Exception {
        assertUsingDatabase(100, 2);
        assertUsingDatabase(100, 3);
        assertUsingDatabase(100, 4);
        assertUsingDatabase(100, 5);
        assertUsingDatabase(10, 7);
        assertUsingDatabase(1000, 2);

        assertUsingBatchInserter(100, 2);
    }

    @Test
    public void shouldGeneratePowerLawDistribution() {
        GraphDatabaseService database = new TestGraphDatabaseFactory().newImpermanentDatabase();

        new Neo4jGraphGenerator(database).generateGraph(getGeneratorConfiguration(100, 2));

        List<Integer> degrees = new LinkedList<>();

        try (Transaction tx = database.beginTx()) {
            for (Node node : GlobalGraphOperations.at(database).getAllNodes()) {
                degrees.add(node.getDegree());
            }
            tx.success();
        }

        Collections.sort(degrees, Collections.reverseOrder());

        //todo make this an automated test
        System.out.println(ArrayUtils.toString(degrees.toArray(new Integer[degrees.size()])));

        database.shutdown();
    }

    @Test(timeout = 10 * 1000)
    @Ignore
    public void shouldGenerateRelationshipsForLargeGraphInAReasonableAmountOfTime() {
        new BarabasiAlbertRelationshipGenerator(new BarabasiAlbertConfig(1_000_000, 3)).generateEdges();
    }

    @Test(timeout = 60 * 1000)
    @Ignore
    public void shouldGenerateLargeGraphInAReasonableAmountOfTime() throws IOException {
        TemporaryFolder folder = new TemporaryFolder();
        folder.create();

        BatchInserter batchInserter = BatchInserters.inserter(folder.getRoot().getAbsolutePath());

        new BatchGraphGenerator(batchInserter).generateGraph(getGeneratorConfiguration(1_000_000, 3));

        folder.delete();
    }

    private void assertUsingDatabase(int numberOfNodes, int edgesPerNewNode) {
        GraphDatabaseService database = new TestGraphDatabaseFactory().newImpermanentDatabase();

        new Neo4jGraphGenerator(database).generateGraph(getGeneratorConfiguration(numberOfNodes, edgesPerNewNode));

        assertCorrectNumberOfNodesAndRelationships(database, numberOfNodes, edgesPerNewNode);

        database.shutdown();
    }

    private void assertUsingBatchInserter(int numberOfNodes, int edgesPerNewNode) throws IOException {
        TemporaryFolder folder = new TemporaryFolder();
        folder.create();

        BatchInserter batchInserter = BatchInserters.inserter(folder.getRoot().getAbsolutePath());

        new BatchGraphGenerator(batchInserter).generateGraph(getGeneratorConfiguration(numberOfNodes, edgesPerNewNode));

        GraphDatabaseService database = new GraphDatabaseFactory().newEmbeddedDatabase(folder.getRoot().getAbsolutePath());

        assertCorrectNumberOfNodesAndRelationships(database, numberOfNodes, edgesPerNewNode);

        database.shutdown();

        folder.delete();
    }

    private void assertCorrectNumberOfNodesAndRelationships(GraphDatabaseService database, int numberOfNodes, int edgesPerNewNode) {
        try (Transaction tx = database.beginTx()) {
            assertEquals(numberOfNodes, count(at(database).getAllNodes()));
            assertEquals(numberOfNodes * edgesPerNewNode - (edgesPerNewNode * (edgesPerNewNode + 1) / 2), count(at(database).getAllRelationships()));

            tx.success();
        }
    }

    private GeneratorConfiguration getGeneratorConfiguration(int numberOfNodes, int edgesPerNewNode) {
        return new BasicGeneratorConfig(
                new BarabasiAlbertRelationshipGenerator(new BarabasiAlbertConfig(numberOfNodes, edgesPerNewNode)),
                SocialNetworkNodeCreator.getInstance(),
                SocialNetworkRelationshipCreator.getInstance()
        );
    }

}
