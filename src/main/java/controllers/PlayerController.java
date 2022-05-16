package controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import models.Player;

import java.sql.Connection;

public class PlayerController {
    private Dao<Player, Long> repo;

    public PlayerController(ConnectionSource databaseConn) {

    }

    public boolean createPlayer(Player obPlayer) {
        return true;
    }

    public boolean verifyUnique(Player obPlayer) {
        return true;
    }

    public boolean modifyPlayer(Player obPlayer) {
        return true;
    }
}
