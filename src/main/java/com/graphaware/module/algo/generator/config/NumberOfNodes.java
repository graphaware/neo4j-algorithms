package com.graphaware.module.algo.generator.config;

/**
 *
 */
public class NumberOfNodes implements RelationshipGeneratorConfig {

    private final int numberOfNodes;

    /**
     * Construct a new config.
     *
     * @param numberOfNodes number of nodes present in the network.
     */
    public NumberOfNodes(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid() {
        return numberOfNodes >= 2;
    }
}
