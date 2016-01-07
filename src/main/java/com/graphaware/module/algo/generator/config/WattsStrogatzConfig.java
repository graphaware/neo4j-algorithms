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

/**
 * {@link RelationshipGeneratorConfig} for {@link com.graphaware.module.algo.generator.relationship.WattsStrogatzRelationshipGenerator}.
 * <p/>
 *
 * meanDegree: degree which a node in the graph has on average (best to choose something < 10)
 * numberOfNodes: number of nodes present in the graph
 * beta: probability an edge will be rewired. Rewiring means that an edge is removed and replaced by another edge
 *       created from a pair chosen at random from a set of unconnected node pairs. Controls the clustering of the graph.
 *       beta = 1.0: Erdos-Renyi model
 *       beta = 0.0: Ring graph
 *       0.0 < beta < 1.0: Fast convergence towards a random graph, but still sufficiently clustered.
 *
 * Recommended value of beta to exploit typical (randomness & clustering) properties of the W-S model: 0.4 < beta < 0.6
 */
public class WattsStrogatzConfig extends NumberOfNodesBasedConfig {

    private final int meanDegree;
    private final double beta;

    /**
     * Construct a new config.
     *
     * @param numberOfNodes number of nodes in the network.
     * @param meanDegree    mean degree of the regular ring network constructed as an initial step for Watts-Strogatz.
     * @param beta          probability of edge rewiring at a given step.
     */
    public WattsStrogatzConfig(int numberOfNodes, int meanDegree, double beta) {
        super(numberOfNodes);
        this.meanDegree = meanDegree;
        this.beta = beta;
    }

    /**
     * Get the mean degree of nodes in the generated network.
     *
     * @return mean degree.
     */
    public int getMeanDegree() {
        return meanDegree;
    }

    /**
     * Get the probability of edge rewiring at each step.
     *
     * @return p.
     */
    public double getBeta() {
        return beta;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid() {
        return !(meanDegree % 2 != 0 ||
                meanDegree < 3 ||
                meanDegree > getNumberOfNodes() - 1) &&
                (0 <= beta && beta <= 1);
    }
}
