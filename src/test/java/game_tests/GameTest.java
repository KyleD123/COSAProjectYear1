package game_tests;

import models.Game;
import models.Team;
import models.Tournament;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * PURPOSE: THE PURPOSE OF THIS JUNIT TEST IS TO VERIFY THAT THE ESSENTIAL FUNCTIONALITIES OF THE GAME OBJECT IS
 * WORKING BEFORE PASSING OF TO THE DATABASE.
 *
 * @author TEAM GREEN
 */
public class GameTest {
    private static ValidatorFactory valFac;
    private static Validator valid;

    private static Tournament obTourn;

    private static Game obGame;

    private static Date startDate;
    private static Date endDate;

    private static Team obTeam1;
    private static Team obTeam2;

    /**
     * THIS METHOD, ASSERTTWOHOURERROR, IS A SPECIAL TYPE OF METHOD THAT ALLOWS US TO CATCH
     * A VALIDATION ERROR FOR A GAME OBJECT WHEN THEIR START TIME AND END TIME GOES OVER
     * THE TWO HOUR EXPECTATIONS FOR A SINGLE GAME.
     *
     * THIS CAN BE USED TO CHECK IF THE END TIME IS WAY BEFORE THE START TIME. FOR EXAMPLE, 9AM FOR THE END TIME,
     * 11 AM FOR THE START TIME.
     * @param expProperty - this takes in nothing since this method expects a Game object as a reference rather than an individual field.
     * @param expMsg - Our expected message that the TwoHourGap annotation will display
     * @param expValue - Takes in the game's end time as a reference that our expected error value would be.
     */
    private void assertTwoHourError(String expProperty, String expMsg, Object expValue)
    {
        Set<ConstraintViolation<Game>> constraintViolations = valid.validate(obGame);

        assertEquals(1, constraintViolations.size());

        ConstraintViolation<Game> violation = constraintViolations.iterator().next();

        assertEquals(expProperty, violation.getPropertyPath().toString());

        assertEquals(expMsg, violation.getMessage());

        Game obRef = (Game) violation.getInvalidValue();

        assertEquals(expValue, obRef.getdEventDate());
    }

    /**
     * BASED ON THE GIVEN CLASS EXAMPLE, THIS METHOD CATCHES AN EXPECTED VALIDATION ERROR IF ANY OF THE FIELDS
     * IN THE GAME OBJECT HAS CREATED AN ERROR BASED ON THE FIELDS THAT WE HAVE DELIBERATELY MADE IT INCORRECT TO
     * FAIL THE VALIDATION
     *
     * @param expProperty - THE EXPECTED PROPERTY OF THE GAME OBJECT TO FAIL
     * @param expMsg - IS THE EXPECTED MESSAGE THAT RETURNS FROM THAT ERROR IS EQUALS TO OUR EXPECTED ERROR MESSAGE
     * @param expValue - EXPECTED VALUE OBJECT THAT MADE THE GAME OBJECT NOT VALID.
     */
    private void assertInvalidSchedule(String expProperty, String expMsg, Object expValue) {
        Set<ConstraintViolation<Game>> constraintViolations = valid.validate(obGame);

        assertEquals(1, constraintViolations.size());

        ConstraintViolation<Game> violation = constraintViolations.iterator().next();

        assertEquals(expProperty, violation.getPropertyPath().toString());

        assertEquals(expMsg, violation.getMessage());

        assertEquals(expValue, violation.getInvalidValue());

    }

    /**
     * BEFORE STARTING UP THIS TESTING CLASS, THIS ENSURES THAT THE VALIDATOR HAS BEEN SET UP PROPERLY
     * TO ACCEPT VALIDATIONS CHECKS FOR THE GAME OBJECTS.
     */
    @BeforeClass
    public static void setUpValidator() {
        valFac = Validation.buildDefaultValidatorFactory();
        valid = valFac.getValidator();
    }

    /**
     * BEFORE EVERY TEST, THIS METHOD CREATES A VALID UNIQUE GAME TO ENSURE THAT EVERYTIME WE ENTER THIS GAME OBJECT TO THE DATABASE,
     * IT IS VALIDATED BEFORE WE CAN MALICIOUSLY MODIFY IT.
     */
    @Before
    public void setUpValidSchedule()
    {
        obTourn = new Tournament();
        obTourn.setsTournamentName("Super Kids Brawling");

        startDate = new Date();
        startDate.setHours(6);
        startDate.setMinutes(0);
        startDate.setTime(startDate.getTime());
        endDate = new Date();
        endDate.setTime(startDate.getTime() + (1000 * 60 * 60 * 24));
        obTourn.setdStartDate(startDate);
        obTourn.setdEndDate(endDate);

        obTeam1 = new Team();
        obTeam1.setTeamName("Saskatoonian Bruhlers");
        obTeam1.setCoachName("Coach Levi");
        obTeam1.setTeamID(1);

        obTeam2 = new Team();
        obTeam1.setTeamName("Regina Trashers");
        obTeam1.setCoachName("Coach Tass");
        obTeam1.setTeamID(2);

        obGame = new Game();
        //initiating methods
        obGame.setdEventDate(obTourn.getdStartDate());
        obGame.settHomeTeam(obTeam1);
        obGame.settAwayTeam(obTeam2);
        obGame.settTournament(obTourn);
        obGame.setsLocation("ACT Centre Rink A");
    }

    /**
     * NORMAL TEST
     *
     * THIS METHOD CREATES A VALID GAME TO VERIFIES THAT OUR VALIDATOR WORKS AS EXPECTED
     */
    @Test
    public void testCreateValidGame() {
        assertEquals(0, valid.validate(obGame).size());
    }

    /**
     * EXCEPTION TEST
     * THIS METHOD CREATES AN INVALID GAME TO VERIFY IF THE VALIDATOR WILL CATCH THE ERROR IF WE DONT HAVE A NULL EVENT DATE.
     *
     */
    @Test
    public void testCreateScheduleWithoutEventDate()
    {
        Date obDate = null;
        obGame.setdEventDate(obDate);
        assertInvalidSchedule("dEventDate", "There must be a Event Date!", obDate);
    }

    /**
     * EXCEPTION
     * THIS METHOD CREATES AN INVALID GAME TO VERIFY IF THE VALIDATOR WILL CATCH THE ERROR IF WE DID NOT ADD A TOURNAMENT AS A REFERENCE FOR THE GAME TO TIE ON.
     *
     * INVALID
     */
    @Test
    public void testCreateScheduleWithoutTournament()
    {
        Tournament obTournament = null;
        obGame.settTournament(obTournament);
        assertInvalidSchedule("tTournament", "There must be a tournament tied to this schedule", obTournament);
    }

    /**
     * EXCEPTION
     * THIS METHOD CREATES AN INVALID GAME TO VERIFY IF THE VALIDATOR WILL CATCH THE ERROR IF WE DID NOT SPECIFY A LOCATION FOR OUR OBJECTION
     *
     * INVALID
     */
    @Test
    public void testCreateScheduleWithoutLocation()
    {
        String location = null;
        obGame.setsLocation(location);
        assertInvalidSchedule("sLocation", "Provide a location and rink!", location);
    }
}