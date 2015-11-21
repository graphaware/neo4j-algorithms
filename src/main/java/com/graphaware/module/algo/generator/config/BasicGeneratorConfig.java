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

import com.graphaware.module.algo.generator.node.NodeCreator;
import com.graphaware.module.algo.generator.relationship.RelationshipCreator;
import com.graphaware.module.algo.generator.relationship.RelationshipGenerator;

/**
 * Basic implementation of {@link GeneratorConfiguration} where everything can be configured by constructor instantiation,
 * except for batch size, which defaults to 1000.
 */
public class BasicGeneratorConfig implements GeneratorConfiguration {

    private final RelationshipGenerator<?> relationshipGenerator;
    private final NodeCreator nodeCreator;
    private final RelationshipCreator relationshipCreator;

    /**
     * Create a new configuration.
     *
     * @param relationshipGenerator core component, generating the edges.
     * @param nodeCreator           component capable of creating nodes.
     * @param relationshipCreator   component capable of creating edges.
     */
    public BasicGeneratorConfig(RelationshipGenerator<?> relationshipGenerator, NodeCreator nodeCreator, RelationshipCreator relationshipCreator) {
        this.relationshipGenerator = relationshipGenerator;
        this.nodeCreator = nodeCreator;
        this.relationshipCreator = relationshipCreator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfNodes() {
        return relationshipGenerator.getNumberOfNodes();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeCreator getNodeCreator() {
        return nodeCreator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RelationshipCreator getRelationshipCreator() {
        return relationshipCreator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RelationshipGenerator<?> getRelationshipGenerator() {
        return relationshipGenerator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBatchSize() {
        return 1000;
    }
}
