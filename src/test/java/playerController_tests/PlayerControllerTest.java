package playerController_tests;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import org.junit.*;
import org.junit.Assert.*;

import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

import models.*;
import controllers.*;

import java.sql.SQLException;

public class PlayerControllerTest {

    private static PlayerController pc;

    @Before
    public void setUpMock() {
        try {
            pc = new PlayerController( new JdbcPooledConnectionSource("jdbc:h2:mem:myDb") );
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
    public void testCreatePlayerThatIsExisting() {
        Player player1 = new Player();
        player1.setsFirstName("Timmy");
        player1.setsLastName("Jackson");
        player1.setnPlayerNumber(24);
        player1.setsPosition("Right Wing");
        player1.setsParentInfo("Linda Jackson");
        player1.setsEmergencyContact("306-456-7892");

        Player player2 = new Player();
        player2.setsFirstName("Timmy");
        player2.setsLastName("Jackson");
        player2.setnPlayerNumber(24);
        player2.setsPosition("Right Wing");
        player2.setsParentInfo("Linda Jackson");
        player2.setsEmergencyContact("306-456-7892");

        try {
            assertSame("sucessful createPlayer returns original Player", pc.createPlayer(player1), true);
            assertFalse("createPlayer with same info returns false", pc.createPlayer(player2));
        } catch (Exception e) {

        }
    }

    @Test
    public void testModifyPlayer() {

    }
}
