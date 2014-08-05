package com.graphaware.module.algo.generator;

import com.graphaware.module.algo.generator.config.BarabasiAlbertConfig;
import com.graphaware.module.algo.generator.config.BasicGeneratorConfig;
import com.graphaware.module.algo.generator.config.GeneratorConfiguration;
import com.graphaware.module.algo.generator.node.SocialNetworkNodeCreator;
import com.graphaware.module.algo.generator.relationship.BarabasiAlbertRelationshipGenerator;
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

    @Test(timeout = 10 * 1000)
    public void shouldGenerateRelationshipsForLargeGraphInAReasonableAmountOfTime() {
        new BarabasiAlbertRelationshipGenerator(new BarabasiAlbertConfig(1_000_000, 3)).generateEdges();
    }

    @Test(timeout = 5 * 60 * 1000)   //5 mins
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
