package models;

import java.io.Serializable;
import java.util.Date;



//@DatabaseTable(tableName = "tournaments")
public class Tournament implements Serializable
{
    //ano's

    private int tournamentId;

    //annotations for tournament name

    private String tournamentName;

    //ano's for start date
    private Date startDate;

    //ano's for end data
    private Date endDate;


    public Tournament()
    {

    }

    public int getTournamentId()
    {

        return this.tournamentId;
    }

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
