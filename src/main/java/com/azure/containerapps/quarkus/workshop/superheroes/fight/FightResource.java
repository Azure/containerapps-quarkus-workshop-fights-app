package com.azure.containerapps.quarkus.workshop.superheroes.fight;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.util.List;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/api/fights")
public class FightResource {

    Logger logger;
    FightService service;

    @Inject
    public FightResource(FightService service, Logger logger) {
        this.service = service;
        this.logger = logger;
    }

    @GET
    @Path("/fighters")
    public Response getRandomFighters() {
        logger.info("Get random fighters");
        Fighters fighters = service.findRandomFighters();
        logger.debug("Get random fighters : " + fighters);
        return Response.ok(fighters).build();
    }

    @GET
    public Response getAllFights() {
        logger.info("Get all the fights");
        List<Fight> fights = service.findAllFights();
        logger.debug("Total number of fights : " + fights.size());
        return Response.ok(fights).build();
    }

    @POST
    public Fight fight(@Valid Fighters fighters) {
        logger.info("Fight");
        logger.debug("Fight between : " + fighters);
        return service.fight(fighters);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/hello")
    @Tag(name = "hello")
    @Operation(summary = "Returns hello from the Fight Resource")
    public String hello() {
        logger.info("Hello from Fight Resource");
        return "Hello from Fight Resource";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/hello/heroes")
    @Tag(name = "hello")
    @Operation(summary = "Ping Heroes hello")
    public String helloHeroes() {
        logger.info("Ping Heroes hello");
        return service.helloHeroes();
    }

    @GET
    @Produces(TEXT_PLAIN)
    @Path("/hello/villains")
    @Tag(name = "hello")
    @Operation(summary = "Ping Villains hello")
    public String helloVillains() {
        logger.info("Ping Villains hello");
        return service.helloVillains();
    }
}
