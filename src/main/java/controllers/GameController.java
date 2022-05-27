package controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import models.Game;
import models.GameValidator;
import models.Tournament;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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

    public boolean createGame(Game obGame)
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
        List<Game> checkall = repo.queryForAll();

        List<Game> lstGame =  repo.query(repo.queryBuilder().where().eq("tHomeTeam_id", obGame.gettHomeTeam().getTeamID()).or()
                .eq("tAwayTeam_id", obGame.gettHomeTeam().getTeamID()).prepare());
//        repo.queryBuilder().where().eq("tHomeTeam_id", obGame.gettHomeTeam().getTeamID()).or()
//                .eq("tAwayTeam_id", obGame.gettHomeTeam().getTeamID()).query();

        lstGame.addAll(repo.query(repo.queryBuilder().where().eq("tAwayTeam_id", obGame.gettAwayTeam().getTeamID()).or()
                .eq("tAwayTeam_id", obGame.gettAwayTeam().getTeamID()).prepare()));

       long overlapGames = lstGame.stream().filter(g -> g.getdEventDate().getTime() < (obGame.getdEventDate().getTime() + (2*60*60*1000)) &&
                (g.getdEventDate().getTime() + (2*60*60*1000)) > obGame.getdEventDate().getTime()).count();


        return overlapGames == 0;
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
