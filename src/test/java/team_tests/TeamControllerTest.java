package team_tests;
import com.j256.ormlite.dao.*;
import com.j256.ormlite.jdbc.*;
import com.j256.ormlite.support.*;
import com.j256.ormlite.table.TableUtils;
import org.junit.*;
import org.junit.runner.*;
import static org.junit.Assert.*;

import models.*;
import controllers.*;

import java.sql.SQLException;
import controllers.TeamController;
import org.junit.Test;

public class TeamControllerTest
{
    private static TeamController tc;

    /***
     * Run once at class creation to set up mock db
     * or any other static objects
     */
    @Before
    public void setUpMock()
    {
        try {
           ConnectionSource obConn = new JdbcConnectionSource("jdbc:h2:mem:myDb");
            tc = new TeamController(obConn);
            TableUtils.clearTable(obConn, Team.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateTeamWithValidName() {
        Team team1 = new Team();
        team1.setTeamName("Saskatoon Blades");
        team1.setCoachName("Sebastion Bird");

        Team team2 = new Team();
        team2.setTeamName("Rosetown Rangers");
        team2.setCoachName("Tom Jerry.");

        assertSame("successful createTeam returns original name", tc.createTeam(team1), true);

        assertTrue("createTeam with same name already exists returns null", tc.createTeam(team2));

        team2.setTeamName("Toronto Michlobes");//set a new random name for second team

        assertSame("successful second createTeam returns original name", tc.createTeam(team2), true);

    }

    /**
     * Invalid team name entry
     */
    @Test
    public void testCreateTeamWithInvalidName() {
        Team team = new Team();

        assertFalse( "Add invalid Team name expect null", tc.createTeam(team));
    }

    /**
     * Valid modify team name entry
     */
    @Test
    public void testModifyTeamWithValidName()
    {
        Team team1 = new Team();
        team1.setTeamName("Saskatoon Blades");

        Team team2 = new Team();
        team2.setTeamName("Saskatoon Thunder");

        tc.createTeam(team1);
        assertSame("Team successfully modified, send new data, and return edited Team", tc.modifyTeam(team2), false);
    }

    /**
     * Invalid modify team name entry
     */
    @Test
    public void testModifyTeamWithIdenticalName()
    {
        Team team1 = new Team();
        team1.setCoachName("Kyle");
        team1.setTeamName("Saskatoon Blades");

        Team team2 = new Team();
        team2.setCoachName("Kyle");
        team2.setTeamName("Saskatoon Jets");

        assertTrue(tc.createTeam(team2));
        assertTrue(tc.createTeam(team1));

        team2.setTeamName("Saskatoon Blades");

        assertFalse("Passed Team Object exists in database return false", tc.modifyTeam(team2));
    }


    @Test
    public void testCreateTeamList()
    {
        Team team1 = new Team();
        team1.setCoachName("Kyle");
        team1.setTeamName("Saskatoon Blades");

        Team team2 = new Team();
        team2.setCoachName("Gabe");
        team2.setTeamName("Penguns");

        tc.createTeam(team1);
        tc.createTeam(team2);
        assertEquals(tc.getAllTeam().size(), 2);

    }

}
