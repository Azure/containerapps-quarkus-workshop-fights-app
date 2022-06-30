package com.azure.containerapps.quarkus.workshop.superheroes.fight;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Objects;

@MongoEntity(collection = "Fights")
@Schema(description = "Each fight has a winner and a loser")
public class Fight extends PanacheMongoEntity {
    @NotNull
    public Instant fightDate;

    @NotEmpty
    public String winnerName;

    @NotNull
    public Integer winnerLevel;

    @NotEmpty
    public String winnerPicture;

    @NotEmpty
    public String loserName;

    @NotNull
    public Integer loserLevel;

    @NotEmpty
    public String loserPicture;

    @NotEmpty
    public String winnerTeam;

    @NotEmpty
    public String loserTeam;

    @Override
    public String toString() {
        return "Fight{" +
            "fightDate=" + this.fightDate +
            ", id=" + this.id +
            ", winnerName='" + this.winnerName + '\'' +
            ", winnerLevel=" + this.winnerLevel +
            ", winnerPicture='" + this.winnerPicture + '\'' +
            ", loserName='" + this.loserName + '\'' +
            ", loserLevel=" + this.loserLevel +
            ", loserPicture='" + this.loserPicture + '\'' +
            ", winnerTeam='" + this.winnerTeam + '\'' +
            ", loserTeam='" + this.loserTeam + '\'' +
            '}';
    }
}
