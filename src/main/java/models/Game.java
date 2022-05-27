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

    @DatabaseField(canBeNull = false, dataType = DataType.SERIALIZABLE)
    private Team tAwayTeam;

    @DatabaseField(canBeNull = false, dataType = DataType.SERIALIZABLE)
    private Team tHomeTeam;

    @DatabaseField(canBeNull = false)
    @NotNull(message = "There must be a Event Date!")
    private Date dEventDate;

    @DatabaseField(canBeNull = false, dataType = DataType.SERIALIZABLE)
    @NotNull(message = "There must be a tournament tied to this schedule")
    private Tournament tTournament;

    @DatabaseField(canBeNull = false)
    @NotNull(message = "Provide a location and rink!")
    private String sLocation;

    public Game()
    {
    }

    public long getlGameID()
    {
        return this.lGameID;
    }

    public void setlGameID(long lGameID)
    {
        this.lGameID = lGameID;
    }

    public Team gettAwayTeam() {
        return tAwayTeam;
    }

    public void settAwayTeam(Team obTeam)
    {
        this.tAwayTeam = obTeam;
    }

    public Team gettHomeTeam()
    {
        return tHomeTeam;
    }

    public void settHomeTeam(Team obTeam)
    {
        this.tHomeTeam = obTeam;
    }

    public Date getdEventDate() {return this.dEventDate;}

    public void setdEventDate(Date obDate)
    {
        this.dEventDate = obDate;
    }

    public Tournament gettTournament()
    {
        return this.tTournament;
    }

    public void settTournament(Tournament tTournament)
    {
        this.tTournament = tTournament;
    }

    public String getsLocation()
    {
        return this.sLocation;
    }

    public void setsLocation(String sLocation)
    {
        this.sLocation = sLocation;
    }

}
