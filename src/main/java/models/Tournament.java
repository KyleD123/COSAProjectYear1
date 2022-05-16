package models;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;



@DatabaseTable(tableName = "tournaments")
public class Tournament implements Serializable
{
    //ano's
    @DatabaseField(generatedId = true, unique = true)
    private int tournamentId;

    //annotations for tournament name
    @DatabaseField(canBeNull = true)
    @Size(max = 40, min = 0, message="Character length limit exceeded")
    private String tournamentName;

    //ano's for start date
    @DatabaseField(canBeNull = false)
    @NotNull(message = "Empty Date Entered")
    @Future(message = "Past date entered")
    private Date startDate;

    //ano's for end data (MAKE A CUSTOM VALIDATION FOR DAY AFTER THE START DATE)
    @DatabaseField(canBeNull = false)
    @NotNull(message = "Empty Date Entered")
    @Future(message = "Past date entered")
    private Date endDate;

    public Tournament()
    {

    }

    public int getTournamentId() { return this.tournamentId; }

    public void setTournamentId(int nId)
    {
        this.tournamentId = nId;
    }

    public String getTournamentName()
    {
        return tournamentName;
    }

    public void setTournamentName(String sName)
    {
        this.tournamentName = sName;
    }

    public Date getStartDate()
    {
        return this.startDate;
    }

    public void setStartDate(Date start)
    {
        this.startDate = start;
    }

    public Date getEndDate()
    {
        return this.endDate;
    }

    public void setEndDate(Date end)
    {
        this.endDate = end;
    }


}
