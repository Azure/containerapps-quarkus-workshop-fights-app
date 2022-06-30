package com.azure.containerapps.quarkus.workshop.superheroes.fight;

import com.azure.containerapps.quarkus.workshop.superheroes.fight.client.MockHeroProxy;
import com.azure.containerapps.quarkus.workshop.superheroes.fight.client.MockVillainProxy;
import io.quarkus.test.junit.QuarkusTest;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class FightResourceTest {

    @Test
    void shouldGetRandomFighters() {
        given()
            .when().get("/api/fights/randomfighters")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .body("hero.name", Is.is(MockHeroProxy.DEFAULT_HERO_NAME))
            .body("hero.picture", Is.is(MockHeroProxy.DEFAULT_HERO_PICTURE))
            .body("hero.level", Is.is(MockHeroProxy.DEFAULT_HERO_LEVEL))
            .body("villain.name", Is.is(MockVillainProxy.DEFAULT_VILLAIN_NAME))
            .body("villain.picture", Is.is(MockVillainProxy.DEFAULT_VILLAIN_PICTURE))
            .body("villain.level", Is.is(MockVillainProxy.DEFAULT_VILLAIN_LEVEL));
    }

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/api/fights/hello")
          .then()
             .statusCode(200)
             .body(is("Hello from Fight Resource"));
    }

    @Test
    public void testHelloVillainEndpoint() {
        given()
          .when().get("/api/fights/hello/villains")
          .then()
             .statusCode(200)
             .body(is("Hello from Villain Mock"));
    }

    @Test
    public void testHelloHeroEndpoint() {
        given()
          .when().get("/api/fights/hello/heroes")
          .then()
             .statusCode(200)
             .body(is("Hello from Hero Mock"));
    }
}
