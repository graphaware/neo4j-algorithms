package com.graphaware.module.algo.generator.config;

/**
 * {@link RelationshipGeneratorConfig} for {@link com.graphaware.module.algo.generator.relationship.BarabasiAlbertRelationshipGenerator}.
 */
public class BarabasiAlbertConfig extends NumberOfNodes {

    private final int edgesPerNewNode;

    /**
     * Construct a new config.
     *
     * @param numberOfNodes   number of nodes in the network.
     * @param edgesPerNewNode number of edges per newly added node.
     */
    public BarabasiAlbertConfig(int numberOfNodes, int edgesPerNewNode) {
        super(numberOfNodes);
        this.edgesPerNewNode = edgesPerNewNode;
    }

    public int getEdgesPerNewNode() {
        return edgesPerNewNode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid() {
        return super.isValid() && !(edgesPerNewNode < 2 || edgesPerNewNode + 1 > getNumberOfNodes());
    }
}
