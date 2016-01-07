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

package com.graphaware.module.algo.generator.api;

import com.graphaware.test.integration.GraphAwareApiTest;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;
import org.neo4j.graphdb.Transaction;
import org.neo4j.helpers.collection.Iterables;
import org.neo4j.tooling.GlobalGraphOperations;

import static org.junit.Assert.assertEquals;

/**
 * Integration test for {@link GeneratorApi}.
 */
public class GeneratorApiTest extends GraphAwareApiTest {

    @Test
    public void shouldGenerateErdosRenyi() {
        httpClient.post(getUrl() + "erdos-renyi/1000/10000", HttpStatus.CREATED_201);

        try (Transaction tx = getDatabase().beginTx()) {
            assertEquals(1000, Iterables.count(GlobalGraphOperations.at(getDatabase()).getAllNodes()));
            assertEquals(10000, Iterables.count(GlobalGraphOperations.at(getDatabase()).getAllRelationships()));

            tx.success();
        }
    }

    @Test
    public void shouldGenerateBarabasiAlbert() {
        httpClient.post(getUrl() + "barabasi-albert/1000/3", HttpStatus.CREATED_201);

        try (Transaction tx = getDatabase().beginTx()) {
            assertEquals(1000, Iterables.count(GlobalGraphOperations.at(getDatabase()).getAllNodes()));
            assertEquals(2994, Iterables.count(GlobalGraphOperations.at(getDatabase()).getAllRelationships()));
            tx.success();
        }
    }

    @Test
    public void shouldGenerateWattsStrogatz() {
        httpClient.post(getUrl() + "watts-strogatz/1000/10/0.5", HttpStatus.CREATED_201);

        try (Transaction tx = getDatabase().beginTx()) {
            assertEquals(1000, Iterables.count(GlobalGraphOperations.at(getDatabase()).getAllNodes()));
            assertEquals(5000, Iterables.count(GlobalGraphOperations.at(getDatabase()).getAllRelationships()));
            tx.success();
        }
    }


    private String getUrl() {
        return baseUrl() + "/algorithm/generator/social/";
    }
}
