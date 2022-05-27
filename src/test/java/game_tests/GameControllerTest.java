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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Purpose: The purpose of this test is to verify that the different functionalities of the GameController are working as expected based
 *          on our Acceptance Test Procedure Document.
 *
 * @author Team Green
 */
public class GameControllerTest
{

    private static GameController gameController;
    private static Tournament obTourn;

    private static Game obGame;
    private static Team obTeam1, obTeam2;

    /**
     * Before every test, it creates a mock database that we can use to verify if the adding and referencing over to our database works as expected.
     * To help compare two Game objects in the database correctly, it clears the table to make a brand new DB from scratch again.
     */
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

    /**
     * Before starting any test, this method creates a valid Game. It firstly initiates the required Tournament object (with its start and end date),
     * initiates two different teams, and initiate a game based on the given initiated Tournament and two teams object.
     */
    @Before
    public void setUpValidTournament()
    {
        obTourn = new Tournament();
        obTourn.setsTournamentName("Super Kids Brawling");

        Date startDate = new Date();
        startDate.setHours(6);
        startDate.setMinutes(0);
        startDate.setSeconds(0);
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
        obTeam2.setTeamName("Regina Trashers");
        obTeam2.setCoachName("Coach Tass");
        obTeam2.setTeamID(2);

        //Setting up a game here
        obGame = new Game();
        //initiating methods
        obGame.setdEventDate(obTourn.getdStartDate());
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
     * NORMAL TEST
     *
     * ADDING TWO DIFFERENT GAMES WITH DIFFERENT FIELDS. BASICALLY, ADDING A TWO UNIQUE GAME OBJECT TO SEE IF THE DATABASE
     * WOULD ACCEPT TWO UNIQUE ENTRIES.
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
        startDate.setHours(6);
        startDate.setMinutes(0);
        startDate.setSeconds(0);
        startDate.setTime(startDate.getTime());
        startDate.setTime(startDate.getTime() + (1000*60*60*48));
        Date endDate = new Date();

        endDate.setTime(startDate.getTime() + (1000*60*60*72));
        obTourn.setdStartDate(startDate);
        obTourn.setdEndDate(endDate);

        Game obGame2 = new Game();
        obGame2.setdEventDate(obTourn.getdStartDate());
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
     * THIS METHOD VERIFIES IF THE DATABASE REJECTS ADDING A GAME TO THE DATABASE IF GAME'S ANNOTATIONS DETECTS IF THE GAME HAS THE SAME
     * HOME AND AWAY TEAM.
     */
    @Test
    public void testSaveGameWithSameHomeAwayTeam()
    {
        Team obTeam1 = new Team();
        obTeam1.setTeamName("Saskatoonian Bruhlers");
        obTeam1.setCoachName("Coach Levi");
        obTeam1.setTeamID(1);

        //THIS PART SETS UP HOME AND AWAY TEAM FOR THE SASKATOONIAN BRUHLERS. HOWEVER, THE CUSTOM ANNOTATIONS FOR THE GAME OBJECT
        //WILL DETECT THAT THE HOME AND AWAY FIELDS IN THE GAME OBJECT ARE IDENTICAL AND WILL THROW OUT AN ERROR INTO THE
        //GAMEVALIDATOR'S ISVALID METHOD.
        obGame.settHomeTeam(obTeam1);
        obGame.settAwayTeam(obTeam1);

        assertSame("Same home and away team return false", gameController.scheduleGame(obGame), false);

    }

    /**
     * NORMAL TEST
     *
     * THIS METHOD VERIFIES THAT ADDING A NEW GAME OBJECT ENTRY TO THE DATABASE THAT HAS THE SAME FIELDS FOR EVENT DATE,
     * START TIME, END TIME, TOURNAMENT, AND EVEN SAME HOME AND AWAY TEAM, BUT INSTEAD OF THE SAME LOCATION, IT IS GOING
     * TO BE SET TO A SECOND LOCATION.
     *
     * THIS SHOULD PASS AS THERE CAN BE GAME RUNNING AT THE SAME TIME AS LONG THEIR LOCATION IS SEPERATE
     */
    @Test
    public void testSaveGameWithSameDateTimeTournamentButDifferentLocation()
    {
        Game obGame2 = new Game();
        obGame2.setdEventDate(obTourn.getdStartDate());
        obGame2.settHomeTeam(obTeam1);
        obGame2.settAwayTeam(obTeam2);
        obGame2.settTournament(obTourn);
        obGame2.setsLocation("ACT Centre Rink A"); //Instead of ACT Centre Rink B, we changed it to ACT Centre Rink A.

        assertSame("Sucessfully added game", gameController.scheduleGame(obGame), true);
        assertSame("Sucessfully added game", gameController.scheduleGame(obGame2), true);
    }

    /**
     * EXCEPTION TEST
     *
     * THIS TEST VERIFIES IF THE DB WILL HAPPILY REJECT ADDING A GAME OBJECT THAT HAS THE SAME FIELD PROPERTIES
     * SUCH AS ITS EVENT DATE, START TIME , END TIME, TOURNAMENT, AND THEIR LOCATION, BUT HAS TWO UNIQUE HOME AND
     * AWAY TEAM.
     *
     * BASICALLY, IF YOU WANT TO SCHEDULE A NEW GAME WITH TWO NEW TEAMS, BUT YOU ARE TRYING TO SCHEDULE IT ON THE SAME DATE,
     * OF THE SAME TIME, IN A SAME LOCATION, WITHIN THE SAME TOURNAMENT. THAT WILL BE REJECTED.
     */
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

        //NOTE THE USAGE OF THE SAME PROPERTIES THROUGH OBGAME'S GETTERS.
        Game obGame2 = new Game();
        obGame2.setdEventDate(obGame.getdEventDate());
        obGame2.settHomeTeam(obTeam3); //ADDING OUR FIRST UNIQUE TEAM
        obGame2.settAwayTeam(obTeam4);//ADDING OUR SECOND UNIQUE TEAM
        obGame2.settTournament(obTourn);
        obGame2.setsLocation(obGame.getsLocation());

        assertSame("Sucessfully added game", gameController.scheduleGame(obGame), true);
        assertSame("conflicting schedule return false", gameController.scheduleGame(obGame2), false);
    }

