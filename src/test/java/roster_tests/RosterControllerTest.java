package roster_tests;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.junit.*;
import org.junit.Assert.*;

import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import models.*;
import controllers.*;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class RosterControllerTest {

    private static RosterController rc;

    @Before
    public void setUpMock() {
        try {
            ConnectionSource obConn = new JdbcPooledConnectionSource("jdbc:h2:mem:myDb");
            rc = new RosterController(obConn );
            TableUtils.clearTable(obConn, Player.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * tests the functionality of adding a player to a team
     */
    @Test
    public void testAddPlayerToTeam()
    {
    Player player = new Player();
    Team team = new Team();
    assertSame("successfully added player to team", rc.addPlayerToTeam(player,team), true);

    }

    @Test
    public void testAddExistingPlayerToTeam()
    {
        Player player1 = new Player();
        Player player2 = new Player();
        Team team = new Team();


        assertTrue("successfully added player to team return Player", rc.addPlayerToTeam(player1,team));

        assertFalse("passed player already exists return false", rc.addPlayerToTeam(player2,team) );


    }

    @Test
    public void  testAdd16thPlayerToTeam(){

    }

    @Test
    public void testAddFinalPlayerToTeam()
    {

    }

    @Test
    public void  testAddFirstPlayerToTeam()
    {

    }

}
