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

import com.graphaware.module.algo.generator.distribution.SimpleDegreeDistribution;

import java.util.List;

/**
 * {@link SimpleDegreeDistribution} serving as a {@link RelationshipGeneratorConfig}.
 *
 * The sum of all degrees should be an even integer. Moreover, not all distributions
 * correspond to a simple, undirected graph. Only graphs that satisfy Erdos-Gallai condition
 * or equivalently the Havel-Hakimi test are valid. The distribution is tested in
 * {@link SimpleDegreeDistribution} class.
 */
public class DistributionBasedConfig extends SimpleDegreeDistribution implements RelationshipGeneratorConfig {

    /**
     * Create a new config.
     *
     * @param degrees list of node degrees.
     */
    public DistributionBasedConfig(List<Integer> degrees) {
        super(degrees);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfNodes() {
        return size();
    }
}
