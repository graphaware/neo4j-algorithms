package com.graphaware.module.algo.generator;

import com.graphaware.common.util.SameTypePair;
import com.graphaware.module.algo.generator.config.GeneratorConfiguration;
import com.graphaware.module.algo.generator.node.NodeCreator;
import com.graphaware.module.algo.generator.relationship.RelationshipCreator;
import com.graphaware.module.algo.generator.relationship.RelationshipGenerator;
import com.graphaware.tx.executor.NullItem;
import com.graphaware.tx.executor.batch.BatchTransactionExecutor;
import com.graphaware.tx.executor.batch.IterableInputBatchTransactionExecutor;
import com.graphaware.tx.executor.batch.NoInputBatchTransactionExecutor;
import com.graphaware.tx.executor.batch.UnitOfWork;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.unsafe.batchinsert.BatchInserter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * {@link com.graphaware.module.algo.generator.GraphGenerator} for Neo4j using {@link BatchInserter}.
 */
public class BatchGraphGenerator implements GraphGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(BatchGraphGenerator.class);

    private final BatchInserter batchInserter;

    public BatchGraphGenerator(BatchInserter batchInserter) {
        this.batchInserter = batchInserter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void generateGraph(GeneratorConfiguration configuration) {
        generateNodes(configuration);
        generateRelationships(configuration);

        batchInserter.shutdown();
        LOG.info("Inserter shut down");
    }

    private void generateNodes(final GeneratorConfiguration config) {
        int numberOfNodes = config.getNumberOfNodes();
        NodeCreator nodeCreator = config.getNodeCreator();

        LOG.info("Creating " + numberOfNodes + " nodes");

        for (int i = 0; i < numberOfNodes; i++) {
            nodeCreator.createNode(batchInserter);
        }

        LOG.info("Created " + numberOfNodes + " nodes");
    }

    private void generateRelationships(final GeneratorConfiguration config) {
        LOG.info("Generating relationships");

        RelationshipGenerator<?> relationshipGenerator = config.getRelationshipGenerator();
        List<SameTypePair<Integer>> relationships = relationshipGenerator.generateEdges();

        LOG.info("Generated relationships, creating them");

        RelationshipCreator relationshipCreator = config.getRelationshipCreator();
        for (SameTypePair<Integer> relationship : relationships) {
            relationshipCreator.createRelationship(relationship.first(), relationship.second(), batchInserter);
        }

        LOG.info("Created relationships, shutting down inserter");
    }
}
