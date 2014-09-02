package com.graphaware.module.algo.generator.relationship;

import com.graphaware.common.util.SameTypePair;
import com.graphaware.common.util.UnorderedPair;
import com.graphaware.module.algo.generator.config.ErdosRenyiConfig;
import com.graphaware.module.algo.generator.utils.RandomIndexChoice;

import java.util.*;

/**
 * {@link RelationshipGenerator} implementation according to Erdos-Renyi random graphs. These are a basic class of
 * random graphs with exponential cut-off. A phase transition from many components graph to a completely connected graph
 * is present.
 * <p/>
 * The algorithm has a switch from sparse ER graph to dense ER graph generator. The sparse algorithm is based on
 * trial-correction method as suggested in the paper cited below. This is extremely inefficient for nearly-complete
 * graphs. The dense algorithm (written by GraphAware) is based on careful avoiding of edge indices in the selection.
 * <p/>
 * The switch allows to generate even complete graphs (eg. (V, E) = (20, 190) in a reasonable time. The switch is turned
 * on to dense graph generator for the case when number of edges requested is greater than half of total possible edges
 * that could be generated.
 */
public class ErdosRenyiRelationshipGenerator extends BaseRelationshipGenerator<ErdosRenyiConfig> {

    private final Random random = new Random();

    /**
     * Construct a new generator.
     *
     * @param configuration of the generator.
     */
    public ErdosRenyiRelationshipGenerator(ErdosRenyiConfig configuration) {
        super(configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<SameTypePair<Integer>> doGenerateEdges() {
        long threshold = getConfiguration().getNumberOfEdges() * 4;
        long potentialEdges = getConfiguration().getNumberOfNodes() * (getConfiguration().getNumberOfNodes() - 1);

        if (threshold > potentialEdges) {
            return doGenerateEdgesWithOmitList(); // Make sure to avoid edges
        }

        return doGenerateEdgesSimpler(); // Be more heuristic (pajek implementation using HashSet).
    }

    /**
     * This algorithm is implemented as recommended in
     * <p/>
     * Efficient generation of large random networks
     * by Vladimir Batagelj and Ulrik Brandes
     * <p/>
     * PHYSICAL REVIEW E 71, 036113, 2005
     * <p/>
     * and relies on excellent hashing performance of Java implementation of HashSet.
     *
     * @return edge list
     */
    private List<SameTypePair<Integer>> doGenerateEdgesSimpler() {
        int numberOfNodes = getConfiguration().getNumberOfNodes();
        long numberOfEdges = getConfiguration().getNumberOfEdges();

        HashSet<SameTypePair<Integer>> edges = new HashSet<>();

        while (edges.size() < numberOfEdges) {
            int origin = random.nextInt(numberOfNodes);
            int target = random.nextInt(numberOfNodes);

            if (target == origin) {
                continue;
            }

            edges.add(new UnorderedPair<>(origin, target));
        }

        return new ArrayList<>(edges);
    }

    /**
     * Improved implementation of Erdos-Renyi generator based on bijection from
     * edge labels to edge realisations. Works very well for large number of nodes,
     * but is slow with increasing number of edges. Best for denser networks, with
     * a clear giant component.
     *
     * @return edge list
     */
    protected List<SameTypePair<Integer>> doGenerateEdgesWithOmitList() {
        long numberOfNodes = getConfiguration().getNumberOfNodes();
        long numberOfEdges = getConfiguration().getNumberOfEdges();

        long maxEdges = numberOfNodes * (numberOfNodes - 1) / 2;

        List<SameTypePair<Integer>> edges = new LinkedList<>();
        HashSet<Long> omitList = new HashSet<>(); // edges to be omited.
        RandomIndexChoice indexChoice = new RandomIndexChoice(); // Index choices with omits

        for (long e = 0; e < numberOfEdges; ++e) {
            long choice = indexChoice.randomIndexChoice(maxEdges, omitList); // must be long to accom. rande of maxEdges
            omitList.add(choice);
            UnorderedPair<Integer> edge = indexToEdgeBijection(choice, numberOfNodes);
            edges.add(edge); // Add the newly created edge (guaranteed unique)
        }

        return edges;
    }


    /**
     * Maps the edge list to edges.
     * <p/>
     * Note that long indices have to be used to label the edges, since
     * there are numberOfNodes*(numberOfNodes-1)/2 indices available. This
     * is beyond range of int for networks of size above ~ 1 000 000
     *
     * @param index
     * @return an edge based on its unique label
     */
    private UnorderedPair<Integer> indexToEdgeBijection(long index, long numberOfNodes) {
        long boundaryL = numberOfNodes * (numberOfNodes - 1) / 1;

        if (index < 0 || boundaryL <= index) {
            throw new IndexOutOfBoundsException("Index is greater than number of possible edges: " + index + " " + boundaryL);
        }
        int i = (int) Math.ceil((Math.sqrt(1 + 8 * (index + 1)) - 1) / 2);   // get the factoradic index (int should suffice - check)
        int diff = (int) index + 1 - (int) ((long) i * (long) (i - 1)) / 2; // convert to long to avoid overflows. The difference should be int range. Ideally change all generators for <? ext. Number> later

        return new UnorderedPair<>(i, diff-1);
    }
}
