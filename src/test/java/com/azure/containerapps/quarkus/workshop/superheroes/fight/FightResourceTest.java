package com.azure.containerapps.quarkus.workshop.superheroes.fight;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class FightResourceTest {

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
