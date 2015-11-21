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
 * {@link RelationshipGeneratorConfig} that is based on an explicitly defined number of nodes in the network.
 */
public class NumberOfNodesBasedConfig implements RelationshipGeneratorConfig {

    private final int numberOfNodes;

    /**
     * Construct a new config.
     *
     * @param numberOfNodes number of nodes present in the network.
     */
    public NumberOfNodesBasedConfig(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid() {
        return numberOfNodes >= 2;
    }
}
