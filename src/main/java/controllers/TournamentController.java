package controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import models.Tournament;

public class TournamentController
{
   private Dao<Tournament, Long> repo;
//   private ValidationHelper vh = new ValidationHelper();

    public TournamentController(ConnectionSource databaseConn)
    {

    }

    public boolean createTournament(Tournament obTourn)
    {
        return true;
    }

    public boolean verifyUnique(Tournament obTourn)
    {
        return true;
    }

    public boolean modifyTournament(Tournament obTourn)
    {
        return true;
    }





}
