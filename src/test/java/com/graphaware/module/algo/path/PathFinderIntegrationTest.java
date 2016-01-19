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

package com.graphaware.module.algo.path;

import com.graphaware.test.integration.NeoServerIntegrationTest;
import org.eclipse.jetty.http.HttpStatus;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;

import static com.graphaware.test.util.TestUtils.jsonAsString;

/**
 *
 */
public class PathFinderIntegrationTest extends NeoServerIntegrationTest {

    @Test
    public void graphAwareApisAreMountedWhenPresentOnClasspath() throws InterruptedException, IOException, JSONException {
        httpClient.executeCypher(baseUrl(),
                "CREATE (one:L1:L2 {name:'one'}), " +
                        "(two:L2 {name:'two'}), " +
                        "(three:L1:L2 {name:'three'}), " +
                        "(four:L2 {name:'four'}), " +
                        "(five:L1 {name:'five'}), " +
                        "(six:L1 {name:'six'}), " +
                        "(seven:L1 {name:'seven'}), " +
                        "(one)-[:R1 {cost:5}]->(two)-[:R2 {cost:1}]->(three), " +
                        "(one)-[:R2 {cost:1}]->(four)-[:R1 {cost:2}]->(five)-[:R1 {cost:1}]->(three), " +
                        "(two)-[:R2 {cost:1}]->(four), " +
                        "(one)-[:R1 {cost:1}]->(six)-[:R1]->(seven)<-[:R1 {cost:1}]-(three)");

        JSONAssert.assertEquals(httpClient.post(baseUrl() + "/graphaware/algorithm/path/increasinglyLongerShortestPath",
                        jsonAsString("minimalInput"), HttpStatus.OK_200),
                jsonAsString("minimalOutput"), false);
    }
}
