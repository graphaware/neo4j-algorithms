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

package com.graphaware.module.algo.generator.config;

import com.graphaware.module.algo.generator.node.NodeCreator;
import com.graphaware.module.algo.generator.relationship.RelationshipCreator;
import com.graphaware.module.algo.generator.relationship.RelationshipGenerator;

/**
 * A configuration of a {@link com.graphaware.module.algo.generator.GraphGenerator}.
 */
public interface GeneratorConfiguration {

    /**
     * Get the total number of nodes that will be generated.
     *
     * @return number of nodes.
     */
    int getNumberOfNodes();

    /**
     * Get the component generating relationships.
     *
     * @return relationship generator.
     */
    RelationshipGenerator<?> getRelationshipGenerator();

    /**
     * Get the component creating (populating) nodes.
     *
     * @return node creator.
     */
    NodeCreator getNodeCreator();

    /**
     * Get the component creating (populating) relationships.
     *
     * @return relationship creator.
     */
    RelationshipCreator getRelationshipCreator();

    /**
     * Get the batch size for graph creation in the database.
     *
     * @return batch size.
     */
    int getBatchSize();
}
