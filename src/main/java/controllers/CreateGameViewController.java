package controllers;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateGameViewController  implements Initializable
{
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        ConnectionSource dbConn = null;
        try {
            dbConn = new JdbcPooledConnectionSource("jdbc:sqlite:eSchedule.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveGame(MouseEvent mouseEvent)
    {

    }

    public void cancel(MouseEvent mouseEvent) throws IOException
    {

    }

    public void responsePrompt(boolean status) throws IOException
    {

    }
}
