package com.graphaware.module.algo.generator.relationship;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.unsafe.batchinsert.BatchInserter;

/**
 * A component creating {@link org.neo4j.graphdb.Relationship}s with properties.
 */
public interface RelationshipCreator {

    /**
     * Create a relationship between two nodes with properties.
     *
     * @param first  first node.
     * @param second second node.
     * @return created relationship.
     */
    Relationship createRelationship(Node first, Node second);

    /**
     * Create a relationship between two nodes with properties.
     *
     * @param first         first node ID.
     * @param second        second node ID.
     * @param batchInserter to use when creating relationship.
     * @return created relationship ID.
     */
    long createRelationship(long first, long second, BatchInserter batchInserter);
}
