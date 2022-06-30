package com.azure.containerapps.quarkus.workshop.superheroes.fight;

import com.azure.containerapps.quarkus.workshop.superheroes.fight.client.Hero;
import com.azure.containerapps.quarkus.workshop.superheroes.fight.client.HeroProxy;
import com.azure.containerapps.quarkus.workshop.superheroes.fight.client.Villain;
import com.azure.containerapps.quarkus.workshop.superheroes.fight.client.VillainProxy;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

import static javax.transaction.Transactional.TxType.REQUIRED;

@ApplicationScoped
public class FightService {

    Logger logger;

    HeroProxy heroProxy;
    VillainProxy villainProxy;

    private final Random random = new Random();

    @Inject
    public FightService(@RestClient HeroProxy heroProxy, @RestClient VillainProxy villainProxy, Logger logger) {
        this.heroProxy = heroProxy;
        this.villainProxy = villainProxy;
        this.logger = logger;
    }

    public List<Fight> findAllFights() {
        return Fight.listAll();
    }

    Fighters findRandomFighters() {
        Hero hero = findRandomHero();
        Villain villain = findRandomVillain();
        Fighters fighters = new Fighters();
        fighters.hero = hero;
        fighters.villain = villain;
        return fighters;
    }

    @Fallback(fallbackMethod = "fallbackRandomHero")
    Hero findRandomHero() {
        return heroProxy.findRandomHero();
    }

    @Fallback(fallbackMethod = "fallbackRandomVillain")
    Villain findRandomVillain() {
        return villainProxy.findRandomVillain();
    }

    public Hero fallbackRandomHero() {
        logger.warn("Falling back on Hero");
        Hero hero = new Hero();
        hero.name = "Fallback hero";
        hero.picture = "https://dummyimage.com/280x380/1e8fff/ffffff&text=Fallback+Hero";
        hero.powers = "Fallback hero powers";
        hero.level = 1;
        return hero;
    }

    public Villain fallbackRandomVillain() {
        logger.warn("Falling back on Villain");
        Villain villain = new Villain();
        villain.name = "Fallback villain";
        villain.picture = "https://dummyimage.com/280x380/b22222/ffffff&text=Fallback+Villain";
        villain.powers = "Fallback villain powers";
        villain.level = 42;
        return villain;
    }

    @Transactional(REQUIRED)
    public Fight fight(Fighters fighters) {
        // Amazingly fancy logic to determine the winner...
        Fight fight;

        int heroAdjust = random.nextInt(20);
        int villainAdjust = random.nextInt(20);

        if ((fighters.hero.level + heroAdjust) > (fighters.villain.level + villainAdjust)) {
            fight = heroWon(fighters);
        } else if (fighters.hero.level < fighters.villain.level) {
            fight = villainWon(fighters);
        } else {
            fight = random.nextBoolean() ? heroWon(fighters) : villainWon(fighters);
        }

        fight.fightDate = Instant.now();
        fight.persist();

        return fight;
    }

    private Fight heroWon(Fighters fighters) {
        logger.info("Yes, Hero won :o)");
        Fight fight = new Fight();
        fight.winnerName = fighters.hero.name;
        fight.winnerPicture = fighters.hero.picture;
        fight.winnerLevel = fighters.hero.level;
        fight.loserName = fighters.villain.name;
        fight.loserPicture = fighters.villain.picture;
        fight.loserLevel = fighters.villain.level;
        fight.winnerTeam = "heroes";
        fight.loserTeam = "villains";
        return fight;
    }

    private Fight villainWon(Fighters fighters) {
        logger.info("Gee, Villain won :o(");
        Fight fight = new Fight();
        fight.winnerName = fighters.villain.name;
        fight.winnerPicture = fighters.villain.picture;
        fight.winnerLevel = fighters.villain.level;
        fight.loserName = fighters.hero.name;
        fight.loserPicture = fighters.hero.picture;
        fight.loserLevel = fighters.hero.level;
        fight.winnerTeam = "villains";
        fight.loserTeam = "heroes";
        return fight;
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
