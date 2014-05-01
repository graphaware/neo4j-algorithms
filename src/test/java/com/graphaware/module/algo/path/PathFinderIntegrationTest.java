/*
 * Copyright (c) 2013 GraphAware
 *
 * This file is part of GraphAware.
 *
 * GraphAware is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received a copy of
 * the GNU General Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package com.graphaware.module.algo.path;

import com.graphaware.test.integration.IntegrationTest;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;

import java.io.IOException;

import static com.graphaware.test.util.TestUtils.*;

/**
 *
 */
public class PathFinderIntegrationTest extends IntegrationTest {

    @Test
    public void graphAwareApisAreMountedWhenPresentOnClasspath() throws InterruptedException, IOException {
        post("http://localhost:7474/db/data/cypher",
                "{\"query\" : \"" +
                        "CREATE (one:L1:L2 { name:\\\"one\\\" }) " +
                        "CREATE (two:L2 { name:\\\"two\\\" }) " +
                        "CREATE (three:L1:L2 { name:\\\"three\\\" }) " +
                        "CREATE (four:L2 { name:\\\"four\\\" }) " +
                        "CREATE (five:L1 { name:\\\"five\\\" }) " +
                        "CREATE (six:L1 { name:\\\"six\\\" }) " +
                        "CREATE (seven:L1 { name:\\\"seven\\\" }) " +
                        "CREATE (one)-[:R1 {cost:5}]->(two)-[:R2 {cost:1}]->(three) " +
                        "CREATE (one)-[:R2 {cost:1}]->(four)-[:R1 {cost:2}]->(five)-[:R1 {cost:1}]->(three) " +
                        "CREATE (two)-[:R2 {cost:1}]->(four) " +
                        "CREATE (one)-[:R1 {cost:1}]->(six)-[:R1]->(seven)<-[:R1 {cost:1}]-(three)" +
                        "\"}",
                HttpStatus.OK_200);

        assertJsonEquals(post("http://localhost:7474/graphaware/algorithm/path/increasinglyLongerShortestPath",
                jsonAsString("minimalInput"), HttpStatus.OK_200),
                jsonAsString("minimalOutput"));
    }
}
