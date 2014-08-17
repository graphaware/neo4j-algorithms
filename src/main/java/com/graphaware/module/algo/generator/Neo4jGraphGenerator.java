package com.graphaware.module.algo.generator;

import com.graphaware.common.util.SameTypePair;
import com.graphaware.module.algo.generator.config.GeneratorConfiguration;
import com.graphaware.module.algo.generator.relationship.RelationshipGenerator;
import com.graphaware.tx.executor.NullItem;
import com.graphaware.tx.executor.batch.BatchTransactionExecutor;
import com.graphaware.tx.executor.batch.IterableInputBatchTransactionExecutor;
import com.graphaware.tx.executor.batch.NoInputBatchTransactionExecutor;
import com.graphaware.tx.executor.batch.UnitOfWork;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link GraphGenerator} for Neo4j.
 */
public class Neo4jGraphGenerator extends BaseGraphGenerator {

    private final GraphDatabaseService database;

    public Neo4jGraphGenerator(GraphDatabaseService database) {
        this.database = database;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<Long> generateNodes(final GeneratorConfiguration config) {
        final List<Long> nodes = new ArrayList<>();

        int numberOfNodes = config.getNumberOfNodes();

        BatchTransactionExecutor executor = new NoInputBatchTransactionExecutor(database, config.getBatchSize(), numberOfNodes, new UnitOfWork<NullItem>() {
            @Override
            public void execute(GraphDatabaseService database, NullItem input, int batchNumber, int stepNumber) {
                nodes.add(config.getNodeCreator().createNode(database).getId());
            }
        });

        executor.execute();

        return nodes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void generateRelationships(final GeneratorConfiguration config, final List<Long> nodes) {
        RelationshipGenerator<?> relationshipGenerator = config.getRelationshipGenerator();
        List<SameTypePair<Integer>> relationships = relationshipGenerator.generateEdges();

        BatchTransactionExecutor executor = new IterableInputBatchTransactionExecutor<>(database, config.getBatchSize(), relationships, new UnitOfWork<SameTypePair<Integer>>() {
            @Override
            public void execute(GraphDatabaseService database, SameTypePair<Integer> input, int batchNumber, int stepNumber) {
                Node first = database.getNodeById(nodes.get(input.first()));
                Node second = database.getNodeById(nodes.get(input.second()));
                config.getRelationshipCreator().createRelationship(first, second);
            }
        });

        executor.execute();
    }
}
