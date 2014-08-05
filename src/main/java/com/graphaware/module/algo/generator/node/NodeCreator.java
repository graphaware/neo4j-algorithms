package com.graphaware.module.algo.generator.node;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.unsafe.batchinsert.BatchInserter;

/**
 * A component creating {@link org.neo4j.graphdb.Node}s with labels and properties.
 */
public interface NodeCreator {

    /**
     * Create a node with labels and properties.
     *
     * @param database to create the node in.
     * @return created node.
     */
    Node createNode(GraphDatabaseService database);

    /**
     * Create a node with labels and properties.
     *
     * @param batchInserter to create the node in.
     * @return created node ID.
     */
    long createNode(BatchInserter batchInserter);
}
