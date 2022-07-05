package com.azure.containerapps.quarkus.workshop.superheroes.fight;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/api/fights")
@Tag(name = "fight")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
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
    @Operation(operationId = "getRandomFighters", summary = "Returns two random fighters: a hero and a villain")
    @APIResponse(
        responseCode = "200",
        content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Fighters.class, required = true))
    )
    public Response getRandomFighters() {
        logger.info("Get two random fighters");
        Fighters fighters = service.findRandomFighters();
        logger.debug("Found two random fighters : " + fighters);
        return Response.ok(fighters).build();
    }

    @GET
    @Operation(operationId = "getAllFights", summary = "Returns all the fights")
    @APIResponse(
        responseCode = "200",
        content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Fight.class))
    )
    public Response getAllFights() {
        logger.info("Get all the fights");
        List<Fight> fights = service.findAllFights();
        logger.debug("Total number of fights : " + fights.size());
        return Response.ok(fights).build();
    }

    @POST
    @Operation(operationId = "fight", summary = "Takes a hero and a villain and make them fight")
    @APIResponse(responseCode = "201")
    public Fight fight(@Valid Fighters fighters) {
        logger.info("Fight");
        logger.debug("Fight between : " + fighters);
        return service.fight(fighters);
    }

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(operationId = "hello", summary = "Returns hello from the Fight resource")
    public String hello() {
        logger.info("Hello from Fight Resource");
        return "Hello from Fight Resource";
    }

    @GET
    @Path("/hello/heroes")
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(operationId = "helloHeroes", summary = "Returns hello from the Heroes resource through the Fight resource")
    public String helloHeroes() {
        logger.info("Hello from the Heroes resource through the Fight resource");
        return service.helloHeroes();
    }

    @GET
    @Produces(TEXT_PLAIN)
    @Path("/hello/villains")
    @Operation(operationId = "helloVillains", summary = "Returns hello from the Villains resource through the Fight resource")
    public String helloVillains() {
        logger.info("Hello from the Villains resource through the Fight resource");
        return service.helloVillains();
    }
}
