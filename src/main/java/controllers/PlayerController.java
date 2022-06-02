package controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import models.Player;
import models.PlayerValidator;
import models.Team;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerController {
    private Dao<Player, Long> repo;
    private PlayerValidator obValid;

    public PlayerController(ConnectionSource databaseConn)
    {
        try {
            this.repo = DaoManager.createDao(databaseConn, Player.class);
            repo.setAutoCommit(databaseConn.getReadWriteConnection("players"), true);
            TableUtils.createTableIfNotExists(databaseConn, Player.class);

            obValid = new PlayerValidator();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean createPlayer(Player obPlayer)
    {
        boolean sent = false;
        try
        {
            if (obValid.isValid(obPlayer) && repo.queryForMatching(obPlayer).isEmpty() ) {
                int nResult = repo.create(obPlayer);
                sent = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sent;
    }

    public boolean modifyPlayer(Player obPlayer) throws SQLException {
        if (obValid.isValid(obPlayer) && repo.queryForMatching(obPlayer).isEmpty())
        {
            try {
                int nResult = repo.update(obPlayer);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public List<Player> getPlayersWithoutTeams()
    {
        List<Player> obReturn = new ArrayList<>();
        try
        {
            obReturn = repo.query(repo.queryBuilder().where().isNull("obTeam_id").prepare());

        }

        catch (SQLException e)
        {

        }

        return obReturn;
    }

    public List<Player> getAllPlayersByTeam(Team obTeam)
    {
        List<Player> obReturn = new ArrayList<>();
        try
        {
            obReturn = repo.queryForEq("obTeam_id", obTeam.getTeamID());
        }

        catch (SQLException e)
        {

        }

        return obReturn;
    }

    public List<Player> getAllPlayers()
    {
        List<Player> obReturn = new ArrayList<>();
        try
        {
            obReturn = repo.queryForAll();
        }

        catch (SQLException e)
        {

        }

        return obReturn;
    }

    public Player getPlayerByName(String sName)
    {
        List<Player> obReference = getAllPlayers().stream().filter(x -> (x.getsFirstName() + " " + x.getsLastName()).equals(sName)).collect(Collectors.toList());
        return obReference.get(obReference.size() - 1);
    }

    public List<Player> getPlayerByNumber(int sNum)
    {
        return getAllPlayers().stream().filter(x -> x.getnPlayerNumber() == sNum).collect(Collectors.toList());
    }

}
