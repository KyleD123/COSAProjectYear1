package tournament_tests;

import models.Tournament;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import javax.validation.*;

public class TournamentTest
{
    private static ValidatorFactory valFac;
    private static Validator valid;

    private static Tournament obTourn;

    //HELPER METHODS
    public String repeatW(int nCount)
    {
       return new String(new char[nCount]).replace('\0', 'W');
    }


    //PRE-TEST SETUP
    @BeforeClass
    public static void setUpValidator()
    {
        valFac = Validation.buildDefaultValidatorFactory();
        valid = valFac.getValidator();
    }
    
    @AfterClass
    public static void closedownValidator()
    {
        valFac.close();
    }


    @Before
    public void setUpValidTournament()
    {

    }


}
