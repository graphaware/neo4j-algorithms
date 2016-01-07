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
import com.graphaware.module.algo.generator.config.NumberOfNodesBasedConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RelationshipGenerator} that generates a complete (undirected) graph.
 * Used for the core graph in {@link BarabasiAlbertRelationshipGenerator}.
 */
public class CompleteGraphRelationshipGenerator extends BaseRelationshipGenerator<NumberOfNodesBasedConfig> {

    /**
     * Create a new generator.
     *
     * @param configuration of the generator.
     */
    public CompleteGraphRelationshipGenerator(NumberOfNodesBasedConfig configuration) {
        super(configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<SameTypePair<Integer>> doGenerateEdges() {
        List<SameTypePair<Integer>> graph = new ArrayList<>();

        // Create a completely connected undirected network
        for (int i = 0; i < getConfiguration().getNumberOfNodes(); i++) {
            for (int j = i + 1; j < getConfiguration().getNumberOfNodes(); j++) {
                graph.add(new UnorderedPair<>(i, j));
            }
        }

        return graph;
    }
}
