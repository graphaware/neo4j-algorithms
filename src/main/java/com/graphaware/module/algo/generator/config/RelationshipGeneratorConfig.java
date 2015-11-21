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
 * Configuration for a {@link com.graphaware.module.algo.generator.relationship.RelationshipGenerator}.
 */
public interface RelationshipGeneratorConfig {

    /**
     * Get the number of nodes that need to be created before the relationships can be generated and created.
     *
     * @return number of nodes for this configuration.
     */
    int getNumberOfNodes();

    /**
     * Returns true iff the config is valid.
     *
     * @return true if the config is valid.
     */
    boolean isValid();
}
