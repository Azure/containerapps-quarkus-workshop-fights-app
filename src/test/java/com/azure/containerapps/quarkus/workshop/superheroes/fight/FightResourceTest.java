package com.azure.containerapps.quarkus.workshop.superheroes.fight;

import com.azure.containerapps.quarkus.workshop.superheroes.fight.client.Hero;
import com.azure.containerapps.quarkus.workshop.superheroes.fight.client.MockHeroProxy;
import com.azure.containerapps.quarkus.workshop.superheroes.fight.client.MockVillainProxy;
import com.azure.containerapps.quarkus.workshop.superheroes.fight.client.Villain;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.HttpHeaders.ACCEPT;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class FightResourceTest {

    @Test
    void shouldAddAnItem() {

        int initialNbFights = get("/api/fights").then()
            .statusCode(OK.getStatusCode())
            .extract().body().as(getFightTypeRef()).size();

        Hero hero = new Hero();
        hero.name = MockHeroProxy.DEFAULT_HERO_NAME;
        hero.picture = MockHeroProxy.DEFAULT_HERO_PICTURE;
        hero.level = MockHeroProxy.DEFAULT_HERO_LEVEL;
        hero.powers = MockHeroProxy.DEFAULT_HERO_POWERS;
        Villain villain = new Villain();
        villain.name = MockVillainProxy.DEFAULT_VILLAIN_NAME;
        villain.picture = MockVillainProxy.DEFAULT_VILLAIN_PICTURE;
        villain.level = MockVillainProxy.DEFAULT_VILLAIN_LEVEL;
        villain.powers = MockVillainProxy.DEFAULT_VILLAIN_POWERS;
        Fighters fighters = new Fighters();
        fighters.hero = hero;
        fighters.villain = villain;

        String fightId = given()
            .body(fighters)
            .header(CONTENT_TYPE, APPLICATION_JSON)
            .header(ACCEPT, APPLICATION_JSON)
            .when()
            .post("/api/fights")
            .then()
            .statusCode(OK.getStatusCode())
            .body(containsString("winner"), containsString("loser"))
            .extract().body().jsonPath().getString("id");

        assertNotNull(fightId);

        int nbFights = get("/api/fights").then()
            .statusCode(OK.getStatusCode())
            .extract().body().as(getFightTypeRef()).size();
        assertEquals(initialNbFights + 1, nbFights);
    }

    @Test
    void shouldGetRandomFighters() {
        given()
            .when().get("/api/fights/fighters")
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

    private TypeRef<List<Fight>> getFightTypeRef() {
        return new TypeRef<List<Fight>>() {
            // Kept empty on purpose
        };
    }
}
