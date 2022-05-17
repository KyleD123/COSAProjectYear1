package controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import models.Player;

import java.sql.Connection;
import java.sql.SQLException;

public class PlayerController {
    private Dao<Player, Long> repo;

    public PlayerController(ConnectionSource databaseConn) {
        try {
            this.repo = DaoManager.createDao(databaseConn, Player.class);
            repo.setAutoCommit(databaseConn.getReadWriteConnection("players"), true);
            TableUtils.createTableIfNotExists(databaseConn, Player.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean createPlayer(Player obPlayer) {
        if (verifyUnique(obPlayer)) {
            try {
                int result = repo.create(obPlayer);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean verifyUnique(Player obPlayer) {

        return true;
    }

    public boolean modifyPlayer(Player obPlayer) {
        if (verifyUnique(obPlayer)) {
            try {
                int result = repo.update(obPlayer);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
