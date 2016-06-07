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

package com.graphaware.module.algo.generator;

import com.graphaware.common.log.LoggerFactory;
import com.graphaware.common.util.SameTypePair;
import com.graphaware.module.algo.generator.config.GeneratorConfiguration;
import com.graphaware.module.algo.generator.node.NodeCreator;
import com.graphaware.module.algo.generator.relationship.RelationshipCreator;
import org.neo4j.logging.Log;
import org.neo4j.unsafe.batchinsert.BatchInserter;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link com.graphaware.module.algo.generator.GraphGenerator} for Neo4j using {@link BatchInserter}.
 */
public class BatchGraphGenerator extends BaseGraphGenerator {

    private static final Log LOG = LoggerFactory.getLogger(BatchGraphGenerator.class);

    private final BatchInserter batchInserter;

    public BatchGraphGenerator(BatchInserter batchInserter) {
        this.batchInserter = batchInserter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void generateGraph(GeneratorConfiguration configuration) {
        super.generateGraph(configuration);

        batchInserter.shutdown();
        LOG.info("Inserter shut down");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<Long> generateNodes(final GeneratorConfiguration config) {
        List<Long> nodes = new ArrayList<>();

        int numberOfNodes = config.getNumberOfNodes();
        NodeCreator nodeCreator = config.getNodeCreator();

        LOG.info("Creating " + numberOfNodes + " nodes");

        for (int i = 0; i < numberOfNodes; i++) {
            nodes.add(nodeCreator.createNode(batchInserter));
        }

        LOG.info("Created " + numberOfNodes + " nodes");

        return nodes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void generateRelationships(GeneratorConfiguration config, List<Long> nodes) {
        LOG.info("Generating relationships");

        List<SameTypePair<Integer>> relationships = config.getRelationshipGenerator().generateEdges();

        LOG.info("Generated relationships, creating them");

        RelationshipCreator relationshipCreator = config.getRelationshipCreator();
        for (SameTypePair<Integer> relationship : relationships) {
            relationshipCreator.createRelationship(nodes.get(relationship.first()), nodes.get(relationship.second()), batchInserter);
        }

        LOG.info("Created relationships, shutting down inserter");
    }
}
