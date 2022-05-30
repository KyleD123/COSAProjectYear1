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

    private static PlayerController pc;

    @Before
    public void setUpMock() {
        try {
            ConnectionSource obConn = new JdbcPooledConnectionSource("jdbc:h2:mem:myDb");
            pc = new PlayerController(obConn );
            TableUtils.clearTable(obConn, Player.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Team team1 = new Team();
        
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


    assertSame("successfully added player to team", pc.(player), true);

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



        assertTrue("successfully added player to team return Player", pc.modifyPlayer(player1));

        assertFalse("passed player already exists return false", pc.modifyPlayer(player2));


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

        assertFalse("max players reached, return false", pc.modifyPlayer(player));

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


        assertTrue("successfully added Player to Team, return Player", pc.modifyPlayer(player));
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


        assertTrue("successfully added Player to Team, return Player", pc.modifyPlayer(player));
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


        pc.modifyPlayer(player);
        assertTrue("successfully removed Player from Team, return Player", pc.removePlayerFromTeam(player));
    }



}
