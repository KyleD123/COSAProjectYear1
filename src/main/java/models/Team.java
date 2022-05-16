package models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import javax.validation.constraints.*;

import java.io.Serializable;

@DatabaseTable(tableName = "team")
public class Team implements Serializable {

    //Team ID Field - Auto Increment in the Database
    @DatabaseField(generatedId = true)
    private int nTeamID;

    @DatabaseField(canBeNull = false)
    @Size(max = 40, message = "Team Name Too Long")
    @NotEmpty(message = "Please enter a name")
    private String sTeamName;

    //Coach Name - In format "FirstName LastName"
    @DatabaseField(canBeNull = false)
    @Size(max = 30, message = "Coach Name Too Long")
    @NotEmpty(message = "Coach Name is Required")
    private String sCoachName;

    public Team()
    {

    }

    //Getters
    public int getTeamID() { return nTeamID; }
    public String getTeamName() { return sTeamName; }
    public String getCoachName() { return sCoachName; }

    //Setters
    public void setTeamID(int nTeamID) { this.nTeamID = nTeamID; }
    public void setTeamName(String sTeamName) { this.sTeamName = sTeamName; }
    public void setCoachName(String sCoachName) { this.sCoachName = sCoachName; }

    public void setAllValues(Team team)
    {
        this.setTeamID(team.getTeamID());
        this.setTeamName(team.getTeamName());
        this.setCoachName(team.getCoachName());
    }





}
