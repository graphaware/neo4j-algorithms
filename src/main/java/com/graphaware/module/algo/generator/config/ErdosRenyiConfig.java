package com.graphaware.module.algo.generator.config;

import java.math.BigInteger;

/**
 * Erdos Renyi graph generator configuration
 */
public class ErdosRenyiConfig extends NumberOfNodes {

    private final int numberOfEdges;

    /**
     * Constructs a new config.
     *
     * @param numberOfNodes number of nodes present in the network
     * @param numberOfEdges number of edges present in the network
     */
    public ErdosRenyiConfig(int numberOfNodes, int numberOfEdges) {
        super(numberOfNodes);
        this.numberOfEdges = numberOfEdges;
    }

    public int getNumberOfEdges() {
        return numberOfEdges;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid() {
        BigInteger maxNumberOfEdges = BigInteger.valueOf(getNumberOfNodes());
        maxNumberOfEdges = maxNumberOfEdges.multiply(maxNumberOfEdges.subtract(BigInteger.ONE));
        maxNumberOfEdges = maxNumberOfEdges.divide(BigInteger.valueOf(2));

        return super.isValid() && numberOfEdges > 0 && BigInteger.valueOf(numberOfEdges).compareTo(maxNumberOfEdges) < 1;
    }
}
