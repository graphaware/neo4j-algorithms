package com.graphaware.module.algo.generator.config;

/**
 * {@link RelationshipGeneratorConfig} for {@link com.graphaware.module.algo.generator.relationship.WattsStrogatzRelationshipGenerator}.
 * <p/>
 * TODO: Document exactly what the values mean, what are their permitted values, and what their recommended values are.
 */
public class WattsStrogatzConfig extends NumberOfNodesBasedConfig {

    private final int meanDegree;
    private final double beta;

    /**
     * Construct a new config.
     *
     * @param numberOfNodes number of nodes in the network.
     * @param meanDegree    mean degree of the regular ring network constructed as an initial step for Watts-Strogatz.
     * @param beta          probability of edge rewiring at a given step.
     */
    public WattsStrogatzConfig(int numberOfNodes, int meanDegree, double beta) {
        super(numberOfNodes);
        this.meanDegree = meanDegree;
        this.beta = beta;
    }

    /**
     * Get the mean degree of nodes in the generated network.
     *
     * @return mean degree.
     */
    public int getMeanDegree() {
        return meanDegree;
    }

    /**
     * Get the probability of edge rewiring at each step.
     *
     * @return p.
     */
    public double getBeta() {
        return beta;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid() {
        return !(meanDegree % 2 != 0 ||
                meanDegree < 3 ||
                meanDegree > getNumberOfNodes() - 1) &&
                (0 <= beta && beta <= 1);
    }
}
