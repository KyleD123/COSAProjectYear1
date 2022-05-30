package models;


import com.j256.ormlite.support.ConnectionSource;

/**
 * Purpose: My Roster Functions
 * @author team green
 */
public class Roster
{

    public Roster(ConnectionSource obConn)
    {

    }

    /**
     * adds player to team
     * @param obPlayer
     * @param obTeam
     * @return a boolean value
     */
    public boolean addPlayerToTeam(Player obPlayer, Team obTeam)
    {
        return true;
    }

    /**
     *
     * @param obPlayer
     * @return
     */
    public boolean removePlayerFromTeam(Player obPlayer)
    {
        return true;
    }


}
