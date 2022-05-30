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
import javafx.scene.layout.VBox;
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
    private TextField txtPlayer1,txtPlayer2,txtPlayer3,txtPlayer4,txtPlayer5, txtPlayer6, txtPlayer7,txtPlayer8,txtPlayer9,txtPlayer10,txtPlayer11,txtPlayer12,txtPlayer13,txtPlayer14,txtPlayer15;

    @FXML
    private VBox vGoalie, vLeftDefense, vRightDefense, vLeftWing, vRightWing, vCenter;

    private Player fakePlayer = new Player();

    private PlayerController playerController;
    private PlayerValidator obValid;

    private Player obCurrentPlayer;

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

        txtPlayer1.setText(playerMap.get(txtPlayer1).getsFirstName() + ", " + playerMap.get(txtPlayer1).getsLastName());
        txtPlayer2.setText(playerMap.get(txtPlayer2).getsFirstName() + ", " + playerMap.get(txtPlayer2).getsLastName());
        txtPlayer3.setText(playerMap.get(txtPlayer3).getsFirstName() + ", " + playerMap.get(txtPlayer3).getsLastName());
        txtPlayer4.setText(playerMap.get(txtPlayer4).getsFirstName() + ", " + playerMap.get(txtPlayer4).getsLastName());
        txtPlayer5.setText(playerMap.get(txtPlayer5).getsFirstName() + ", " + playerMap.get(txtPlayer5).getsLastName());
        txtPlayer6.setText(playerMap.get(txtPlayer6).getsFirstName() + ", " + playerMap.get(txtPlayer6).getsLastName());
        txtPlayer7.setText(playerMap.get(txtPlayer7).getsFirstName() + ", " + playerMap.get(txtPlayer7).getsLastName());

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

    public void addPlayerToTeam()
    {

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
