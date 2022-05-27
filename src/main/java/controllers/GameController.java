package controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import models.Game;
import models.GameValidator;
import models.Tournament;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameController
{
    public Dao<Game, Long> repo;
    private GameValidator obValid;

    public GameController(ConnectionSource databaseConn) throws SQLException
    {
        try
        {
            this.repo = DaoManager.createDao(databaseConn, Game.class);
            repo.setAutoCommit(databaseConn.getReadWriteConnection("games"), true);
            TableUtils.createTableIfNotExists(databaseConn, Game.class);

            obValid = new GameValidator();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public boolean scheduleGame(Game obGame)
    {
        boolean isScheduled = false;
        try
        {
            if (obValid.isGameValid(obGame) && isUnique(obGame))
            {
                int result = repo.create(obGame);
                isScheduled = true;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return isScheduled;

    }

    public boolean isUnique(Game obGame) throws SQLException
    {

        //Firstly, get all of the list of the games and filter them out by the Game's tournament object
        List<Game> obRef = getAllSchedule();

        //Check if there's a date equals to the obGames date. If there's no entry for this tourmanent about the request date, that means that date has not been filled up yet.
        for (int i = 0; i <= obRef.size() - 1; i++)
        {
            if (obGame.getdEventDate().isAfter(obRef.get(i).getdEventDate().plusHours(1).plusMinutes(59)))
            {
                    if (obRef.get(i).gettTournament().equals(obGame.gettTournament()))
                    {
                        if (obRef.get(i).getsLocation().equals(obGame.getsLocation()))
                        {
                            return false;
                        }
                    }
            }
        }

        return true;
    }

    public List<Game> getAllSchedule() throws SQLException
    {
       return repo.queryForAll();
    }

    public List<Game> getAllScheduleByTournament(Tournament obTourn) throws SQLException
    {
       return repo.queryForAll().stream().filter(x -> x.gettTournament().equals(obTourn)).collect(Collectors.toList());
    }


}
