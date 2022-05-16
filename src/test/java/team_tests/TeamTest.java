package team_tests;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import javax.validation.*;
import models.*;
import models.Team;

import javax.validation.ValidatorFactory;

public class TeamTest
{
    private static ValidatorFactory valFac;
    private static Validator validator;
    private static Team obTeam;

    private String repeatM(int count)
    {
        return new String(new char[count]).replace('\0','M');
    }

    private void assertInvalidTeam(String expProperty, String expErrMsg, Object expValue)
    {
        Set<ConstraintViolation<Team>> constraintViolations = validator.validate( obTeam );//use the private global car created in setUpValidTeam

        assertEquals( 1, constraintViolations.size() );

        ConstraintViolation<Team> violation = constraintViolations.iterator().next();

        assertEquals(expProperty, violation.getPropertyPath().toString() );

        assertEquals(expErrMsg, violation.getMessage() );

        assertEquals(expValue, violation.getInvalidValue() );
    }
    //endRegion HELPER METHODS

    //region SETUP
    @BeforeClass
    public static void setUpValidator() {
        valFac = Validation.buildDefaultValidatorFactory();
        validator = valFac.getValidator();
    }

    @AfterClass
    public static void tearDownValidator()
    {
        //gracefully teardown the validator factory
        valFac.close();
    }

    @Before
    public void setUpValidTeam()
    {
        obTeam = new Team();
        obTeam.setTeamName("Melville Giants");
        obTeam.setCoachName("Jared S.");
    }

    //----- Tests Begin Here -----//
    /**
     * INVALID: Team Name null (Blank/ Empty)
     */
    @Test
    public void testTeamNameIsEmpty()
    {
        obTeam.setTeamName("");
        assertInvalidTeam("sTeamName", "Please enter a name", "");
    }

    /**
     * INVALID: Team Name that consists of 52 characters long
     */
    @Test
    public void testTeamNameIsTooLong()
    {
        //Create an invalid Team Name with 41 characters - too long
        String invalid = repeatM(41);
        obTeam.setTeamName(invalid);
        assertInvalidTeam("sTeamName", "Team Name Too Long", invalid);
    }

    /**
     * VALID: Team Name is 40 characters (Upper Bound)
     */
    @Test
    public void testTeamNameUpperBound()
    {
        //Set value to valid 40 character String Team name - Upper Bound
        obTeam.setTeamName(repeatM(40));
        assertEquals(0, validator.validate(obTeam).size());
    }

    /**
     * VALID: Team Name is 25 characters (Within Range)
     */
    @Test
    public void testTeamNameValid()
    {
        //Set value to valid 25 character String Team Name - Valid
        obTeam.setTeamName(repeatM(25));
        assertEquals(0, validator.validate(obTeam).size());
    }

    /**
     * VALID: Team Name is 1 character (Lower Bound)
     */
    @Test
    public void testTeamNameLowerBound()
    {
        //Set value to valid 1 character String Team Name - Lower Bound
        obTeam.setTeamName(repeatM(1));
        assertEquals(0, validator.validate(obTeam).size());
    }
}
