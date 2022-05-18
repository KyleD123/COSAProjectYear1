package controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import models.Team;
import models.TeamValidator;

import java.sql.SQLException;
import java.util.ArrayList;

public class TeamController
{
    private Dao<Team, Long> repo;
    //private ValidationHelper vh = new ValidationHelper(); //TeamValidator
    private TeamValidator tV = new TeamValidator();

    public TeamController(ConnectionSource databaseConn)
    {
        try
        {
            this.repo = DaoManager.createDao(databaseConn, Team.class);
            repo.setAutoCommit(databaseConn.getReadWriteConnection("teams"), true);
            TableUtils.createTableIfNotExists(databaseConn, Team.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean createTeam(Team obTeam)
    {
        boolean isCreated = false;
        try{
            if (tV.isTeamValid(obTeam) && 0 == repo.queryForEq("Team",obTeam.getTeamName()).size()) {
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
            if(tV.isTeamValid(obTeam))
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
