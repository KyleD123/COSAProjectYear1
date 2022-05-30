package controllers;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import models.Player;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManageRosterController implements Initializable
{

    @FXML
    private ListView<Player> PlayerList;

    private PlayerController playerControl;


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

        playerControl = new PlayerController(databaseConn);
        populateListView();
    }

    public void populateListView()
    {
        ObservableList<Player> ListOfPlayer = FXCollections.observableArrayList(playerControl.getAllPlayers());
        PlayerList.getItems().addAll(ListOfPlayer);

    }

    public void addPlayerToTeam()
    {

    }
}
