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
