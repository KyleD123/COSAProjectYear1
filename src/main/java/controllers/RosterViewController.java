package controllers;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RosterViewController implements Initializable {

    //Add @FXML annotations

    private Stage obMainStage = new Stage();
    private RosterController rosterControl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ConnectionSource databaseConn = null;
        try
        {
            databaseConn = new JdbcPooledConnectionSource("jdbc:sqlite:eSchedule.db");

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        rosterControl = new RosterController(databaseConn);
    }

    public void cancel(MouseEvent mouseEvent) throws IOException
    {
//        obMainStage = (Stage) btnCancel.getScene().getWindow();
//
//        obMainStage.close();

    }



}
