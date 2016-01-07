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

import com.graphaware.common.util.SameTypePair;
import com.graphaware.module.algo.generator.config.GeneratorConfiguration;
import com.graphaware.module.algo.generator.relationship.RelationshipGenerator;
import com.graphaware.tx.executor.NullItem;
import com.graphaware.tx.executor.batch.BatchTransactionExecutor;
import com.graphaware.tx.executor.batch.IterableInputBatchTransactionExecutor;
import com.graphaware.tx.executor.batch.NoInputBatchTransactionExecutor;
import com.graphaware.tx.executor.batch.UnitOfWork;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link GraphGenerator} for Neo4j.
 */
public class Neo4jGraphGenerator extends BaseGraphGenerator {

    private final GraphDatabaseService database;

    public Neo4jGraphGenerator(GraphDatabaseService database) {
        this.database = database;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<Long> generateNodes(final GeneratorConfiguration config) {
        final List<Long> nodes = new ArrayList<>();

        int numberOfNodes = config.getNumberOfNodes();

        BatchTransactionExecutor executor = new NoInputBatchTransactionExecutor(database, config.getBatchSize(), numberOfNodes, new UnitOfWork<NullItem>() {
            @Override
            public void execute(GraphDatabaseService database, NullItem input, int batchNumber, int stepNumber) {
                nodes.add(config.getNodeCreator().createNode(database).getId());
            }
        });

        executor.execute();

        return nodes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void generateRelationships(final GeneratorConfiguration config, final List<Long> nodes) {
        RelationshipGenerator<?> relationshipGenerator = config.getRelationshipGenerator();
        List<SameTypePair<Integer>> relationships = relationshipGenerator.generateEdges();

        BatchTransactionExecutor executor = new IterableInputBatchTransactionExecutor<>(database, config.getBatchSize(), relationships, new UnitOfWork<SameTypePair<Integer>>() {
            @Override
            public void execute(GraphDatabaseService database, SameTypePair<Integer> input, int batchNumber, int stepNumber) {
                Node first = database.getNodeById(nodes.get(input.first()));
                Node second = database.getNodeById(nodes.get(input.second()));
                config.getRelationshipCreator().createRelationship(first, second);
            }
        });

        executor.execute();
    }
}
