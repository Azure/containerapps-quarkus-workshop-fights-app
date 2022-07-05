package com.azure.containerapps.quarkus.workshop.superheroes.fight;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(
    info = @Info(title = "Fight API", description = "This API allows a hero and a villain to fight each other.", version = "1.0"),
    servers = {
        @Server(url = "http://localhost:8703", description = "Local server")
    }
)
public class FightApplication extends Application {
}
