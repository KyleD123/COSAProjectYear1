package game_tests;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import controllers.GameController;
import models.Game;
import controllers.GameController;
import models.Team;
import models.Tournament;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.assertSame;

public class GameControllerTest
{

    private static GameController gameController;
    private static Tournament obTourn;

    private static Game obGame;
    private static Team obTeam1, obTeam2;

    @Before
    public void setUpMockDb()
    {
        try
        {
            ConnectionSource obConn = new JdbcConnectionSource("jdbc:h2:mem:myDb");
            gameController = new GameController(obConn);
            TableUtils.clearTable(obConn, Game.class);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Before
    public void setUpValidTournament()
    {
        obTourn = new Tournament();
        obTourn.setsTournamentName("Super Kids Brawling");

        Date startDate = new Date();
        startDate.setTime(startDate.getTime() + (1000*60*60*24));
        Date endDate = new Date();

        endDate.setTime(startDate.getTime() + (1000*60*60*24));
        obTourn.setdStartDate(startDate);
        obTourn.setdEndDate(endDate);

        obTeam1 = new Team();
        obTeam1.setTeamName("Saskatoonian Bruhlers");
        obTeam1.setCoachName("Coach Levi");
        obTeam1.setTeamID(1);

        obTeam2 = new Team();
        obTeam1.setTeamName("Regina Trashers");
        obTeam1.setCoachName("Coach Tass");
        obTeam1.setTeamID(2);

        //Setting up a game here
        obGame = new Game();
        //initiating methods
        obGame.setdEventDate(obTourn.getdStartDate());
        obGame.setnStartTime(900);
        obGame.setnEndTime(1100);
        obGame.settHomeTeam(obTeam1);
        obGame.settAwayTeam(obTeam2);
        obGame.settTournament(obTourn);
        obGame.setsLocation("ACT Centre Rink B");
    }

    /**
     * INITIAL TEST
     *
     * MAKING SURE IF THE DATABASE ADDED A VALID SINGLE GAME
     */
    @Test
    public void testCreateValidOneGame()
    {
        assertSame("Sucessfully added game", gameController.scheduleGame(obGame), true);
    }

    /**
     * NORMAL
     *
     * ADDING TWO DIFFERENT GAMES WITH DIFFERENT FIELDS
     */
    @Test
    public void testCreateValidDifferentGames()
    {
        Team obTeam1 = new Team();
        obTeam1.setTeamName("Regina Bruhlers");
        obTeam1.setCoachName("Coach Kyle");
        obTeam1.setTeamID(1);

        Team obTeam2 = new Team();
        obTeam1.setTeamName("Swift Current Mawlers");
        obTeam1.setCoachName("Coach Kale");
        obTeam1.setTeamID(2);

        obTourn.setsTournamentName("Southeners Bruhmoment");
        Date startDate = new Date();
        startDate.setTime(startDate.getTime() + (1000*60*60*48));
        Date endDate = new Date();

        endDate.setTime(startDate.getTime() + (1000*60*60*72));
        obTourn.setdStartDate(startDate);
        obTourn.setdEndDate(endDate);

        Game obGame2 = new Game();
        obGame2.setdEventDate(obTourn.getdStartDate());
        obGame2.setnStartTime(1100);
        obGame2.setnEndTime(1300);
        obGame2.settHomeTeam(obTeam1);
        obGame2.settAwayTeam(obTeam2);
        obGame2.settTournament(obTourn);
        obGame2.setsLocation("Archibald Rink A");

        assertSame("Sucessfully added game", gameController.scheduleGame(obGame), true);
        assertSame("Sucessfully added game", gameController.scheduleGame(obGame2), true);
    }

    /**
     * EXCEPTION
     *
     * SAVING GAME WITH SAME HOME AND AWAY TEAM
     */
    @Test
    public void testSaveGameWithSameHomeAwayTeam()
    {
        Team obTeam1 = new Team();
        obTeam1.setTeamName("Saskatoonian Bruhlers");
        obTeam1.setCoachName("Coach Levi");
        obTeam1.setTeamID(1);

        obGame.settHomeTeam(obTeam1);
        obGame.settAwayTeam(obTeam1);

        assertSame("Same home and away team return false", gameController.scheduleGame(obGame), false);

    }

    @Test
    public void testSaveGameWithSameDateButEverythingDifferent()
    {
        Date diffDate = obTourn.getdStartDate();
        diffDate.setTime(diffDate.getTime() + (1000*60*60*48));

        Game obGame2 = new Game();
        obGame2.setdEventDate(diffDate);
        obGame2.setnStartTime(1100);
        obGame2.setnEndTime(1300);
        obGame2.settHomeTeam(obTeam1);
        obGame2.settAwayTeam(obTeam2);
        obGame2.settTournament(obTourn);
        obGame2.setsLocation("Archibald Rink B");

        assertSame("Sucessfully added game", gameController.scheduleGame(obGame), true);
        assertSame("Sucessfully added game", gameController.scheduleGame(obGame2), true);
    }

    @Test
    public void testSaveGameWithSaveDateTimeTournamentButDifferentLocation()
    {
        Game obGame2 = new Game();
        obGame2.setdEventDate(obTourn.getdStartDate());
        obGame2.setnStartTime(1100);
        obGame2.setnEndTime(1300);
        obGame2.settHomeTeam(obTeam1);
        obGame2.settAwayTeam(obTeam2);
        obGame2.settTournament(obTourn);
        obGame2.setsLocation("ACT Centre Rink A");

        assertSame("Sucessfully added game", gameController.scheduleGame(obGame), true);
        assertSame("Sucessfully added game", gameController.scheduleGame(obGame2), true);
    }

    @Test
    public void testSaveGameWithAllSameFieldsExceptHomeAndAwayTeam()
    {

        Team obTeam3 = new Team();
        obTeam1.setTeamName("Calgary Bruhlers");
        obTeam1.setCoachName("Coach Kyle");
        obTeam1.setTeamID(3);

        Team obTeam4 = new Team();
        obTeam1.setTeamName("Saskatoon Mawlers");
        obTeam1.setCoachName("Coach Kale");
        obTeam1.setTeamID(4);

        Game obGame2 = new Game();
        obGame2.setdEventDate(obGame.getdEventDate());
        obGame2.setnStartTime(obGame.getnStartTime());
        obGame2.setnEndTime(obGame.getnEndTime());
        obGame2.settHomeTeam(obTeam3);
        obGame2.settAwayTeam(obTeam4);
        obGame2.settTournament(obTourn);
        obGame2.setsLocation(obGame.getsLocation());

        assertSame("Sucessfully added game", gameController.scheduleGame(obGame), true);
        assertSame("conflicting schedule return false", gameController.scheduleGame(obGame2), false);
    }

    @Test
    public void testSaveGameWithAllSameFieldsExceptTournament()
    {
        Tournament obTourn2 = new Tournament();
        obTourn2.setsTournamentName("Super Kids Brawling");

        Date startDate = new Date();
        startDate.setTime(startDate.getTime() + (1000*60*60*24));
        Date endDate = new Date();

        endDate.setTime(startDate.getTime() + (1000*60*60*24));
        obTourn2.setdStartDate(startDate);
        obTourn2.setdEndDate(endDate);


        Game obGame2 = new Game();
        obGame2.setdEventDate(obGame.getdEventDate());
        obGame2.setnStartTime(obGame.getnStartTime());
        obGame2.setnEndTime(obGame.getnEndTime());
        obGame2.settHomeTeam(obTeam1);
        obGame2.settAwayTeam(obTeam2);
        obGame2.settTournament(obTourn2);
        obGame2.setsLocation(obGame.getsLocation());

        assertSame("Sucessfully added game", gameController.scheduleGame(obGame), true);
        assertSame("conflicting schedule return false", gameController.scheduleGame(obGame2), false);
    }


    @Test
    public void testSaveGameWithSameFieldsButDifferentDay()
    {
        Date addOneDay = obGame.getdEventDate();
        addOneDay.setTime(addOneDay.getTime() + ((1000*60*60*48)));

        Game obGame2 = new Game();
        obGame2.setdEventDate(addOneDay);
        obGame2.setnStartTime(obGame.getnStartTime());
        obGame2.setnEndTime(obGame.getnEndTime());
        obGame2.settHomeTeam(obTeam1);
        obGame2.settAwayTeam(obTeam2);
        obGame2.settTournament(obTourn);
        obGame2.setsLocation(obGame.getsLocation());

        assertSame("Sucessfully added game", gameController.scheduleGame(obGame), true);
        assertSame("Sucessfully added game", gameController.scheduleGame(obGame2), true);
    }


}
