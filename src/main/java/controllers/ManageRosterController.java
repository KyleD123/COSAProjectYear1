package controllers;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.Player;
import models.PlayerValidator;


import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
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
    int nCounter =1;

    public HashMap<Text, Player> playerMap = new HashMap<>();
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


//        playerMap.put(txtPlayer1, fakePlayer);
//        playerMap.put(txtPlayer2, fakePlayer);
//        playerMap.put(txtPlayer3, fakePlayer);
//        playerMap.put(txtPlayer4, fakePlayer);
//        playerMap.put(txtPlayer5, fakePlayer);
//        playerMap.put(txtPlayer6, fakePlayer);
//        playerMap.put(txtPlayer7, fakePlayer);



    }

    /**
     * This method is to populate the listview with all available players that have their sTeamName set as " "
     *
     * @param
     * @return
     */
    public void populateListView()
    {
        PlayerList.getItems().clear();
        List<Player> ListOfPlayer = playerControl.getAllPlayers();

        ListOfPlayer=ListOfPlayer.stream().filter(x -> x.getsTeamName().equals("")).collect(Collectors.toList());
        PlayerList.getItems().addAll(ListOfPlayer);


    }

    /**
     * This method is to add the selected player from the listview to the flowpane (bench) at the top, assigning the player a team by setting the
     * sTeamName in player object to be what the team value is from the selected dropdown before
     */
    public void addPlayerToTeam() throws SQLException {
        Player obSelected = PlayerList.getSelectionModel().getSelectedItem();
        Text obPlayer = new Text();
        obPlayer.setText(obSelected.toString());
        obPlayer.setOnDragDetected(event -> {setOnDragDetected((MouseDragEvent)event);});
        obPlayer.setOnDragOver(event -> {setOnDragOver(event);});
        obPlayer.setOnDragDropped(event -> {setOnDragDropped(event);});
        obPlayer.setOnDragDone(event -> {setOnDraggedDone(event);});
        fpBench.getChildren().add(obPlayer);

        obPlayer.setId(("text"+nCounter));
        playerMap.put(obPlayer,obSelected);

        obSelected.setsTeamName(TeamViewController.obCurrentTeam);
        playerController.modifyPlayer(obSelected);
        populateListView();

        nCounter++;
    }



    public void populateTeamList()
    {

    }

    @FXML
    public void setOnDragDetected(MouseDragEvent e)
    {


    }

    @FXML
    public void setOnDragOver(DragEvent e)
    {

    }

    @FXML
    public void setOnDragDropped(DragEvent e)
    {

    }

    @FXML
    public void setOnDraggedDone(DragEvent e)
    {

    }

}
