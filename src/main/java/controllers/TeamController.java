package controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import models.Team;

import java.sql.SQLException;
import java.util.ArrayList;

public class TeamController
{
    private Dao<Team, Long> repo;
    private ValidationHelper vh = new ValidationHelper();

    public TeamController(ConnectionSource databaseConn)
    {

    }

    public boolean createTeam(Team obTeam)
    {
        boolean isCreated = false;
        try{
            if (vh.isValid(obTeam) && 0 == repo.queryForEq("Team",obTeam.getTeamName()).size()) {
                int result = repo.create(obTeam);
                isCreated = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {

        }

        return isCreated;
    }

    public boolean modifyTeamName(Team obTeam) {
        boolean isCreated = false; // false indicates not created
        try
        {
            if(vh.isValid(obTeam))
            {
                int result = repo.update(obTeam);
                isCreated = true;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return isCreated;
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


 }
