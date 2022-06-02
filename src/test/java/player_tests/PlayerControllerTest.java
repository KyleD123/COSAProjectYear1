package player_tests;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.junit.*;
import org.junit.Assert.*;

import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

import models.*;
import controllers.*;

import java.sql.Connection;
import java.sql.SQLException;

public class PlayerControllerTest {

    private static PlayerController pc;
    private static TeamController tc;

    @Before
    public void setUpMock() {
        try {
            ConnectionSource obConn = new JdbcPooledConnectionSource("jdbc:h2:mem:myDb");
            pc = new PlayerController(obConn);

            tc = new TeamController(obConn);

            TableUtils.clearTable(obConn, Player.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreatePlayerWithValidInfo() {
        Player player1 = new Player();
        player1.setsFirstName("Timmy");
        player1.setsLastName("Jackson");
        player1.setnPlayerNumber(24);
//        player1.setsPosition("Right Wing");
        player1.setsParentInfo("Linda Jackson");
        player1.setsEmergencyContact("306-456-7892");

        Player player2 = new Player();
        player2.setsFirstName("Tommy");
        player2.setsLastName("Jackson");
        player2.setnPlayerNumber(42);
//        player2.setsPosition("Left Wing");
        player2.setsParentInfo("Linda Jackson");
        player2.setsEmergencyContact("306-456-7892");

        try {
            assertSame("successful createPlayer returns original Player", pc.createPlayer(player1), true);

            assertSame("successful createPlayer returns original Player", pc.createPlayer(player2), true);
        } catch (Exception e) {

        }
    }

    @Test
    public void testCreatePlayerThatIsExisting() {
        Player player1 = new Player();
        player1.setsFirstName("Timmy");
        player1.setsLastName("Jackson");
        player1.setnPlayerNumber(24);
//        player1.setsPosition("Right Wing");
        player1.setsParentInfo("Linda Jackson");
        player1.setsEmergencyContact("306-456-7892");

        Player player2 = new Player();
        player2.setsFirstName("Timmy");
        player2.setsLastName("Jackson");
        player2.setnPlayerNumber(24);
//        player2.setsPosition("Right Wing");
        player2.setsParentInfo("Linda Jackson");
        player2.setsEmergencyContact("306-456-7892");

        try {
            assertSame("sucessful createPlayer returns original Player", pc.createPlayer(player1), true);
            assertFalse("createPlayer with same info returns false", pc.createPlayer(player2));
        } catch (Exception e) {

        }
    }

    @Test
    public void testModifyPlayerWithValidInfo() throws SQLException {
        Player player1 = new Player();
        player1.setsFirstName("Timmy");
        player1.setsLastName("Jackson");
        player1.setnPlayerNumber(24);
        player1.setsPosition("Right Wing");
        player1.setsParentInfo("Linda Jackson");
        player1.setsEmergencyContact("306-456-7892");

        Player player2 = new Player();
        player2.setsFirstName("Tommy");
        player2.setsLastName("Jackson");
        player2.setnPlayerNumber(42);
        player2.setsPosition("Left Wing");
        player2.setsParentInfo("Linda Jackson");
        player2.setsEmergencyContact("306-456-7892");

        try {
            assertSame("successful createPlayer returns original Player", pc.createPlayer(player1), true);

            assertSame("successful createPlayer returns original Player", pc.createPlayer(player2), true);
        } catch (Exception e) {

        }
    }

    @Test
    public void testNoDuplicatePlayersPosition()
    {
        Team obTeam1 = new Team();
        obTeam1.setTeamName("Blades");
        obTeam1.setCoachName("Kyle");

        tc.createTeam(obTeam1);
        Player player1 = new Player();
        player1.setsFirstName("Timmy");
        player1.setsLastName("Jackson");
        player1.setObTeam(obTeam1);
        player1.setnPlayerNumber(24);
        player1.setsPosition("Goalie");
        player1.setObTeam(obTeam1);
        player1.setsParentInfo("Linda Jackson");
        player1.setsEmergencyContact("306-456-7892");

        Player player2 = new Player();
        player2.setsFirstName("Timmy");
        player2.setsLastName("Jackson");
        player2.setnPlayerNumber(26);
        player2.setsPosition(null);
        player2.setObTeam(obTeam1);
        player2.setsParentInfo("Linda Jackson");
        player2.setsEmergencyContact("306-456-7892");

        pc.createPlayer(player1);
        pc.createPlayer(player2);
        player2.setsPosition("Goalie");

        try {
            assertSame("Passed Player Object exists in database return false", pc.modifyPlayer(player2), false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testModifyPlayerWithIdenticalInfo() throws SQLException {
        Player player1 = new Player();
        player1.setsFirstName("Timmy");
        player1.setsLastName("Jackson");
        player1.setnPlayerNumber(24);
        player1.setsPosition("Right Wing");
        player1.setsParentInfo("Linda Jackson");
        player1.setsEmergencyContact("306-456-7892");

        pc.createPlayer(player1);
        assertSame("Passed Player Object exists in database return false", pc.modifyPlayer(player1), true);
    }

    @Test
    public void testDatabaseList()
    {
        Player player1 = new Player();
        player1.setsFirstName("Timmy");
        player1.setsLastName("Jackson");
        player1.setnPlayerNumber(24);
//        player1.setsPosition("Right Wing");
        player1.setsParentInfo("Linda Jackson");
        player1.setsEmergencyContact("306-456-7892");

        Player player2 = new Player();
        player2.setsFirstName("Ernesto");
        player2.setsLastName("Basoalto");
        player2.setnPlayerNumber(65);
//        player2.setsPosition("Left Wing");
        player2.setsParentInfo("Bryce Barrie");
        player2.setsEmergencyContact("306-699-6999");

        pc.createPlayer(player1);
        pc.createPlayer(player2);

        assertSame(pc.getAllPlayers().size(), 2);

    }
}
