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
    @NotEmpty(message = "Team Name is Required")
    private String sTeamName;

    //Coach Name - In format "FirstName LastName"
    @DatabaseField(canBeNull = false)
    @Size(max = 30, message = "Coach Name Too Long")
    @NotEmpty(message = "Coach Name is Required")
    private String sCoachName;

    public Team(int nTeamID, String sTeamName, String sCoachName)
    {
        this.nTeamID = nTeamID;
        this.sTeamName = sTeamName;
        this.sCoachName = sCoachName;

        //Fill More
    }

    //Getters
    public int getTeamID() { return nTeamID; }

    public String getTeamName() { return sTeamName; }

    public String getCoachName() { return sCoachName; }

    //Setters


}
