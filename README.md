GraphAware Neo4j Algorithms (WIP - not ready for production!)
============================================================

[![Build Status](https://travis-ci.org/graphaware/neo4j-algorithms.png)](https://travis-ci.org/graphaware/neo4j-algorithms)

<a name="algos"/>
### Graph Algorithms

#### Increasingly Longer (Weighted) Shortest Paths

A managed plugin is provided and accessible via REST API that finds finds a given number of shortest paths between two nodes.
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

**API:** `POST` to `{SERVER_URI}/db/data/ext/NumberOfShortestPaths/node/{sourceNodeId}/paths` with the following parameters:

```
{
  "extends" : "node",
  "description" : "Find a number shortest path between two nodes, with increasing path length and optionally path cost.",
  "name" : "paths",
  "parameters" : [ {
    "description" : "The node to find the shortest paths to.",
    "optional" : false,
    "name" : "target",
    "type" : "node"
  }, {
    "description" : "The relationship types to follow when searching for the shortest paths. If omitted all types are followed.",
    "optional" : true,
    "name" : "types",
    "type" : "strings"
  }, {
    "description" : "The maximum path length to search for, default value (if omitted) is 3.",
    "optional" : true,
    "name" : "depth",
    "type" : "integer"
  }, {
    "description" : "The desired number of results, default value (if omitted) is 10. The actual number of results can be smaller (if no other paths exist).",
    "optional" : true,
    "name" : "noResults",
    "type" : "integer"
  }, {
    "description" : "The name of relationship property indicating its weight/cost. If omitted, result isn't sorted by total path weights. If the property is missing for a relationship, Integer.MAX_VALUE is used.",
    "optional" : true,
    "name" : "weight",
    "type" : "string"
  } ]
}
```

License
-------

Copyright (c) 2014 GraphAware

GraphAware is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License
as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.
If not, see <http://www.gnu.org/licenses/>.