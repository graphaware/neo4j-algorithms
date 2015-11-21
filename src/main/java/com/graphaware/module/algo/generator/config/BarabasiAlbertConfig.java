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

/**
 * {@link RelationshipGeneratorConfig} for {@link com.graphaware.module.algo.generator.relationship.BarabasiAlbertRelationshipGenerator}.
 * <p/>
 * Permitted values: 0 < edgesPerNode < numberOfNodes
 * Recommended values: Interested in phenomenological model? Use low edgesPerNode value (2 ~ 3)
 * Real nets can have more than that. Usually choose less than half of a "mean" degree.
 * Precision is not crucial here.
 */
public class BarabasiAlbertConfig extends NumberOfNodesBasedConfig {

    /**
     * Number of edges added to the graph when
     * a new node is connected. The node has this
     * number of edges at that instant.
     */
    private final int edgesPerNewNode;

    /**
     * Construct a new config.
     *
     * @param numberOfNodes   number of nodes in the network.
     * @param edgesPerNewNode number of edges per newly added node.
     */
    public BarabasiAlbertConfig(int numberOfNodes, int edgesPerNewNode) {
        super(numberOfNodes);
        this.edgesPerNewNode = edgesPerNewNode;
    }

    /**
     * Get the number of edges per newly added node.
     *
     * @return number of edges.
     */
    public int getEdgesPerNewNode() {
        return edgesPerNewNode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid() {
        return super.isValid() && !(edgesPerNewNode < 1 || edgesPerNewNode + 1 > getNumberOfNodes());
    }
}
