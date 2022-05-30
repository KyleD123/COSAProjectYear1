package models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import models.custom_constraints.CompareAwayHomeTeam;

import javax.validation.constraints.NotNull;

@CompareAwayHomeTeam
@DatabaseTable(tableName = "games")
public class Game implements Serializable
{
    @DatabaseField(generatedId = true, unique = true)
    private long lGameID;

    @DatabaseField(canBeNull = false, foreign = true)
    private Team tAwayTeam;

    @DatabaseField(canBeNull = false, foreign = true)
    private Team tHomeTeam;

    @DatabaseField(canBeNull = false)
    @NotNull(message = "There must be a Event Date!")
    private Date dEventDate;

    @DatabaseField(canBeNull = false, foreign = true)
    @NotNull(message = "There must be a tournament tied to this schedule")
    private Tournament tTournament;

    @DatabaseField(canBeNull = false)
    @NotNull(message = "Provide a location and rink!")
    private String sLocation;

    public Game()
    {
    }

    public long getLGameID()
    {
        return this.lGameID;
    }

    public void setLGameID(long lGameID)
    {
        this.lGameID = lGameID;
    }

    public Team getTAwayTeam() {
        return tAwayTeam;
    }

    public void setTAwayTeam(Team obTeam)
    {
        this.tAwayTeam = obTeam;
    }

    public Team getTHomeTeam()
    {
        return tHomeTeam;
    }

    public void setTHomeTeam(Team obTeam)
    {
        this.tHomeTeam = obTeam;
    }

    public Date getDEventDate() {return this.dEventDate;}

    public void setDEventDate(Date obDate)
    {
        this.dEventDate = obDate;
    }

    public Tournament getTTournament()
    {
        return this.tTournament;
    }

    public void setTTournament(Tournament tTournament)
    {
        this.tTournament = tTournament;
    }

    public String getSLocation()
    {
        return this.sLocation;
    }

    public void setSLocation(String sLocation)
    {
        this.sLocation = sLocation;
    }

}
