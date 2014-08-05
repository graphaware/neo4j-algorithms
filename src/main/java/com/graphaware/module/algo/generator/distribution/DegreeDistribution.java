package com.graphaware.module.algo.generator.distribution;

import java.util.List;

/**
 * A distribution of node degrees for {@link com.graphaware.module.algo.generator.relationship.RelationshipGenerator}s.
 */
public interface DegreeDistribution {

    /**
     * Returns true iff the config is valid.
     *
     * @return true if the config is valid.
     */
    boolean isValid();

    /**
     * Get the node degrees produced by this distribution.
     *
     * @return node degrees. Should be immutable (read-only).
     */
    List<Integer> getDegrees();

    /**
     * Returns true iff this distribution is a zero-list.
     *
     * @return true iff this distribution is a zero-list.
     */
    boolean isZeroList();

    /**
     * Get degree by index.
     *
     * @param index of the degree to get.
     * @return degree.
     */
    int get(int index);

    /**
     * Get the size of the distribution, i.e., the number of nodes.
     *
     * @return size.
     */
    int size();
}
