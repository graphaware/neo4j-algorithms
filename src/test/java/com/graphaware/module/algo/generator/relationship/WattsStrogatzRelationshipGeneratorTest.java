package com.graphaware.module.algo.generator.relationship;

import com.graphaware.module.algo.generator.config.WattsStrogatzConfig;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

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