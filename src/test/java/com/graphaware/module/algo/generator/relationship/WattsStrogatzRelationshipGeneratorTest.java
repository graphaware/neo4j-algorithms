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

package com.graphaware.module.algo.generator.relationship;

import com.graphaware.module.algo.generator.config.WattsStrogatzConfig;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WattsStrogatzRelationshipGeneratorTest {

    @Test
    public void testDoGenerateEdgesValidity() throws Exception {
        int meanDegree = 4;
        int numberOfNodes = 10;
        double betaCoefficient = 0.5;

        WattsStrogatzRelationshipGenerator generator = new WattsStrogatzRelationshipGenerator(new WattsStrogatzConfig(numberOfNodes, meanDegree, betaCoefficient));

        assertEquals((int) (meanDegree * numberOfNodes * .5), generator.doGenerateEdges().size());
    }

    @Test
    public void testDoGenerateEdgesPerformance() throws Exception {
        int meanDegree = 4;
        int numberOfNodes = 2_000;
        double betaCoefficient = 0.5;

        WattsStrogatzRelationshipGenerator generator = new WattsStrogatzRelationshipGenerator(new WattsStrogatzConfig(numberOfNodes, meanDegree, betaCoefficient));
        assertEquals((int) (meanDegree * numberOfNodes * .5), generator.doGenerateEdges().size());
    }
}