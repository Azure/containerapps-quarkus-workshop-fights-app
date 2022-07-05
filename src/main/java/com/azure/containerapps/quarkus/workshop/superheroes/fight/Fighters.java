package com.azure.containerapps.quarkus.workshop.superheroes.fight;

import com.azure.containerapps.quarkus.workshop.superheroes.fight.client.Hero;
import com.azure.containerapps.quarkus.workshop.superheroes.fight.client.Villain;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Entity class representing Fighters
 */
@Schema(description = "A fight between one hero and one villain")
public class Fighters {

    @NotNull
    @Valid
    public Hero hero;

    @NotNull
    @Valid
    public Villain villain;

}
