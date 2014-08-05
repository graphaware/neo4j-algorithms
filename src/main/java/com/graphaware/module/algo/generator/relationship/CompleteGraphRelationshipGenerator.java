package com.graphaware.module.algo.generator.relationship;

import com.graphaware.common.util.SameTypePair;
import com.graphaware.common.util.UnorderedPair;
import com.graphaware.module.algo.generator.config.NumberOfNodesBasedConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RelationshipGenerator} that generates a complete (undirected) graph.
 * Used for the core graph in {@link BarabasiAlbertRelationshipGenerator}.
 */
public class CompleteGraphRelationshipGenerator extends BaseRelationshipGenerator<NumberOfNodesBasedConfig> {

    /**
     * Create a new generator.
     *
     * @param configuration of the generator.
     */
    public CompleteGraphRelationshipGenerator(NumberOfNodesBasedConfig configuration) {
        super(configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<SameTypePair<Integer>> doGenerateEdges() {
        List<SameTypePair<Integer>> graph = new ArrayList<>();

        // Create a completely connected undirected network
        for (int i = 0; i < getConfiguration().getNumberOfNodes(); ++i)
            for (int j = i + 1; j < getConfiguration().getNumberOfNodes(); ++j)
                graph.add(new UnorderedPair<>(i, j));

        return graph;
    }
}
