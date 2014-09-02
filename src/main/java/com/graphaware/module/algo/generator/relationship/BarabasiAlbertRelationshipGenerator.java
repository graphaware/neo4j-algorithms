package com.graphaware.module.algo.generator.relationship;

import com.graphaware.common.util.SameTypePair;
import com.graphaware.common.util.UnorderedPair;
import com.graphaware.module.algo.generator.config.BarabasiAlbertConfig;
import com.graphaware.module.algo.generator.config.NumberOfNodesBasedConfig;

import java.util.*;

/**
 * {@link RelationshipGenerator} implemented according to the Barabasi-Albert preferential attachment model, which is
 * appropriate for networks reflecting cumulative advantage (rich get richer).
 * <p/>
 * Each newly added node has a probability weighted by the node degree to be attached. Since BA references
 * (Newmann, Barabasi-Albert) do not define strict conditions on initial state of the model, completely connected network
 * is used to start up the algorithm.
 */
public class BarabasiAlbertRelationshipGenerator extends BaseRelationshipGenerator<BarabasiAlbertConfig> {

    private final Random random = new Random();

    /**
     * Create a new generator.
     *
     * @param configuration of the generator.
     */
    public BarabasiAlbertRelationshipGenerator(BarabasiAlbertConfig configuration) {
        super(configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<SameTypePair<Integer>> doGenerateEdges() {
        int edgesPerNewNode = getConfiguration().getEdgesPerNewNode();
        int numberOfNodes = getConfiguration().getNumberOfNodes();

        // Create a completely connected network
        List<SameTypePair<Integer>> edges
                = new CompleteGraphRelationshipGenerator(new NumberOfNodesBasedConfig(edgesPerNewNode + 1)).doGenerateEdges();

        // Degree list of the network
        List<Integer> degrees = new ArrayList<>(2 * (numberOfNodes * edgesPerNewNode - (edgesPerNewNode * (edgesPerNewNode - 1)) / 2));

        for (int k = 0; k < edgesPerNewNode + 1; k++) {
            for (int l = 0; l < edgesPerNewNode; l++) {
                degrees.add(k);
            }
        }

        // Preferentially attach other nodes
        Set<Integer> omit = new HashSet<>(edgesPerNewNode);
        for (int source = edgesPerNewNode + 1; source < numberOfNodes; source++) {
            omit.clear();

            for (int edge = 0; edge < edgesPerNewNode; edge++) {
                while (true) {
                    int target = degrees.get(random.nextInt(degrees.size()));

                    if (omit.contains(target)) {
                        continue;
                    }

                    omit.add(target); // to avoid multi-edges
                    edges.add(new UnorderedPair<>(target, source)); // Add the edge
                    degrees.add(source);
                    degrees.add(target);

                    break;
                }
            }

        }

        return edges;
    }
}
