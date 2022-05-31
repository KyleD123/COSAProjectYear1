package controllers;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.Player;
import models.PlayerValidator;


import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ManageRosterController implements Initializable
{

    @FXML
    private ListView<Player> PlayerList;

    private PlayerController playerControl;

    @FXML
    private FlowPane fpBench;

    @FXML
    private VBox vGoalie, vLeftDefense, vRightDefense, vLeftWing, vRightWing, vCenter;

    private Player fakePlayer = new Player();

    private PlayerController playerController;
    private PlayerValidator obValid;


    public HashMap<TextField, Player> playerMap = new HashMap<>();
    private TextField src;
    private VBox target;



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

        playerController = new PlayerController(databaseConn);
        obValid = new PlayerValidator();

        fakePlayer.setsFirstName("Kyle");
        fakePlayer.setsLastName("Doerksen");
        fakePlayer.setsEmergencyContact("123-123-1234");
        fakePlayer.setnPlayerNumber(45);
        fakePlayer.setsParentInfo("Mother");

        playerMap.put(txtPlayer1, fakePlayer);
        playerMap.put(txtPlayer2, fakePlayer);
        playerMap.put(txtPlayer3, fakePlayer);
        playerMap.put(txtPlayer4, fakePlayer);
        playerMap.put(txtPlayer5, fakePlayer);
        playerMap.put(txtPlayer6, fakePlayer);
        playerMap.put(txtPlayer7, fakePlayer);



    }

    /**
     * This method is to populate the listview with all available players that have their sTeamName set as " "
     *
     * @param
     * @return
     */
    public void populateListView()
    {
        ObservableList<Player> ListOfPlayer = FXCollections.observableArrayList(playerControl.getAllPlayers());

//        ListOfPlayer = (ObservableList<Player>) ListOfPlayer.stream().filter(x -> x.getsTeamName().equals("")).collect(Collectors.toList());
        PlayerList.getItems().addAll(ListOfPlayer);

    }

    /**
     * This method is to add the selected player from the listview to the flowpane (bench) at the top, assigning the player a team by setting the
     * sTeamName in player object to be what the team value is from the selected dropdown before
     */
    public void addPlayerToTeam()
    {
        Player obSelected = PlayerList.getSelectionModel().getSelectedItem();
        Text obPlayer = new Text();
        obPlayer.setText(obSelected.toString());
        obPlayer.setOnDragDetected(event -> {setOnDragDetected((MouseDragEvent) event);});
        obPlayer.setOnDragOver(event -> {setOnDragOver((MouseDragEvent)event);});
        obPlayer.setOnDragDropped(event -> {setOnDragDropped((MouseDragEvent)event);});
        obPlayer.setOnDragDone(event -> {setOnDraggedDone((MouseDragEvent)event);});
        fpBench.getChildren().add(obPlayer);

        playerMap.put(,obSelected);

        obSelected.setsTeamName(TeamViewController.obCurrentTeam);
    }

    @FXML
    public void setOnDragDetected(MouseDragEvent e)
    {


    }

    @FXML
    public void setOnDragOver(MouseDragEvent e)
    {

    }

    @FXML
    public void setOnDragDropped(MouseDragEvent e)
    {

    }

    @FXML
    public void setOnDraggedDone(MouseDragEvent e)
    {

    }

}
