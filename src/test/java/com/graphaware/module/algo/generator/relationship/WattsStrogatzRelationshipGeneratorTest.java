package com.graphaware.module.algo.generator.relationship;

import com.graphaware.module.algo.generator.config.WattsStrogatzConfig;
import junit.framework.TestCase;
import org.junit.Test;

public class WattsStrogatzRelationshipGeneratorTest extends TestCase {

    @Test
    public void testDoGenerateEdgesValidity() throws Exception {
        int meanDegree = 4;
        int numberOfNodes = 10;
        double betaCoefficient = 0.5;

        WattsStrogatzRelationshipGenerator generator = new WattsStrogatzRelationshipGenerator(new WattsStrogatzConfig(numberOfNodes, meanDegree, betaCoefficient));

        System.out.println(generator.doGenerateEdges());
        assertEquals((int) (meanDegree * numberOfNodes * .5), generator.doGenerateEdges().size());
    }

    @Test(timeout = 2 * 60 * 1000)
    public void testDoGenerateEdgesPerformance() throws Exception {
        int meanDegree = 4;
        int numberOfNodes = 2_000_000;
        double betaCoefficient = 0.5;

        WattsStrogatzRelationshipGenerator generator = new WattsStrogatzRelationshipGenerator(new WattsStrogatzConfig(numberOfNodes, meanDegree, betaCoefficient));
        assertEquals((int) (meanDegree * numberOfNodes * .5), generator.doGenerateEdges().size());
    }
}