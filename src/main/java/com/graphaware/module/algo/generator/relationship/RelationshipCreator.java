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
