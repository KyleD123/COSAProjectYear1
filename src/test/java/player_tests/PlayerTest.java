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
//        player.setsPosition("Right Wing");
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
    public void testFirstNameLowerBound() {
        player.setsFirstName(repeatQ(1));

        assertEquals(0, validator.validate(player).size());
    }

    @Test
    public void testFirstNameUpperBound() {
        player.setsFirstName(repeatQ(15));

        assertEquals(0, validator.validate(player).size());
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
    public void testLastNameLowerBound() {
        player.setsLastName(repeatQ(1));

        assertEquals(0, validator.validate(player).size());
    }

    @Test
    public void testLastNameUpperBound() {
        player.setsLastName(repeatQ(15));

        assertEquals(0, validator.validate(player).size());
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
    public void testPlayerNumberUpperBound() {
        player.setnPlayerNumber(99);

        assertEquals(0, validator.validate(player).size());
    }

    @Test
    public void testPlayerNumberLowerBound() {
        player.setnPlayerNumber(1);

        assertEquals(0, validator.validate(player).size());
    }



//    @Test
//    public void testPlayerPositionIsTooLong() {
//        String invalid = repeatQ(25);
//        player.setsPosition(invalid);
//
//        assertInvalidPlayer("sPosition", "Player Position Too Long", invalid);
//    }

//    @Test
//    public void testPlayerPositionUpperBound() {
//        player.setsPosition(repeatQ(20));
//
//        assertEquals(0, validator.validate(player).size());
//    }

//    @Test
//    public void testPlayerPositionLowerBound() {
//        player.setsPosition(repeatQ(1));
//
//        assertEquals(0, validator.validate(player).size());
//    }

//    @Test
//    public void testPlayerPositionIsEmpty() {
//        player.setsPosition("");
//
//        assertInvalidPlayer("sPosition", "Player Position is Required", "");
//    }

    @Test
    public void testPlayerParentInfoIsTooLong() {
        String invalid = repeatQ(35);
        player.setsParentInfo(invalid);

        assertInvalidPlayer("sParentInfo", "Player Parent Info Too Long", invalid);
    }

    @Test
    public void testPlayerParentInfoUpperBound() {
        player.setsParentInfo(repeatQ(30));

        assertEquals(0, validator.validate(player).size());
    }

    @Test
    public void testPlayerParentInfoLowerBound() {
        player.setsParentInfo(repeatQ(1));

        assertEquals(0, validator.validate(player).size());
    }

    @Test
    public void testPlayerParentInfoIsEmpty() {
        player.setsParentInfo("");

        assertInvalidPlayer("sParentInfo", "Player Parent Info is Required", "");
    }

    @Test
    public void testPlayerEmergencyContactIsValid() {
        String[] validContact = {"123-456-7890", "1-123-456-7890"};

        for (String valid : validContact) {
            player.setsEmergencyContact(valid);

            assertEquals(0, validator.validate(player).size());
        }
    }

    @Test
    public void testPlayerEmergencyContactIsInvalid() {
        String[] invalidContact = {"1234567890", "11234567890", "123-4569"};

        for (String valid : invalidContact) {
            player.setsEmergencyContact(valid);

            assertInvalidPlayer("sEmergencyContact", "Improper Phone Number Format", valid);
        }
    }

}
