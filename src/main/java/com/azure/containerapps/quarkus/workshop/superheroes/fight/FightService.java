package com.azure.containerapps.quarkus.workshop.superheroes.fight;

import com.azure.containerapps.quarkus.workshop.superheroes.fight.client.HeroProxy;
import com.azure.containerapps.quarkus.workshop.superheroes.fight.client.VillainProxy;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.temporal.ChronoUnit;
import java.util.List;

@ApplicationScoped
public class FightService {

    Logger logger;

    HeroProxy heroProxy;
    VillainProxy villainProxy;

    @Inject
    public FightService(@RestClient HeroProxy heroProxy, @RestClient VillainProxy villainProxy, Logger logger) {
        this.heroProxy = heroProxy;
        this.villainProxy = villainProxy;
        this.logger = logger;
    }

    public List<Fight> findAllFights() {
        return Fight.listAll();
    }

    @Timeout(value = 5, unit = ChronoUnit.SECONDS)
    @Fallback(fallbackMethod = "fallbackHelloHeroes")
    public String helloHeroes() {
        logger.info("Pinging the Heroes microservice");
        return this.heroProxy.hello();
    }

    String fallbackHelloHeroes() {
        logger.warn("Could not invoke the Heroes microservice");
        return "Could not invoke the Heroes microservice";
    }

    @Timeout(value = 5, unit = ChronoUnit.SECONDS)
    @Fallback(fallbackMethod = "fallbackHelloVillains")
    public String helloVillains() {
        logger.info("Pinging the Villains microservice");
        return this.villainProxy.hello();
    }

    String fallbackHelloVillains() {
        logger.warn("Could not invoke the Villains microservice");
        return "Could not invoke the Villains microservice";
    }
}
