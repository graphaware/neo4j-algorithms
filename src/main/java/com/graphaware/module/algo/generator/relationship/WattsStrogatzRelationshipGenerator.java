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

import com.graphaware.common.util.SameTypePair;
import com.graphaware.common.util.UnorderedPair;
import com.graphaware.module.algo.generator.config.WattsStrogatzConfig;

import java.util.*;

/**
 * Watts-Strogatz model implementation.
 */
public class WattsStrogatzRelationshipGenerator extends BaseRelationshipGenerator<WattsStrogatzConfig> {

    private Random random = new Random();

    public WattsStrogatzRelationshipGenerator(WattsStrogatzConfig configuration) {
        super(configuration);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * Generates a ring and performs rewiring on the network. This creates
     * a small-world network with high clustering coefficients (ie. there
     * are a lot of triangles present in the network, but the diameter
     * scales as ln(N)). Good choice for modelling simple social network
     * relationships (although hubs are not present).
     * <p/>
     *
     * @return ring - edge list as a list of unordered integer pairs
     */
    @Override
    protected List<SameTypePair<Integer>> doGenerateEdges() {

        int numberOfNodes = getConfiguration().getNumberOfNodes();
        int meanDegree = getConfiguration().getMeanDegree();
        double beta = getConfiguration().getBeta();

        HashSet<SameTypePair<Integer>> ring = new HashSet<>(numberOfNodes);

        // Create a ring network
        for (int i = 0; i < numberOfNodes; ++i) {
            for (int j = i + 1; j <= i + meanDegree / 2; ++j) {
                int friend = j % numberOfNodes;
                ring.add(new UnorderedPair<>(i, friend));
            }
        }

        /** Rewire edges with probability beta.

         The false rewirings change the desired probability distribution a little bit, but for
         large enough networks do not matter.

         At the moment, I am hacking my way around a bit here, due to constraints
         enforced by the class structure. There is a room for improvement as this
         implementation is not the most effective one.

         Also, the wiring stops when the algorithm rewires to a non-graphical set of
         edges. Unconnected components may appear in the graph due to rewiring.

         Works, but could be faster.
         */
        HashSet<SameTypePair<Integer>> newEdges = new HashSet<>();
        Iterator<SameTypePair<Integer>> iterator = ring.iterator();

        while (iterator.hasNext()) {
            SameTypePair<Integer> edge = iterator.next();
            if (random.nextDouble() <= beta) {
                int choice = random.nextDouble() > .5 ? edge.first() : edge.second(); // select first/second at random

                while (true) {
                    int trial = random.nextInt(numberOfNodes - 1);
                    int partner = trial < choice ? trial : trial + 1;  // avoid self loops

                    UnorderedPair<Integer> trialPair = new UnorderedPair<>(choice, partner);

                    // Allows for self-rewiring to avoid parasitic cases?
                    // check with original definition of the model
                    if (trialPair.equals(edge))
                        break;

                    if (!ring.contains(trialPair) && !newEdges.contains(trialPair)) {
                        iterator.remove();//ring.remove(edge);
                        newEdges.add(trialPair);
                        break;
                    }
                }
            }
        }

        // add newly rewired edges to the ring
        ring.addAll(newEdges);
        return new ArrayList<>(ring);
    }
}
