package roster_tests;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.junit.*;
import org.junit.Assert.*;

import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

import models.*;
import controllers.*;

import java.sql.SQLException;

public class RosterControllerTest {

    private static RosterController rc;

    @Before
    public void setUpMock() {
        try {
            ConnectionSource obConn = new JdbcPooledConnectionSource("jdbc:h2:mem:myDb");
            rc = new RosterController(obConn );
            TableUtils.clearTable(obConn, Player.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddPlayerToTeam() {

    }

    @Test
    public void testAddExistingPlayertoTeam() {

    }

}
