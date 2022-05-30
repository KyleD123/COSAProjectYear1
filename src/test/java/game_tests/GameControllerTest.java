package game_tests;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import controllers.GameController;
import models.Game;
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
        obTourn = new Tournament();
        obTourn.setsTournamentName("Super Kids Brawling");

        Date startDate = new Date();
        startDate.setTime(startDate.getTime() + (1000*60*60*24));

        obTourn.setdStartDate(startDate);
        obTourn.setdEndDate(new Date(startDate.getTime() + (1000*60*60*24)));

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
        obGame.setDEventDate(obTourn.getdStartDate());
        obGame.setTHomeTeam(obTeam1);
        obGame.setTAwayTeam(obTeam2);
        obGame.setTTournament(obTourn);
        obGame.setSLocation("ACT Centre Rink B");

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
    }

    /**
     * INITIAL TEST
     *
     * MAKING SURE IF THE DATABASE ADDED A VALID SINGLE GAME
     */
    @Test
    public void testCreateValidOneGame()
    {
        assertSame("Sucessfully added game", gameController.createGame(obGame), true);
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
        Team obTeam3 = new Team();
        obTeam3.setTeamName("Regina Bruhlers");
        obTeam3.setCoachName("Coach Kyle");
        obTeam3.setTeamID(3);

        Team obTeam4 = new Team();
        obTeam4.setTeamName("Swift Current Mawlers");
        obTeam4.setCoachName("Coach Kale");
        obTeam4.setTeamID(4);

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
        obGame2.setDEventDate(endDate);
        obGame2.setTHomeTeam(obTeam3);
        obGame2.setTAwayTeam(obTeam4);
        obGame2.setTTournament(obTourn);
        obGame2.setSLocation("Archibald Rink A");

        assertSame("Sucessfully added game", gameController.createGame(obGame), true);
        assertSame("Sucessfully added game", gameController.createGame(obGame2), true);
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
        obGame.setTHomeTeam(obTeam1);
        obGame.setTAwayTeam(obTeam1);

        assertSame("Same home and away team return false", gameController.createGame(obGame), false);

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
        obGame2.setDEventDate(obTourn.getdStartDate());
        obGame2.setTHomeTeam(obTeam1);
        obGame2.setTAwayTeam(obTeam2);
        obGame2.setTTournament(obTourn);
        obGame2.setSLocation("ACT Centre Rink A"); //Instead of ACT Centre Rink B, we changed it to ACT Centre Rink A.

        assertSame("Sucessfully added game", gameController.createGame(obGame), true);
        assertSame("Sucessfully added game", gameController.createGame(obGame2), true);
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
        obGame2.setDEventDate(obGame.getDEventDate());
        obGame2.setTHomeTeam(obTeam3); //ADDING OUR FIRST UNIQUE TEAM
        obGame2.setTAwayTeam(obTeam4);//ADDING OUR SECOND UNIQUE TEAM
        obGame2.setTTournament(obTourn);
        obGame2.setSLocation(obGame.getSLocation());

        assertSame("Sucessfully added game", gameController.createGame(obGame), true);
        assertSame("conflicting schedule return false", gameController.createGame(obGame2), false);
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
    public void testSaveGameWithSameFieldsButDifferentDay() throws SQLException
    {
        Date diffDate = new Date();

        diffDate.setTime(diffDate.getTime() + (1000*60*60*72)); //SETTING THE DATE ONE DAY AFTER TODAY
        obTourn.setsTournamentName("Southeners Bruhmoment");
        obTourn.setdStartDate(diffDate);
        obTourn.setdEndDate(new Date(diffDate.getTime() + (1000*60*60*126)));


        //NOTE THE USAGE OF THE SAME PROPERTIES THROUGH OBGAME'S GETTERS.
        Game obGame2 = new Game();
        obGame2.setDEventDate(new Date(diffDate.getTime() + (1000*60*60*24))); //NOTE THE SETTING OF THE DAY USING THE DIFF DATE
        obGame2.setTHomeTeam(obTeam1);
        obGame2.setTAwayTeam(obTeam2);
        obGame2.setTTournament(obTourn);
        obGame2.setSLocation(obGame.getSLocation());

        assertSame("Sucessfully added game", gameController.createGame(obGame), true);
        assertSame("Sucessfully added game", gameController.isUnique(obGame2), true);
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

        Team obTeam3 = new Team();
        obTeam3.setTeamName("Regina Bruhlers");
        obTeam3.setCoachName("Coach Kyle");
        obTeam3.setTeamID(3);

        Team obTeam4 = new Team();
        obTeam4.setTeamName("Swift Current Mawlers");
        obTeam4.setCoachName("Coach Kale");
        obTeam4.setTeamID(4);

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
        obGame2.setDEventDate(endDate);
        obGame2.setTHomeTeam(obTeam3);
        obGame2.setTAwayTeam(obTeam4);
        obGame2.setTTournament(obTourn);
        obGame2.setSLocation("Archibald Rink A");


        gameController.createGame(obGame);
        gameController.createGame(obGame2);

        assertEquals(gameController.getAllSchedule().size(), 2);

    }


}
