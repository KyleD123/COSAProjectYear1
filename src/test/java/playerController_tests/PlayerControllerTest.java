package playerController_tests;

import controllers.PlayerController;
import models.Player;
import org.junit.Before;
import org.junit.Test;

import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class PlayerControllerTest {

    private static PlayerController pc;

//    @Before
//    public void setUpMock() {
//
//    }

    @Test
    public void testAddPlayerWithValidInfo() {
        Player player1 = new Player();
        player1.setsFirstName("Timmy");
        player1.setsLastName("Jackson");
        player1.setnPlayerNumber(24);
        player1.setsPosition("Right Wing");
        player1.setsParentInfo("Linda Jackson");
        player1.setsEmergencyContact("306-456-7892");
    }
}
