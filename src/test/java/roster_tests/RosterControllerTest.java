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

/**
 * Purpose: To test the functionality of adding/removing a player from a team
 *
 * @author Team Green
 */
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
     * Tests the functionality of adding a player to a team
     *
     * @param
     * @returns true
     */
    @Test
    public void testAddPlayerToTeam()
    {
    Player player = new Player();
    Team team = new Team();
    assertSame("successfully added player to team", rc.addPlayerToTeam(player,team), true);

    }

    /**
     * Tests the functionality of adding a same, existing player to a team
     *
     * @param
     * @returns
     */
    @Test
    public void testAddSameExistingPlayerToTeam()
    {
        Player player1 = new Player();
        Player player2 = new Player();
        Team team = new Team();


        assertTrue("successfully added player to team return Player", rc.addPlayerToTeam(player1,team));

        assertFalse("passed player already exists return false", rc.addPlayerToTeam(player2,team));


    }

    /**
     * tests the functionality of adding a player to a team that has a max amount of players for the team (15 players max)
     *
     * @param
     * @returns false
     */
    @Test
    public void  testAdd16thPlayerToTeam()
    {
        Player player = new Player();
        Team team = new Team();

        assertFalse("max players reached, return false", rc.addPlayerToTeam(player, team));

    }

    /**
     * tests the functionality of adding a final player to a team, reaching the max amount of players for the team
     *
     * @param
     * @returns true
     */
    @Test
    public void testAddFinalPlayerToTeam()
    {
        Player player = new Player();
        Team team = new Team();

        assertTrue("successfully added Player to Team, return Player", rc.addPlayerToTeam(player, team));
    }

    /**
     * tests the functionality of adding the very first player to a team
     *
     * @param
     * @returns true
     */
    @Test
    public void  testAddFirstPlayerToTeam()
    {
        Player player = new Player();
        Team team = new Team();

        assertTrue("successfully added Player to Team, return Player", rc.addPlayerToTeam(player, team));
    }

    /**
     * tests the functionality of removing a player from a team
     *
     * @param
     * @return true
     */
    @Test
    public void testRemovePlayerFromTeam()
    {
        Player player = new Player();
        Team team = new Team();

        rc.addPlayerToTeam(player, team);
        assertTrue("successfully removed Player from Team, return Player", rc.removePlayerFromTeam(player));
    }

}
