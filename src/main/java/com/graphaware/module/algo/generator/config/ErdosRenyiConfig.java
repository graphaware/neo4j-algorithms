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

package com.graphaware.module.algo.generator.config;

import java.math.BigInteger;

/**
 * {@link RelationshipGeneratorConfig} for {@link com.graphaware.module.algo.generator.relationship.ErdosRenyiRelationshipGenerator}.
 *
 * numberOfNodes: number of nodes in the graph. 1 < numberOfNodes
 * numberOfEdges: number of edges present in the generated graph. Range: 0 < numberOfEdges < (numberOfNodes*(numberOfNodes - 1)/2).
 *                Highly recommended not to exceed ~ 4 000 000 edges.
 *
 */
public class ErdosRenyiConfig extends NumberOfNodesBasedConfig {

    private final int numberOfEdges;

    /**
     * Constructs a new config.
     *
     * @param numberOfNodes number of nodes present in the network.
     * @param numberOfEdges number of edges present in the network.
     */
    public ErdosRenyiConfig(int numberOfNodes, int numberOfEdges) {
        super(numberOfNodes);
        this.numberOfEdges = numberOfEdges;
    }

    /**
     * Get the number of edges present in the network.
     *
     * @return number of edges.
     */
    public int getNumberOfEdges() {
        return numberOfEdges;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid() {
        BigInteger maxNumberOfEdges = BigInteger.valueOf(getNumberOfNodes());
        maxNumberOfEdges = maxNumberOfEdges.multiply(maxNumberOfEdges.subtract(BigInteger.ONE));
        maxNumberOfEdges = maxNumberOfEdges.divide(BigInteger.valueOf(2));

        return super.isValid() && numberOfEdges > 0 && BigInteger.valueOf(numberOfEdges).compareTo(maxNumberOfEdges) < 1;
    }
}
