package com.graphaware.module.algo.generator.distribution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.min;

/**
 * Simple {@link com.graphaware.module.algo.generator.distribution.DegreeDistribution} where the distribution can be directly passed into the constructor.
 */
public class SimpleDegreeDistribution implements DegreeDistribution {

    protected final List<Integer> degrees = new ArrayList<>();

    public SimpleDegreeDistribution(List<Integer> degrees) {
        this.degrees.addAll(degrees);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> getDegrees() {
        return Collections.unmodifiableList(degrees);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isZeroList() {
        for (int degree : degrees) {
            if (degree > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int get(int index) {
        return degrees.get(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return degrees.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid() {
        int degreeSum = 0;
        for (int degree : getDegrees()) {
            degreeSum += degree;
        }

        //Total has to be even by the handshaking lemma
        return (degreeSum % 2) == 0
                && passesErdosGallaiTest();
    }

    /**
     * All valid distributions must be graphical. This is tested using Erdos-Gallai condition on degree distribution
     * graphicality. (see Blitzstein-Diaconis paper)
     *
     * @return true iff passes.
     */
    protected final boolean passesErdosGallaiTest() {
        // Do this in-place instead?
        MutableDegreeDistribution copy = new MutableSimpleDegreeDistribution(getDegrees());

        int L = copy.size();
        int degreeSum = 0;           // Has to be even by the handshaking lemma

        for (int degree : copy.getDegrees()) {
            if (degree < 0) {
                return false;
            }
            degreeSum += degree;
        }

        if (degreeSum % 2 != 0) {
            return false;
        }

        copy.sort(Collections.<Integer>reverseOrder());
        // Erdos-Gallai test
        for (int k = 1; k < L; ++k) {
            int sum = 0;
            for (int i = 0; i < k; ++i) {
                sum += copy.get(i);
            }

            int comp = 0;
            for (int j = k; j < L; ++j) {
                comp += min(k, copy.get(j));
            }

            if (sum > k * (k - 1) + comp) {
                return false;
            }
        }

        return true;
    }

    /**
     * Havel-Hakimi is a recursive alternative to the Erdos-Gallai condition
     *
     * @return true iff passes.
     */
    protected final boolean passesHavelHakimiTest() {
        /*
         * The test fails if there are less available
         * nodes to connect to than the degree of lar-
         * gest node.
         */

        MutableDegreeDistribution copy = new MutableSimpleDegreeDistribution(getDegrees());

        int i = 0;
        int first;
        int L = this.size();

        while (L > 0) {
            first = copy.get(i);
            L--;

            int j = 1;
            for (int k = 0; k < first; ++k) {
                while (copy.get(j) == 0) {

                    j++;
                    if (j > L) {
                        return false;
                    }
                }

                copy.set(j, copy.get(j) - 1);
            }

            copy.set(i, 0);
            copy.sort(Collections.<Integer>reverseOrder());
        }
        return true;
    }
}
