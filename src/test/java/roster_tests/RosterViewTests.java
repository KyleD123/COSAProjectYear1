//package roster_tests;
//
//import models.Player;
//import models.Team;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import javax.validation.ConstraintViolation;
//import javax.validation.Validation;
//import javax.validation.Validator;
//import javax.validation.ValidatorFactory;
//import java.util.Set;
//
//import static org.junit.Assert.assertEquals;
//
///**
// * Purpose: Testing the GUI functions for dragging and dropping players in the roster view
// *
// * @author Kyle & Jared team green
// *
// */
//public class RosterViewTests
//{
//    private static ValidatorFactory valFac;
//    private static Validator validator;
//
//    private void assertInvalidRoster(String expProperty, String expErrMsg, Object expValue)
//    {
//        Set<ConstraintViolation<Team>> constraintViolations = validator.validate( obTeam );//use the private global car created in setUpValidTeam
//
//        assertEquals( 1, constraintViolations.size() );
//
//        ConstraintViolation<Team> violation = constraintViolations.iterator().next();
//
//        assertEquals(expProperty, violation.getPropertyPath().toString() );
//
//        assertEquals(expErrMsg, violation.getMessage() );
//
//        assertEquals(expValue, violation.getInvalidValue() );
//    }
//
//    @BeforeClass
//    public static void setUpValidator()
//    {
//        valFac = Validation.buildDefaultValidatorFactory();
//        validator = valFac.getValidator();
//    }
//
//    @AfterClass
//    public static void tearDownValidator()
//    {
//        valFac.close();
//    }
//
//    @Before
//    public static void setUpValidPlayers()
//    {
//        Player obPlayer1 = new Player();
//        obPlayer1.setnPlayerNumber(69);
//        obPlayer1.setsFirstName("Jack");
//        obPlayer1.setsLastName("Cummins");
//        obPlayer1.setsEmergencyContact("306-999-9999");
//        obPlayer1.setsParentInfo("Mother");
//
//
//    }
//
//    /**
//     * Tests if you can drag a player onto a position that is already filled
//     *
//     * Exception Case
//     */
//    @Test
//    public void testPlayerAlreadyOccupiesPosition()
//    {
//
//    }
//
//    /**
//     * Tests if you can drag and drop a player to a position
//     * which would then set the position of the player object
//     *
//     * Normal Case
//     */
//    @Test
//    public void testPlayerAddedToPosition()
//    {
//
//    }
//
//    /**
//     * Tests if you can add the sixth player into the position
//     *
//     * Boundary Case
//     */
//    @Test
//    public void testAddingTheSixthPlayerToAPosition()
//    {
//
//    }
//
//    /**
//     * Tests if you can drag an empty player into a position field
//     */
//    @Test
//    public void testDraggingEmptyPlayerToPosition()
//    {
//
//    }
//
//
//
//
//
//
//}
