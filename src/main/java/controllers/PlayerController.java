package controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import models.Player;
import models.PlayerValidator;

import java.sql.Connection;
import java.sql.SQLException;

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
            if (obValid.isValid(obPlayer)) {
                    int nResult = repo.create(obPlayer);
                    sent = true;
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sent;
    }

//    public boolean verifyUnique(Player obPlayer) {
//        try {
//            int nFirstName = repo.queryForEq("sFirstName", obPlayer.getsFirstName()).size();
//            int nLastName = repo.queryForEq("sLastName", obPlayer.getsLastName()).size();
//            int nPlayerNum = repo.queryForEq("nPlayerNumber", obPlayer.getnPlayerNumber()).size();
//            int nParentInfo = repo.queryForEq("sParentInfo", obPlayer.getsParentInfo()).size();
//            int nEmergencyContact = repo.queryForEq("sEmergencyContact", obPlayer.getsEmergencyContact()).size();
//            int[] naResult = {nFirstName, nLastName, nPlayerNum, nParentInfo, nEmergencyContact};
//
//            for (int nField : naResult) {
//                if (nField == 0) {
//                    return true;
//                }
//            }
//            if (repo.queryForMatching(obPlayer).size() == 0) {
//                return true;
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

    public boolean modifyPlayer(Player obPlayer) {
        if (obValid.isValid(obPlayer))
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
}
