package tournament_tests;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import controllers.TournamentController;
import models.Tournament;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class TournamentControllerTests
{

    private static TournamentController tournControl;

    @Before
    public void setUpMockDb()
    {
        try
        {
            ConnectionSource obConn = new JdbcConnectionSource("jdbc:h2:mem:myDb");
            tournControl = new TournamentController(obConn);
            TableUtils.clearTable(obConn, Tournament.class);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    //CREATE TOURNAMENT TEST

    @Test
    public void testCreateValidTournament()
    {
        Tournament obTourn = new Tournament();
        Date start = new Date();
        start.setTime(start.getTime() + + (1000*60*60*24));

        obTourn.setdStartDate(start);
        Date end = new Date();
        end.setTime(start.getTime() + (1000*60*60*24));
        obTourn.setdEndDate(end);
        obTourn.setsTournamentName("");

        assertSame("Successfully added tournament", tournControl.createTournament(obTourn), true);


    }

    @Test
    public void testCreateInvalidTournament()
    {
        Tournament obTourn = new Tournament();
        Date start = new Date();
        obTourn.setdStartDate(start);
        Date end = new Date();
        end.setTime(start.getTime() - (1000*60*60*24));
        obTourn.setdEndDate(end);

        assertFalse("Successfully added tournament", tournControl.createTournament(obTourn));


    }


    @Test
    public void testTournamentWithSameDates()
    {
        Tournament obTourn = new Tournament();
        Date start = new Date();

        start.setTime(start.getTime() +  (1000*60*60*48));
        obTourn.setdStartDate(start);

        Date end = new Date();
        end.setTime(start.getTime() + (1000*60*60*72));
        obTourn.setdEndDate(end);
        obTourn.setsTournamentName("");

        Tournament obTourn2 = new Tournament();
        Date start2 = new Date();
        start2.setTime(start2.getTime() + + (1000*60*60*72));
        obTourn2.setdStartDate(start2);

        Date end2 = new Date();
        end.setTime(start2.getTime() + (1000*60*60*127));
        obTourn2.setdEndDate(end2);
        obTourn2.setsTournamentName("Tester Tournament");

        assertSame("Successfully added tournament", tournControl.createTournament(obTourn), true);
        assertSame("Tournaments with the same start and/or end date exist", tournControl.createTournament(obTourn2), false);

    }

    //MODIFY TOURNAMENT TOURNAMENT

    @Test
    public void testEditValidTournament()
    {
        Tournament obTourn = new Tournament();
        Date start = new Date();
        start.setTime(start.getTime() + + (1000*60*60*24));
        obTourn.setdStartDate(start);

        Date end = new Date();
        end.setTime(start.getTime() + (1000*60*60*48));
        obTourn.setdEndDate(end);
        obTourn.setsTournamentName("");

        assertTrue( "Successfully overwritten!", tournControl.modifyTournament(obTourn));


    }

    @Test
    public void testEditInvalidTournament()
    {
        Tournament obTourn = new Tournament();
        obTourn.setdStartDate(null);
        obTourn.setdEndDate(null);
        obTourn.setsTournamentName("");

        assertFalse("Error entry  not overwritten", tournControl.modifyTournament(obTourn));

    }


    @Test
    public void testCreateTournamentList()
    {
        Tournament obTourn1 = new Tournament();
        Date start = new Date();
        start.setTime(start.getTime() + + (1000*60*60*48));
        obTourn1.setdStartDate(start);
        Date end = new Date();

        end.setTime(start.getTime() + (1000*60*60*72));
        obTourn1.setdEndDate(end);
        obTourn1.setsTournamentName("");

        Tournament obTourn2 = new Tournament();
        Date start2 = new Date();
        start2.setTime(start2.getTime() + (1000*60*60*96));
        obTourn2.setdStartDate(start2);

        Date end2 = new Date();
        end2.setTime(start2.getTime() + (1000*60*60*120));
        obTourn2.setdEndDate(end2);
        obTourn2.setsTournamentName("");

        tournControl.createTournament(obTourn1);
        tournControl.createTournament(obTourn2);

        List<Tournament> actualList = tournControl.getAllTournament();

        assertEquals("EQUAL!", actualList.size(), 2);


    }



}
