package com.graphaware.module.algo.generator.config;

/**
 *  Configuration for a {@link com.graphaware.module.algo.generator.relationship.RelationshipGenerator}.
 */
public interface RelationshipGeneratorConfig {

    /**
     * Returns true iff the config is valid.
     *
     * @return true if the config is valid.
     */
    boolean isValid();
}
