package com.azure.containerapps.quarkus.workshop.superheroes.fight.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/api/villains")
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "villain-proxy")
public interface VillainProxy {

    @GET
    @Path("/random")
    Villain findRandomVillain();

    @GET
    @Path("/hello")
    @Produces(TEXT_PLAIN)
    String hello();
}
