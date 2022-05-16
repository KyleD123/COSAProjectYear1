package player_tests;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import javax.validation.*;
import models.*;

public class PlayerTest {

    private static ValidatorFactory vf;
    private static Validator validator;

    private Player player;

    private String repeatQ(int count){
        return new String(new char[count]).replace('\0','Q');
    }

    private void assertInvalidPlayer(String expectedProperty, String expectedErrMsg, Object expectedValue){
        Set<ConstraintViolation<Player>> constraintViolations = validator.validate( player );
        //count how many violations - SHOULD ONLY BE 1
        assertEquals( 1, constraintViolations.size() );

        //get first violation from constraintViolations collection
        ConstraintViolation<Player> violation = constraintViolations.iterator().next();

        //ensure that expected property has the violation
        assertEquals( expectedProperty, violation.getPropertyPath().toString() );

        //ensure error message matches what is expected
        assertEquals( expectedErrMsg, violation.getMessage() );

        //ensure the invalid value is what was set
        assertEquals( expectedValue, violation.getInvalidValue() );
    }

    @BeforeClass
    public static void setUpValidator() {
        vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
    }

    @AfterClass
    public static void tearDownValidator() {
        vf.close();
    }

    @Before
    public void setUpValidPlayer() {
        player = new Player();
        player.setsFirstName("Timmy");
        player.setsLastName("Jackson");
        player.setnPlayerNumber(24);
        player.setsPosition("Right Wing");
        player.setsParentInfo("Linda Jackson");
        player.setsEmergencyContact("306-456-7892");
    }

    @Test
    public void testFirstNameIsTooLong() {
        String invalid = repeatQ(20);
        player.setsFirstName(invalid);

        assertInvalidPlayer("sFirstName", "Player First Name Too Long", invalid);
    }

    @Test
    public void testFirstNameIsEmpty() {
        player.setsFirstName("");

        assertInvalidPlayer("sFirstName", "Player First Name is Required", "");
    }

    @Test
    public void testLastNameIsTooLong() {
        String invalid = repeatQ(20);
        player.setsLastName(invalid);

        assertInvalidPlayer("sLastName", "Player Last Name Too Long", invalid);
    }

    @Test
    public void testLastNameIsEmpty() {
        player.setsLastName("");

        assertInvalidPlayer("sLastName", "Player Last Name is Required", "");
    }

    @Test
    public void testPlayerNumberIsTooManyDigits() {
        player.setnPlayerNumber(673);

        assertInvalidPlayer("nPlayerNumber", "Player Number must be 1 or 2 digits", 673);
    }

    @Test
    public void testPlayerNumberIsValid() {
        player.setnPlayerNumber("2a");

        assertInvalidPlayer("nPlayerNumber", "Player Number must be 1 or 2 digits", "2a");
    }

}
