package com.graphaware.module.algo.generator.api;

import com.graphaware.module.algo.generator.GraphGenerator;
import com.graphaware.module.algo.generator.Neo4jGraphGenerator;
import com.graphaware.module.algo.generator.config.*;
import com.graphaware.module.algo.generator.node.SocialNetworkNodeCreator;
import com.graphaware.module.algo.generator.relationship.BarabasiAlbertRelationshipGenerator;
import com.graphaware.module.algo.generator.relationship.ErdosRenyiRelationshipGenerator;
import com.graphaware.module.algo.generator.relationship.SocialNetworkRelationshipCreator;
import com.graphaware.module.algo.generator.relationship.WattsStrogatzRelationshipGenerator;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * REST API for {@link com.graphaware.module.algo.generator.GraphGenerator}.
 */
@Controller
@RequestMapping("/algorithm/generator")
public class GeneratorApi {

    private final GraphGenerator generator;

    @Autowired
    public GeneratorApi(GraphDatabaseService database) {
        this.generator = new Neo4jGraphGenerator(database);
    }

    @RequestMapping(value = "/social/watts-strogatz/{numberOfNodes}/{meanDegree}/{beta}", method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void wattsStrogatzSocialNetwork(@PathVariable int numberOfNodes, @PathVariable int meanDegree, @PathVariable double beta) {
        generator.generateGraph(wattsStrogatzSocialConfig(numberOfNodes, meanDegree, beta));
    }

    @RequestMapping(value = "/social/erdos-renyi/{numberOfNodes}/{numberOfEdges}", method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void erdosRenyiSocialNetwork(@PathVariable int numberOfNodes, @PathVariable int numberOfEdges) {
        generator.generateGraph(erdosRenyiConfiguration(numberOfNodes, numberOfEdges));
    }

    @RequestMapping(value = "/social/barabasi-albert/{numberOfNodes}/{edgesPerNewNode}", method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void barabasiAlbertSocialNetwork(@PathVariable int numberOfNodes, @PathVariable int edgesPerNewNode) {
        generator.generateGraph(barabasiAlbertConfiguration(numberOfNodes, edgesPerNewNode));
    }

    private GeneratorConfiguration wattsStrogatzSocialConfig(int numberOfNodes, int meanDegree, double beta) {
        return new BasicGeneratorConfig(
                new WattsStrogatzRelationshipGenerator(new WattsStrogatzConfig(numberOfNodes, meanDegree, beta)),
                SocialNetworkNodeCreator.getInstance(),
                SocialNetworkRelationshipCreator.getInstance()
        );
    }

    private GeneratorConfiguration erdosRenyiConfiguration(int numberOfNodes, int numberOfEdges) {
        return new BasicGeneratorConfig(
                new ErdosRenyiRelationshipGenerator(new ErdosRenyiConfig(numberOfNodes, numberOfEdges)),
                SocialNetworkNodeCreator.getInstance(),
                SocialNetworkRelationshipCreator.getInstance()
        );
    }

    private GeneratorConfiguration barabasiAlbertConfiguration(int numberOfNodes, int edgesPerNewNode) {
        return new BasicGeneratorConfig(
                new BarabasiAlbertRelationshipGenerator(new BarabasiAlbertConfig(numberOfNodes, edgesPerNewNode)),
                SocialNetworkNodeCreator.getInstance(),
                SocialNetworkRelationshipCreator.getInstance()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleIllegalArguments() {
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFound() {
    }
}
