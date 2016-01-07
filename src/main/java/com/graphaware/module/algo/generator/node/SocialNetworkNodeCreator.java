/*
 * Copyright (c) 2013-2016 GraphAware
 *
 * This file is part of the GraphAware Framework.
 *
 * GraphAware Framework is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received a copy of
 * the GNU General Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package com.graphaware.module.algo.generator.node;

import com.graphaware.common.util.FileScanner;
import com.graphaware.common.util.Pair;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.unsafe.batchinsert.BatchInserter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A {@link NodeCreator} that assigns every {@link org.neo4j.graphdb.Node} a "Person" {@link org.neo4j.graphdb.Label},
 * a Male/Female {@link Label} (with equal probabilities), and a randomly generated English name under the property key
 * "name". Singleton.
 */
public class SocialNetworkNodeCreator implements NodeCreator {

    private static final Label PERSON_LABEL = DynamicLabel.label("Person");
    private static final Label MALE_LABEL = DynamicLabel.label("Male");
    private static final Label FEMALE_LABEL = DynamicLabel.label("Female");
    private static final String NAME = "name";

    private static final SocialNetworkNodeCreator INSTANCE = new SocialNetworkNodeCreator();

    private List<Pair<Label, String>> gendersAndNames = new ArrayList<>();
    private Random random = new Random();

    protected SocialNetworkNodeCreator() {
        populateGendersAndNames();
    }

    /**
     * Get an instance of this node creator.
     *
     * @return instance.
     */
    public static SocialNetworkNodeCreator getInstance() {
        return INSTANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node createNode(GraphDatabaseService database) {
        Node node = database.createNode(getPersonLabel());

        Pair<Label, String> genderAndName = getGenderAndName();
        node.addLabel(genderAndName.first());

        node.setProperty(NAME, genderAndName.second());

        return node;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long createNode(BatchInserter batchInserter) {
        Pair<Label, String> genderAndName = getGenderAndName();

        return batchInserter.createNode(Collections.<String, Object>singletonMap(NAME, genderAndName.second()), genderAndName.first());
    }

    protected Pair<Label, String> getGenderAndName() {
        return gendersAndNames.get(random.nextInt(gendersAndNames.size()));
    }

    protected Label getPersonLabel() {
        return PERSON_LABEL;
    }

    private void populateGendersAndNames() {
        for (String line : FileScanner.produceLines(SocialNetworkNodeCreator.class.getClassLoader().getResourceAsStream("fake_names.csv"), 0)) {
            String[] fields = line.split(",");
            gendersAndNames.add(new Pair<>("female".equals(fields[0]) ? FEMALE_LABEL : MALE_LABEL, fields[1] + " " + fields[2]));
        }
    }
}
