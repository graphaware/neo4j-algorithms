GraphAware Neo4j Algorithms
===========================

[![Build Status](https://travis-ci.org/graphaware/neo4j-algorithms.png)](https://travis-ci.org/graphaware/neo4j-algorithms) | <a href="http://graphaware.com/downloads/" target="_blank">Downloads</a> | <a href="http://graphaware.com/site/algorithms/latest/apidocs/" target="_blank">Javadoc</a> | Latest Release: 2.1.4.18.2

GraphAware Algorithms is a library of graph algorithms for Neo4j.

* [Graph Generators](#graph-generators)
* [Path Finding](#path-finding)


Getting the Software
--------------------

### Server Mode

When using Neo4j in the <a href="http://docs.neo4j.org/chunked/stable/server-installation.html" target="_blank">standalone server</a> mode,
you will need the <a href="https://github.com/graphaware/neo4j-framework" target="_blank">GraphAware Neo4j Framework</a> and GraphAware Neo4j Algorithms .jar files (both of which you can <a href="http://graphaware.com/downloads/" target="_blank">download here</a>) dropped
into the `plugins` directory of your Neo4j installation. After Neo4j restart, you will be able to use the REST APIs of the Algorithms.

### Embedded Mode / Java Development

Java developers that use Neo4j in <a href="http://docs.neo4j.org/chunked/stable/tutorials-java-embedded.html" target="_blank">embedded mode</a>
and those developing Neo4j <a href="http://docs.neo4j.org/chunked/stable/server-plugins.html" target="_blank">server plugins</a>,
<a href="http://docs.neo4j.org/chunked/stable/server-unmanaged-extensions.html" target="_blank">unmanaged extensions</a>,
GraphAware Runtime Modules, or Spring MVC Controllers can include use the Algorithms as a dependency for their Java project.

#### Releases

Releases are synced to <a href="http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22algorithms%22" target="_blank">Maven Central repository</a>. When using Maven for dependency management, include the following dependency in your pom.xml.

    <dependencies>
        ...
        <dependency>
            <groupId>com.graphaware.neo4j</groupId>
            <artifactId>algorithms</artifactId>
            <version>2.1.4.18.2</version>
        </dependency>
        ...
    </dependencies>

#### Snapshots

To use the latest development version, just clone this repository, run `mvn clean install` and change the version in the
dependency above to 2.1.4.18.3-SNAPSHOT.

#### Note on Versioning Scheme

The version number has two parts. The first four numbers indicate compatibility with Neo4j GraphAware Framework.
 The last number is the version of the Algorithms library. For example, version 2.1.3.10.2 is version 2 of the library
 compatible with GraphAware Neo4j Framework 2.1.3.10.

Using the Software
------------------

### Graph Generators

It is often required for testing and implementation to work with a randomly generated graph. Getting one isn't as simple
as it sounds - care has to be taken when generating relationships in order to obtain the desired network shape. A lot of
research has gone into networks in the past decades; we have taken some of it and implemented a number of random graph
generators. For a theoretical introduction, please take a look at the following blog posts:
* [Random Graph Models (Part I)](http://graphaware.com/graph/theory/2014/07/16/random-graphs-part-one.html)
* [Random Graph Models (Part II)](http://graphaware.com/graph/theory/2014/08/06/random-graphs-part-two.html)

#### Java

In order to use the graph generators from Java, get the library following the instructions above and have a look at the
[Javadoc](http://graphaware.com/site/algorithms/latest/apidocs) and tests. This will give you a really good idea of how the generators can be used. Provided implementations
include:
* Erdos-Renyi Model
* Barabasi-Albert Model
* Watts-Strogatz Model
* Configuration Model

You have to choose two things: the shape of the network (one of the models above) and its contents (what labels will nodes
get? what properties? what types of relationships will you generate?).

To choose the contents of the network, you can plug in your own implementations of [`NodeCreator`](http://graphaware.com/site/algorithms/latest/apidocs/com/graphaware/module/algo/generator/node/NodeCreator.html) and [`RelationshipCreator`](http://graphaware.com/site/algorithms/latest/apidocs/com/graphaware/module/algo/generator/relationship/RelationshipCreator.html) to create nodes and relationships
with types, labels, and properties of your choosing. You can use the provided `SocialNetworkNodeCreator` and
`SocialNetworkRelationshipCreator` to get started quickly.

#### REST API

For now, the provided REST API allows you to create social networks only with FRIEND_OF relationships and randomly generated
 English names and uniformly assigned genders for people nodes.

* POST to `http://your-server:7474/graphaware/algorithm/generator/social/watts-strogatz/{numberOfNodes}/{meanDegree}/{beta}` will
return a CREATED 201 status when your Watts-Strogatz network has been successfully created. Please refer to [`WattsStrogatzConfig`](http://graphaware.com/site/algorithms/latest/apidocs/com/graphaware/module/algo/generator/config/WattsStrogatzConfig.html) for explanation
of the parameters you need to provide.

* POST to `http://your-server:7474/graphaware/algorithm/generator/social/erdos-renyi/{numberOfNodes}/{numberOfEdges}` will
return a CREATED 201 status when your Erdos-Renyi network has been successfully created. Please refer to [`ErdosRenyiConfig`](http://graphaware.com/site/algorithms/latest/apidocs/com/graphaware/module/algo/generator/config/ErdosRenyiConfig.html) for explanation
of the parameters you need to provide.

* POST to `http://your-server:7474/graphaware/algorithm/generator/social/barabasi-albert/{numberOfNodes}/{edgesPerNewNode}` will
return a CREATED 201 status when your Barabasi-Albert network has been successfully created. Please refer to [`BarabasiAlberConfig`](http://graphaware.com/site/algorithms/latest/apidocs/com/graphaware/module/algo/generator/config/BarabasiAlbertConfig.html) for explanation
of the parameters you need to provide.

<a name="algos"/>
### Path Finding

#### Increasingly Longer (Weighted) Shortest Paths

This library allows to find a given number of shortest paths between two nodes.
It is different from standard shortest path finding, because it allows to specify the desired number of results. Provided
that there are enough paths between the two nodes in the graph, this path finder will first return all the shortest paths,
then all the paths one hop longer, then two hops longer, etc., until enough paths have been returned.

Please note that nodes that are on a path with certain length will not be considered for paths with greater lengths.

For example, given the following graph:

    (1)->(2)->(3)
    (1)->(4)->(5)->(3)
    (4)->(2)

the shortest path from (1) to (3) is (1)->(2)->(3) and has a length of 2. If more paths are needed, the next path
returned will be (1)->(4)->(5)->(3) with a length of 3. Note that there is another path of length 3:
(1)->(4)->(2)->(3), but it is not returned, since (2)->(3) is contained in a shorter path.

##### REST API

When deployed in server mode, issue a POST request to `http://your-server-address:7474/graphaware/algorithm/path/increasinglyLongerShortestPath`
with the following JSON minimal payload

```json
{
     "startNode": 0,
     "endNode": 2
 }
```

in order to find all paths between nodes with ID 0 and 2, sorted by increasing length.

You can also optionally specify the following:
* maximum path depth (`maxDepth`), 3 by default
* maximum number of results (`maxResults`), unlimited by default
* cost property (`costProperty`), which must be a number-valued relationship property that indicates the cost of the path, none by default
* sort order (`sortOrder`):
    * LENGTH_ASC (By increasing length. Ordering of paths with the same lengths is unspecified. This is the default.)
    * LENGTH_ASC_THEN_COST_ASC (By increasing length, then by increasing cost. The cost property must be specified.)
    * LENGTH_ASC_THEN_COST_DESC (By increasing length, then by decreasing cost. The cost property must be specified.)
* whether to include node labels in the result (`includeNodeLabels`) set to true or false, default is false
* which node properties to include in the result (`nodeProperties`) as an array of Strings, default is none
* which relationship properties to include in the result (`relationshipProperties`) as an array of Strings, default is none
* which relationships to traverse by supplying one of the following (default is all relationships in both directions):
    * the direction in which to traverse relationships (`direction`), INCOMING, OUTGOING, or BOTH
    * relationship types and directions for each (`typesAndDirections` array with `type` and optionally `direction`)

An example input with most of these parameters would look like this:

```json
{
    "startNode": 0,
    "endNode": 2,
    "costProperty": "cost",
    "sortOrder": "LENGTH_ASC_THEN_COST_DESC",
    "maxDepth": 2,
    "maxResults": 10,
    "includeNodeLabels": true,
    "nodeProperties": [
            "name"
    ],
    "relationshipProperties": [
            "cost"
    ],
    "typesAndDirections": [
        {
            "type": "R1"
        },
        {
            "type": "R2",
            "direction": "INCOMING"
        }

    ]
}
```

The output depends on the specification, but could look something like this:

```json
[
    {
        "nodes": [
            {
                "id": 0
            },
            {
                "id": 5
            },
            {
                "id": 6
            },
            {
                "id": 2
            }
        ],
        "relationships": [
            {
                "id": 6,
                "type": "R1",
                "direction": "OUTGOING"
            },
            {
                "id": 7,
                "type": "R1",
                "direction": "OUTGOING"
            },
            {
                "id": 8,
                "type": "R1",
                "direction": "INCOMING"
            }
        ]
    }
]
```

##### Java API

For the Java API, please refer to the Javadoc of `NumberOfShortestPathsFinder` and `PathFinderInput`.


License
-------

Copyright (c) 2014 GraphAware

GraphAware is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License
as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.
If not, see <http://www.gnu.org/licenses/>.