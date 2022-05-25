package tournament_tests;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import controllers.TournamentController;
import models.Tournament;
import models.TournamentValidator;

import java.sql.SQLException;
import java.util.Date;

public class DatabaseConnectionTest
{
    private static TournamentController obTourn;
    private static TournamentValidator tournValid;
    public static void main(String[] args)
    {
        ConnectionSource dbConn = null;
        try {
            dbConn = new JdbcPooledConnectionSource("jdbc:sqlite:tournaments.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        obTourn = new TournamentController(dbConn);
        tournValid = new TournamentValidator();

        addTournament();
    }

    public static void addTournament()
    {
        Tournament obStuff = new Tournament();

        Date today = new Date();
        Date tommorow = new Date();
        tommorow.setTime(today.getTime() + (1000*60*60*24));
        today.setTime(today.getTime() + (1000*60*60*24));

        obStuff.setdStartDate(today);
        obStuff.setdEndDate(tommorow);

        obStuff.setsTournamentName("Brycelandia");

        System.out.println(tournValid.isTournValid(obStuff));
        if (tournValid.isTournValid(obStuff))
        {
            obTourn.createTournament(obStuff);
        }
    }
}
