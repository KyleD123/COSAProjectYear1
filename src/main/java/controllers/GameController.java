package controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import models.Game;
import models.GameValidator;
import models.Tournament;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Purpose: This controller helps to send and receive information to the Dao database.
 *          It also validates if the current game is overlapping another game using a method.
 * Author: Project Green
 */
public class GameController
{
    public Dao<Game, Long> repo;
    private GameValidator obValid;
    private TeamController teamController; //This Team Controller is used to reference a team based on their ID for retrieval purposes

    /**
     * This method starts up the database connection to the "games" table and creates if not found.
     * @param databaseConn Connection to eSchedule.db file
     * @throws SQLException
     */
    public GameController(ConnectionSource databaseConn) throws SQLException
    {
        try
        {
            this.repo = DaoManager.createDao(databaseConn, Game.class);
            repo.setAutoCommit(databaseConn.getReadWriteConnection("games"), true);
            TableUtils.createTableIfNotExists(databaseConn, Game.class);

            obValid = new GameValidator();
            teamController = new TeamController(databaseConn);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * This method checks if a game to be created is unique and valid. Creates the entry in the database if true.
     * @param obGame
     * @return
     */
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

    /**
     * This method takes in a game object and generates a list from the database of all games that include either of the two teams.
     * Then filters the list to check if any of those games overlap times with the game to be created.
     * @param obGame The game object to be verified if unique
     * @return Returns true if unique, false if not unique
     * @throws SQLException
     */
    public boolean isUnique(Game obGame) throws SQLException
    {
        List<Game> lstGame =  repo.query(repo.queryBuilder().where().eq("tHomeTeam_id", obGame.getTHomeTeam().getTeamID()).or()
                .eq("tAwayTeam_id", obGame.getTHomeTeam().getTeamID()).prepare());

        lstGame.addAll(repo.query(repo.queryBuilder().where().eq("tAwayTeam_id", obGame.getTAwayTeam().getTeamID()).or()
                .eq("tAwayTeam_id", obGame.getTAwayTeam().getTeamID()).prepare()));

       long overlapGames = lstGame.stream().filter(g -> g.getDEventDate().getTime() < (obGame.getDEventDate().getTime() + (2*60*60*1000)) &&
                (g.getDEventDate().getTime() + (2*60*60*1000)) > obGame.getDEventDate().getTime()).count();


        return overlapGames == 0;
    }

    /**
     * Queries database to get a list of all games within
     * @return List of all games in the database
     * @throws SQLException
     */
    public List<Game> getAllSchedule() throws SQLException
    {
       return repo.queryForAll();
    }

    /**
     * Generates a list of all games within a specific tournament
     * @param obTourn Tournament object used to filter the list
     * @return List of games within the passed in tournament
     * @throws SQLException
     */
    public List<Game> getAllScheduleByTournament(Tournament obTourn) throws SQLException
    {
        List<Game> obList =  repo.queryForAll().stream().filter(x -> x.getTTournament().equals(obTourn)).collect(Collectors.toList());

        //Returns obList only contains the IDs of the Home and Away teams
        //This will set each appropriate team using the team controllers getTeamByID
        obList.stream().forEach(x -> {
            x.setTHomeTeam(teamController.getTeamByID((long) x.getTHomeTeam().getTeamID()));
            x.setTAwayTeam(teamController.getTeamByID((long) x.getTAwayTeam().getTeamID()));
        });

        return obList;
    }


}
