package team_tests;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import javax.validation.*;
import models.*;
import models.Team;

import javax.validation.ValidatorFactory;

public class TeamTests
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

        assertEquals( expProperty, violation.getPropertyPath().toString() );

        assertEquals( expErrMsg, violation.getMessage() );

        assertEquals( expValue, violation.getInvalidValue() );
    }

    //endregion HELPER METHODS

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

    //----- Tests Start Here -----//




    //Look at testManufacturerIsTooLong
    @Test
    public void TooLongTeamName()
    {
        String invalid = repeatM(41);

    }
}
