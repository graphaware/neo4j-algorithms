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
import com.graphaware.module.algo.generator.config.InvalidConfigException;
import com.graphaware.module.algo.generator.config.RelationshipGeneratorConfig;

import java.util.List;

/**
 * Abstract base-class for {@link RelationshipGenerator} implementations.
 *
 * @param <T> type of accepted configuration.
 */
public abstract class BaseRelationshipGenerator<T extends RelationshipGeneratorConfig> implements RelationshipGenerator<T> {

    private final T configuration;

    /**
     * Construct a new relationship generator.
     *
     * @param configuration to base the generation on
     */
    protected BaseRelationshipGenerator(T configuration) {
        this.configuration = configuration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfNodes() {
        return configuration.getNumberOfNodes();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SameTypePair<Integer>> generateEdges() throws InvalidConfigException {
        if (!configuration.isValid()) {
            throw new InvalidConfigException("The supplied config is not valid");
        }

        return doGenerateEdges();
    }

    /**
     * Perform the actual edge generation.
     *
     * @return generated edges as pair of node IDs that should be connected.
     */
    protected abstract List<SameTypePair<Integer>> doGenerateEdges();

    /**
     * Get the configuration of this generator.
     *
     * @return configuration.
     */
    protected T getConfiguration() {
        return configuration;
    }
}
