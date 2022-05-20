package tournament_tests;

import javafx.util.converter.LocalDateTimeStringConverter;
import models.*;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import javax.validation.*;
import java.util.Date;
import java.util.Set;

public class TournamentTest
{
    private static ValidatorFactory valFac;
    private static Validator valid;

    private static Tournament obTourn;
    private static Date startDate;
    private static Date endDate;

    /**
     * Apparently, all of this passed somehow. I dont know where we messed it up.
     * @param nCount
     * @return
     */
    //HELPER METHODS
    public String repeatW(int nCount)
    {
       return new String(new char[nCount]).replace('\0', 'W');
    }

    private void assertInvalidTournament(String expProperty, String expMsg, Object expValue)
    {
        Set<ConstraintViolation<Tournament>> constraintViolations = valid.validate(obTourn);

        assertEquals(1, constraintViolations.size());

        ConstraintViolation<Tournament> violation = constraintViolations.iterator().next();

        assertEquals(expProperty, violation.getPropertyPath().toString());

        assertEquals(expMsg, violation.getMessage());

        assertEquals(expValue, violation.getInvalidValue());

    }


    //PRE-TEST SETUP
    @BeforeClass
    public static void setUpValidator()
    {
        valFac = Validation.buildDefaultValidatorFactory();
        valid = valFac.getValidator();
    }
    
    @AfterClass
    public static void closeDownValidator()
    {
        valFac.close();
    }


    @Before
    public void setUpValidTournament()
    {
        obTourn = new Tournament();
        obTourn.setTournamentName("Penguins");
        startDate = new Date();
        startDate.setTime(startDate.getTime() + (1000*60*60*24));
        endDate = new Date();
        endDate.setTime(startDate.getTime() + (1000*60*60*24));
        obTourn.setStartDate(startDate);
        obTourn.setEndDate(endDate);
    }

 //Tests
    //START AND END DATES

    /**
     * INVALID TEST
     */
    @Test
    public void testTournamentPastStartDate()
    {
        startDate.setTime(startDate.getTime() - (1000*60*60*24));
        obTourn.setStartDate(startDate);
        assertInvalidTournament("startDate","Past date entered", startDate);
    }

    /**
     * INVALID TEST
     */
    @Test
    public void testTournamentPastEndDate()
    {
        endDate.setTime(startDate.getTime() - (1000*60*60*25));
        obTourn.setEndDate(endDate);
        assertInvalidTournament("endDate", "Past date entered", endDate);
    }

    /**
     * INVALID TEST
     */
    @Test
    public void testTournamentBlankStartDate()
    {
        obTourn.setStartDate(null);
        assertInvalidTournament("startDate", "Empty Date Entered", obTourn.getStartDate());

    }

    /**
     * INVALID TEST
     */
    @Test
    public void testTournamentBlankEndDate()
    {
        obTourn.setEndDate(null);
        assertInvalidTournament("endDate", "Empty Date Entered", obTourn.getEndDate());
    }

    /**
     * VALID TEST
     */
    @Test
    public void testTournamentValidStartDate()
    {
        startDate.setTime(startDate.getTime() + (1000*60*60*168));
        obTourn.setStartDate(startDate);
        assertEquals(0, valid.validate(obTourn).size());
    }

    /**
     * VALID TEST
     */
    @Test
    public void testTournamentValidEndDate()
    {
        endDate.setTime(startDate.getTime() + (1000*60*60*48));
        obTourn.setEndDate(endDate);
        assertEquals(0, valid.validate(obTourn).size());
    }


    //Tournament Name
    /**
     * INVALID TEST
     */
    @Test
    public void testTournamentNameAboveLimit()
    {
        String sName = repeatW(41);
        obTourn.setTournamentName(sName);
        assertInvalidTournament("tournamentName", "Character length limit exceeded", sName);

    }

    /**
     * VALID TEST
     */
    @Test
    public void testTournamentNameWithinLimit()
    {
        String sName = repeatW(40);
        obTourn.setTournamentName(sName);
        assertEquals(0, valid.validate(obTourn).size());
    }

    /**
     * VALID TEST
     */
    @Test
    public void testBlankTournamentName()
    {
        obTourn.setTournamentName("");
        assertEquals(0, valid.validate(obTourn).size());
    }

    /**
     * VALID TEST
     */
    @Test
    public void testValidTournamentName()
    {
        obTourn.setTournamentName("Bryce Javanians");
        assertEquals(0, valid.validate(obTourn).size());
        System.out.println(valid.validate(obTourn));

    }



}
