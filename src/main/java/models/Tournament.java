package models;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;



@DatabaseTable(tableName = "tournaments")
public class Tournament implements Serializable
{
    //ano's
    @DatabaseField(generatedId = true, unique = true)
    private int nTournamentID;

    //annotations for tournament name
    @DatabaseField(canBeNull = true)
    @Size(max = 40, min = 0, message="Character length limit exceeded")
    private String sTournamentName;

    //ano's for start date
    @DatabaseField(canBeNull = false)
    @NotNull(message = "Empty Date Entered")
    @Future(message = "Past date entered")
    private Date dStartDate;

    //ano's for end data (MAKE A CUSTOM VALIDATION FOR DAY AFTER THE START DATE)
    @DatabaseField(canBeNull = false)
    @NotNull(message = "Empty Date Entered")
    @Future(message = "Past date entered")
//    @AfterStartDate(message = "Date must be after one day after the start date")
    private Date dEndDate;

    public Tournament()
    {

    }

    public int getnTournamentID() { return this.nTournamentID; }

    public void setnTournamentID(int nId)
    {
        this.nTournamentID = nId;
    }

    public String getsTournamentName()
    {
        return sTournamentName;
    }

    public void setsTournamentName(String sName)
    {
        this.sTournamentName = sName;
    }

    public Date getdStartDate()
    {
        return this.dStartDate;
    }

    public void setdStartDate(Date start)
    {
        this.dStartDate = start;
    }

    public Date getdEndDate()
    {
        return this.dEndDate;
    }

    public void setdEndDate(Date end)
    {
        this.dEndDate = end;
    }

    @Override
    public String toString()
    {
        return getnTournamentID() + "";
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tournament that = (Tournament) o;
        return nTournamentID == that.nTournamentID;
    }

}
