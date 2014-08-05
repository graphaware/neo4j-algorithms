package com.graphaware.module.algo.generator.config;

import com.graphaware.module.algo.generator.distribution.SimpleDegreeDistribution;

import java.util.List;

/**
 * {@link SimpleDegreeDistribution} serving as a {@link RelationshipGeneratorConfig}.
 *
 * TODO: Document exactly what the permitted values are
 */
public class DistributionBasedConfig extends SimpleDegreeDistribution implements RelationshipGeneratorConfig {

    /**
     * Create a new config.
     *
     * @param degrees list of node degrees.
     */
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
