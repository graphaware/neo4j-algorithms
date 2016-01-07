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

package com.graphaware.module.algo.generator.relationship;

import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.unsafe.batchinsert.BatchInserter;

import java.util.Collections;

/**
 * A {@link RelationshipCreator} that only creates relationship of type FRIEND_OF and assigns no properties to those
 * relationships.
 */
public class SocialNetworkRelationshipCreator implements RelationshipCreator {

    private static final RelationshipType FRIEND_OF = DynamicRelationshipType.withName("FRIEND_OF");

    private static final SocialNetworkRelationshipCreator INSTANCE = new SocialNetworkRelationshipCreator();

    private SocialNetworkRelationshipCreator() {
    }

    public static SocialNetworkRelationshipCreator getInstance() {
        return INSTANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Relationship createRelationship(Node first, Node second) {
        return first.createRelationshipTo(second, FRIEND_OF);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long createRelationship(long first, long second, BatchInserter batchInserter) {
        return batchInserter.createRelationship(first, second, FRIEND_OF, Collections.EMPTY_MAP);
    }
}
