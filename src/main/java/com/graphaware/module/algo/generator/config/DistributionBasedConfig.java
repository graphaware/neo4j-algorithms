package com.graphaware.module.algo.generator.config;

import com.graphaware.module.algo.generator.distribution.SimpleDegreeDistribution;

import java.util.List;

/**
 *
 */
public class DistributionBasedConfig extends SimpleDegreeDistribution implements RelationshipGeneratorConfig {

    public DistributionBasedConfig(List<Integer> degrees) {
        super(degrees);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfNodes() {
        return size();
    }
}
