package com.graphaware.module.algo.generator.relationship;

import com.graphaware.common.util.SameTypePair;
import com.graphaware.common.util.UnorderedPair;
import com.graphaware.module.algo.generator.config.ErdosRenyiConfig;
import com.graphaware.module.algo.generator.utils.RandomIndexChoice;
import com.graphaware.module.algo.generator.utils.ReservoirSampler;

import java.math.BigInteger;
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
 *
 * TODO: The "faster" way should be made faster, or deleted.
 * Vojta: I agree, this is partially an artifact (refractored).
 *        I was hoping to find a
 *        mathematical formula for effectively calculating the
 *        mapping from edge index to unordered pairs  - if I succeeded,
 *        it would be way more faster than the HashSet approach.
 *        The mapping is not trivial though and I was not able
 *        to find the formula yet.
 *
 *        HOWEVER!:
 *        The switch in goGenerateEdges() has its purpose.
 *        Even at the present stage, the second approach is faster
 *        for nets which are almost complete. As a check, try
 *        to generate (20, 190) using both methods.
 *
 *        I will change the test method from PQ to Hash, as you
 *        suggest, that is absolutely true, thanks.
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

        //disabled for now - very slow
//        if (threshold > potentialEdges) {
//            return doGenerateEdgesWithOmitList(); // Make sure to avoid edges (this takes reasonable time only up till ~ 100k)
//        }

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
        int numberOfEdges = getConfiguration().getNumberOfEdges();

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
     * <p/>
     *
     * TODO: Remove the bijection iteration and optimise duplicity test?
     * (effectivelly hashing)
     *
     * @return edge list
     */
    protected List<SameTypePair<Integer>> doGenerateEdgesWithOmitList() {
        int numberOfNodes = getConfiguration().getNumberOfNodes();
        int numberOfEdges = getConfiguration().getNumberOfEdges();

        long maxEdges = numberOfNodes * (numberOfNodes - 1) / 2;

        List<SameTypePair<Integer>> edges = new LinkedList<>();
        PriorityQueue<Long> omitList = new PriorityQueue<>(); // edges to be omited. TODO: Isn't it more efficient to implement this with HashSet?
        RandomIndexChoice indexChoice = new RandomIndexChoice(); // Index choices with omits

        for (int e = 0; e < numberOfEdges; ++e) {
            long choice = indexChoice.randomIndexChoice(maxEdges, omitList); // must be long to accom. rande of maxEdges
            omitList.add(choice);
            UnorderedPair<Integer> edge = indexToEdgeBijection(choice, numberOfNodes);
            edges.add(edge); // Add the newly created edge (guaranteed unique)
        }

        return edges;
    }


    /**
     * Maps the edge list to edges.
     * TODO: The iteration over buckets is not optimal. It would be cool if some simple mathematical formula was behind this.
     * (at the present stage I wasn't able to find any)
     * <p/>
     * Note that long indices have to be used to label the edges, since
     * there are numberOfNodes*(numberOfNodes-1) indices available. This
     * is beyond range of int for networks of size above ~ 1 000 000
     *
     * @param index
     * @return an edge based on its unique label
     */
    private UnorderedPair<Integer> indexToEdgeBijection(long index, int numberOfNodes) {

        // Bijection from edge label to realisation seems to be the bottleneck!
        long cummulative = 0;
        int remainder = 0;
        int j;
        for (j = 0; j < numberOfNodes - 1; ++j) { // how to avoid this loop ?
            cummulative += numberOfNodes - j - 1;
            if (cummulative > index) {
                remainder = (int) (index - cummulative + numberOfNodes - j);
                break; // found the correct j
            }
        }

        return new UnorderedPair<>(j, remainder + j);
    }
}