    /**
     * EXCEPTION
     *
     * THIS METHOD VERIFIES IF THE DB WILL GRACEFULLY REJECTS ADDING A GAME OBJECT THAT HAS THE SAME FIELD PROPERTIES
     * SUCH AS ITS EVENT DATE, START TIME , END TIME, TOURNAMENT, LOCATION, AND EVEN THEIR HOME AND AWAY TEAM WITH THE PREVIOUS ENTRY.
     * BUT HAS DIFFERENT TOURNAMENT.
     *
     * THE IDEA IS THAT FACILITATOR CANNOT SCHEDULE A GAME THAT IS CONFLICTING WITH ANOTHER GAME NO MATTER WHAT THEIR HOME AND AWAY TEAM IS, OR THEIR
     * CONTRASTING TOURNAMENT. THEY CAN DO IT HOWEVER IF THEY CHOOSE A DIFFERENT LOCATION
     */
    @Test
    public void testSaveGameWithAllSameFieldsExceptTournament()
    {
        Tournament obTourn2 = new Tournament();
        obTourn2.setsTournamentName("Super Kids Stuff");
        obTourn2.setnTournamentID(1);
        obTourn2.setdStartDate(obTourn.getdStartDate());
        obTourn2.setdEndDate(obTourn.getdEndDate());

        //NOTE THE USAGE OF THE SAME PROPERTIES THROUGH OBGAME'S GETTERS.
        Game obGame2 = new Game();
        obGame2.setdEventDate(obGame.getdEventDate());
        obGame2.settHomeTeam(obTeam1);
        obGame2.settAwayTeam(obTeam2);
        obGame2.settTournament(obTourn2); //INSTEAD OF THE ORIGINAL TOURNAMENT, WE ARE CREATING A NEW TOURNAMENT.
        obGame2.setsLocation(obGame.getsLocation());

        assertSame("Sucessfully added game", gameController.scheduleGame(obGame), true);
        assertSame("conflicting schedule return false", gameController.scheduleGame(obGame2), false);
    }


    /**
     * NORMAL TEST
     *
     * THIS VERIFIES IF THE DATABASE HAPPILY ACCEPTS A NEW GAME OBJECTS THAT HAVE SAME PROPERTIES AS THE EXISITING
     * GAME ENTRY, BUT THE DIFFERENCE IS THAT THIS GAME IS SET ON THE NEXT DAY.
     *
     * THIS IS CONSIDERED ACCEPTABLE AS THEY CAN HAVE A GAME SCHEDULED ON THE SAME TIME, ON THE SAME DATE, ON THE SAME LOCATION, WITHIN
     * THEIR SAME TOURNAMENT AS LONG THEY ARE SCHEDULED EITHER THE NEXT DAY OR THE DAY BEFORE.
     */
    @Test
    public void testSaveGameWithSameFieldsButDifferentDay()
    {
        Date diffDate = new Date();
        diffDate.setHours(6);
        diffDate.setMinutes(0);
        diffDate.setSeconds(0);
        diffDate.setTime(diffDate.getTime() + (1000*60*60*48)); //SETTING THE DATE ONE DAY AFTER TODAY

        //NOTE THE USAGE OF THE SAME PROPERTIES THROUGH OBGAME'S GETTERS.
        Game obGame2 = new Game();
        obGame2.setdEventDate(diffDate); //NOTE THE SETTING OF THE DAY USING THE DIFF DATE
        obGame2.settHomeTeam(obTeam1);
        obGame2.settAwayTeam(obTeam2);
        obGame2.settTournament(obTourn);
        obGame2.setsLocation(obGame.getsLocation());

        assertSame("Sucessfully added game", gameController.scheduleGame(obGame), true);
        assertSame("Sucessfully added game", gameController.scheduleGame(obGame2), true);
    }

    /**
     * NORMAL
     *
     * THIS METHOD VERIFIES THE GETALLSCHEDULE LIST METHOD OF THE GAME CONTROLLER IF THE DATABASE HAS ACCEPTED TWO VALID
     * GAMES AND WHEN WE REQUEST TO GET ALL OF THE LIST, THE SIZE OF THAT LIST SHOULD BE 2.
     *
     * @throws SQLException - THIS THROWS A SQL EXCEPTION SINCE WE ARE USING SQL TO QUERY ALL OF THE EXISTING DATABASE.
     */
    @Test
    public void testSaveGameList() throws SQLException
    {
        Date diffDate = new Date();
        diffDate.setHours(6);
        diffDate.setMinutes(0);
        diffDate.setSeconds(0);
        diffDate.setTime(diffDate.getTime() + (1000*60*60*48));

        Game obGame2 = new Game();
        obGame2.setdEventDate(diffDate);
        obGame2.settHomeTeam(obTeam1);
        obGame2.settAwayTeam(obTeam2);
        obGame2.settTournament(obTourn);
        obGame2.setsLocation(obGame.getsLocation());

        gameController.scheduleGame(obGame);
        gameController.scheduleGame(obGame2);

        assertEquals(gameController.getAllSchedule().size(), 2);

    }


}
