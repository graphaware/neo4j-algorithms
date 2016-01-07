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

package com.graphaware.module.algo.generator.relationship;

import com.graphaware.common.util.SameTypePair;
import com.graphaware.common.util.UnorderedPair;
import com.graphaware.module.algo.generator.config.BarabasiAlbertConfig;
import com.graphaware.module.algo.generator.config.NumberOfNodesBasedConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * {@link RelationshipGenerator} implemented according to the Barabasi-Albert preferential attachment model, which is
 * appropriate for networks reflecting cumulative advantage (rich get richer).
 * <p/>
 * Each newly added node has a probability weighted by the node degree to be attached. Since BA references
 * (Newmann, Barabasi-Albert) do not define strict conditions on initial state of the model, completely connected network
 * is used to start up the algorithm.
 */
public class BarabasiAlbertRelationshipGenerator extends BaseRelationshipGenerator<BarabasiAlbertConfig> {

    private final Random random = new Random();

    /**
     * Create a new generator.
     *
     * @param configuration of the generator.
     */
    public BarabasiAlbertRelationshipGenerator(BarabasiAlbertConfig configuration) {
        super(configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<SameTypePair<Integer>> doGenerateEdges() {
        final int edgesPerNewNode = getConfiguration().getEdgesPerNewNode();
        final int numberOfNodes = getConfiguration().getNumberOfNodes();

        // Create a completely connected network
        final List<SameTypePair<Integer>> edges
                = new CompleteGraphRelationshipGenerator(new NumberOfNodesBasedConfig(edgesPerNewNode + 1)).doGenerateEdges();

        // Preferentially attach other nodes
        final Set<Integer> omit = new HashSet<>(edgesPerNewNode);
        for (int source = edgesPerNewNode + 1; source < numberOfNodes; source++) {
            omit.clear();

            for (int edge = 0; edge < edgesPerNewNode; edge++) {
                while (true) {
                    SameTypePair<Integer> randomEdge = edges.get(random.nextInt(edges.size()));
                    int target = random.nextBoolean() ? randomEdge.first() : randomEdge.second();

                    if (omit.contains(target)) {
                        continue;
                    }

                    omit.add(target); // to avoid multi-edges

                    edges.add(new UnorderedPair<>(target, source));

                    break;
                }
            }
        }

        return edges;
    }
}
