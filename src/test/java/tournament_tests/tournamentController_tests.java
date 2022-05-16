package tournament_tests;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import controllers.TournamentController;
import models.Tournament;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Date;
import static org.junit.Assert.*;

public class tournamentController_tests
{
    private static TournamentController tournControl;

    @Before
    public void setUpMockDb()
    {
        try{
            tournControl = new TournamentController(new JdbcConnectionSource("jdbc:h2:mem:myDb"));
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    //Create tests

    @Test
    public void testCreateValidTournament()
    {
        Tournament obTourn = new Tournament();
        Date start = new Date();
        obTourn.setStartDate(start);
        Date end = new Date();
        end.setTime(start.getTime() + (1000*60*60*24));
        obTourn.setEndDate(end);
        obTourn.setTournamentName("");

        try{
            assertSame("Successfully added tournament", tournControl.createTournament(obTourn), true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Test
    public void testCreateInvalidTournament()
    {
        Tournament obTourn = new Tournament();
        Date start = new Date();
        obTourn.setStartDate(start);
        Date end = new Date();
        end.setTime(start.getTime() - (1000*60*60*24));
        obTourn.setEndDate(end);
        obTourn.setTournamentName("");

        try
        {
            assertSame("Successfully added tournament", tournControl.createTournament(obTourn), false);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }


    @Test
    public void testTournamentWithSameDates()
    {
        Tournament obTourn = new Tournament();
        Date start = new Date();
        obTourn.setStartDate(start);
        Date end = new Date();
        end.setTime(start.getTime() + (1000*60*60*24));
        obTourn.setEndDate(end);
        obTourn.setTournamentName("");

        Tournament obTourn2 = new Tournament();
        Date start2 = new Date();
        obTourn.setStartDate(start);
        Date end2 = new Date();
        end.setTime(start.getTime() + (1000*60*60*24));
        obTourn.setEndDate(end);
        obTourn.setTournamentName("Tester Tournament");

        try{
            assertSame("Successfully added tournament", tournControl.createTournament(obTourn), true);
            assertSame("Tournaments with the same start and/or end date exist", tournControl.createTournament(obTourn2), false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //Modify functionality

    @Test
    public void testEditValidTournament()
    {
        Tournament obTourn = new Tournament();
        Date start = new Date();
        obTourn.setStartDate(start);
        Date end = new Date();
        end.setTime(start.getTime() + (1000*60*60*24));
        obTourn.setEndDate(end);
        obTourn.setTournamentName("");

        try{
            assertSame("Successfully overwritten", tournControl.modifyTournament(obTourn), true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    @Test
    public void testEditInvalidTournament()
    {
        Tournament obTourn = new Tournament();
        obTourn.setStartDate(null);
        obTourn.setEndDate(null);
        obTourn.setTournamentName("");

        try{
            assertSame("Error entry  not overwritten", tournControl.modifyTournament(obTourn), false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }



}
