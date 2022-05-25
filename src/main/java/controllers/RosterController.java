package controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import models.Team;

public class RosterController {

    //Change name to Player?
//    private Dao<Team, Long> repo;
//    private RosterValidator rv = new RosterValidator();

    public RosterController(ConnectionSource databaseConn)
    {
//        try
//        {
//            this.repo = DaoManager.createDao(databaseConn, Team.class);
//            repo.setAutoCommit(databaseConn.getReadWriteConnection("teams"), true);
//            TableUtils.createTableIfNotExists(databaseConn, Team.class);
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
    }


//Need a "addPlayerToPosition" Method


}
