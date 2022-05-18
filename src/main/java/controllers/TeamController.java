package controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import models.Team;
import models.TeamValidator;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class TeamController
{
    private Dao<Team, Long> repo;
    private TeamValidator tV = new TeamValidator();

    public TeamController(ConnectionSource databaseConn)
    {
        try
        {
            this.repo = DaoManager.createDao(databaseConn, Team.class);
            repo.setAutoCommit(databaseConn.getReadWriteConnection("tournaments"), true);
            TableUtils.createTableIfNotExists(databaseConn, Team.class);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public boolean createTeam(Team obTeam)
    {
        boolean isCreated = false; // false indicates not created
        try
        {
            if (tV.isTeamValid(obTeam) && repo.queryForEq("startDate", obTeam.getTeamName()).size() == 0)
            {
                int result = repo.create(obTeam);

                isCreated = true;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return isCreated;
    }

    public boolean modifyTeam(Team obTeam)
    {
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

    /**
     * For future releases, to edit the last Tournament entry, this queries all of the Tournament entries and returns that list of Tournaments
     * @return
     */
    public List<Team> getAllTeam()
    {
        List<Team> obReturn = new ArrayList<>();
        try
        {
            obReturn = repo.queryForAll();
        }

        catch (SQLException e)
        {

        }

        return obReturn;
    }

    public Team getLastEntry()
    {
        return getAllTeam().get(getAllTeam().size() - 1);
    }

    public Team getTournamentById(Long id)
    {
        Team obReturn;

        try {
            obReturn = repo.queryForId((long) id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return obReturn;

    }




}
