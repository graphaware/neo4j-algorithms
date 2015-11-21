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

package com.graphaware.module.algo.generator;

import com.graphaware.module.algo.generator.config.GeneratorConfiguration;

import java.util.List;

/**
 * Base class for {@link GraphGenerator} implementations.
 */
public abstract class BaseGraphGenerator implements GraphGenerator {

    /**
     * {@inheritDoc}
     */
    @Override
    public void generateGraph(GeneratorConfiguration configuration) {
        generateRelationships(configuration, generateNodes(configuration));
    }

    /**
     * Generate (i.e. create and persist) nodes.
     *
     * @param configuration generator config.
     * @return list of node IDs of the generated nodes.
     */
    protected abstract List<Long> generateNodes(GeneratorConfiguration configuration);

    /**
     * Generate (i.e. create and persist) relationships.
     *
     * @param config generator config.
     * @param nodes  list of node IDs of the generated nodes.
     */
    protected abstract void generateRelationships(final GeneratorConfiguration config, List<Long> nodes);
}
