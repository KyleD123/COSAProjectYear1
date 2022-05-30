package controllers;

import com.cosacpmg.MainWindow;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import models.Player;
import models.PlayerValidator;


import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Roster "View" Controller
 */
public class ManageRosterController implements Initializable
{
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
    public void initialize(URL location, ResourceBundle resources)
    {
        ConnectionSource databaseConn = null;
        try {
            databaseConn = new JdbcPooledConnectionSource(MainWindow.CONNECT_STRING);
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
    //Create instances of the other controllers to set things (Call the data controllers and set values_
    //Player Controller - Because we are updating a player ONLY




    //Drag and Drop Methods
    //Create separate Methods - Drag and Drop Amounts


    //** Current Impediment is figuring out which source the end user would be grabbing from **
    @FXML
    public void setOnDragDetected(MouseEvent mouseEvent)
    {
       // Dragboard dragboard = source.startDragAndDrop(TransferMode.ANY);

    }

    @FXML
    public void setOnDragOver(MouseEvent mouseEvent)
    {

    }

    @FXML
    public void setOnDragDropped(MouseEvent mouseEvent)
    {

    }

    @FXML
    public void setOnDraggedDone(MouseDragEvent mouseEvent)
    {

    }



}
