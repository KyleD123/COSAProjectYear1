package controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import models.Team;
import java.util.ArrayList;

public class TeamController
{
    private Dao<Team, Long> repo;
//    private ValidationHelper vh = new ValidationHelper();

    public TeamController(ConnectionSource databaseConn)
    {

    }

    public boolean createTeam(Team obTeam)
    {
        //return null;
        return true;
    }

    public boolean modifyTeamName(Team obTeam) {
        //return null;
        return true;
    }

    public boolean verifyUniqueTeamName(Team obTeam) {
        return false;
    }

    public Team getTeam(int teamID) {
        return null;
    }

    private ArrayList<Team> getTeams() {
        return null;
    }

    public boolean isSuccesfullyAdded() {
        return false;
    }

    public void sendTeam(Team obTeam)
    {

    }

    public void initiateORMConnection()
    {

    }


 }
