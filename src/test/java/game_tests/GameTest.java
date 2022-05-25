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
import java.util.Date;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class GameTest {
    private static ValidatorFactory valFac;
    private static Validator valid;

    private static Tournament obTourn;

    private static Game obGame;

    private static Date startDate;
    private static Date endDate;

    private static Team obTeam1;
    private static Team obTeam2;

    private void assertTwoHourError(String expProperty, String expMsg, Object expValue)
    {
        Set<ConstraintViolation<Game>> constraintViolations = valid.validate(obGame);

        assertEquals(1, constraintViolations.size());

        ConstraintViolation<Game> violation = constraintViolations.iterator().next();

        assertEquals(expProperty, violation.getPropertyPath().toString());

        assertEquals(expMsg, violation.getMessage());

        Game obRef = (Game) violation.getInvalidValue();

        assertEquals(expValue, obRef.getnEndTime());
    }

    private void assertInvalidSchedule(String expProperty, String expMsg, Object expValue) {
        Set<ConstraintViolation<Game>> constraintViolations = valid.validate(obGame);

        assertEquals(1, constraintViolations.size());

        ConstraintViolation<Game> violation = constraintViolations.iterator().next();

        assertEquals(expProperty, violation.getPropertyPath().toString());

        assertEquals(expMsg, violation.getMessage());

        assertEquals(expValue, violation.getInvalidValue());

    }

    @BeforeClass
    public static void setUpValidator() {
        valFac = Validation.buildDefaultValidatorFactory();
        valid = valFac.getValidator();
    }

    /**
     * This will set up a valid game, a valid pair team( same pool id) and set up schedule.
     */
    @Before
    public void setUpValidSchedule() {
        obTourn = new Tournament();
        obTourn.setsTournamentName("Super Kids Brawling");

        startDate = new Date();
        startDate.setTime(startDate.getTime() + (1000 * 60 * 60 * 24));
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
        obGame.setnStartTime(900);
        obGame.setnEndTime(1100);
        obGame.settHomeTeam(obTeam1);
        obGame.settAwayTeam(obTeam2);
        obGame.settTournament(obTourn);
        obGame.setsLocation("ACT Centre Rink A");
    }

    @Test
    public void testCreateValidGame() {
        assertEquals(0, valid.validate(obGame).size());
    }

    @Test
    public void testCreateGameWithinBoundary() {
        obGame.setnEndTime(obGame.getnStartTime() + 199);
        assertEquals(0, valid.validate(obGame).size());
    }

    @Test
    public void testCreateGameOutsideBoundary() {
        int endTime = obGame.getnStartTime() + 201;
        obGame.setnEndTime(endTime);
        assertTwoHourError("", "The end date is over two hours based on the requested time", endTime);
    }

    /**
     * Exception Test
     * END TIME IS BEFORE THE START TIME
     *
     * INVALID
     */
    @Test
    public void testCreateSchedulePastEndTime()
    {
        int endTime = 600;
        obGame.setnEndTime(endTime);
        assertTwoHourError("", "Past End Time is selected", endTime);
    }

    /**
     * EXCEPTION TEST
     * Start Date is not defined (But really, this is automatically added based on the tournament screen anyway based on the Tournament end and startdate)
     *
     * INVALID
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
     * Tournament is not added to the schedule object as a sort of reference.
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
     * You did not specify a location for this schedule.
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