package controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import models.Tournament;
import models.TournamentValidator;

import java.sql.SQLException;

public class TournamentController
{
   private Dao<Tournament, Long> repo;
//   private ValidationHelper vh = new ValidationHelper();
    private TournamentValidator tV = new TournamentValidator();

    public TournamentController(ConnectionSource databaseConn)
    {
        try
        {
            this.repo = DaoManager.createDao(databaseConn, Tournament.class);
            repo.setAutoCommit(databaseConn.getReadWriteConnection("tournaments"), true);
            TableUtils.createTableIfNotExists(databaseConn, Tournament.class);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public boolean createTournament(Tournament obTourn)
    {
        boolean isCreated = false; // false indicates not created
        try
        {
            if(tV.isTournValid(obTourn))
            {
                int result = repo.create(obTourn);
                isCreated = true;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return isCreated;
    }

    public boolean modifyTournament(Tournament obTourn)
    {
        boolean isCreated = false; // false indicates not created
        try
        {
            if(tV.isTournValid(obTourn))
            {
                int result = repo.update(obTourn);
                isCreated = true;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return isCreated;
    }





}
