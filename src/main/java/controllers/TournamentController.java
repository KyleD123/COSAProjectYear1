package controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import models.Tournament;
import models.TournamentValidator;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TournamentController
{
    public Dao<Tournament, Long> repo;
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
            if (tV.isTournValid(obTourn) && repo.queryForEq("startDate", obTourn.getdStartDate()).size() == 0)
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

    /**
     * For future releases, to edit the last Tournament entry, this queries all of the Tournament entries and returns that list of Tournaments
     * @return
     */
    public List<Tournament> getAllTournament()
    {
        List<Tournament> obReturn = new ArrayList<>();
        try
        {
            obReturn = repo.queryForAll();
        }

        catch (SQLException e)
        {

        }

        return obReturn;
    }

    public Tournament getLastEntry()
    {
        return getAllTournament().get(getAllTournament().size() - 1);
    }

    public Tournament getTournamentById(Long id)
    {
        Tournament obReturn;

        try {
            obReturn = repo.queryForId((long) id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return obReturn;

    }




}
