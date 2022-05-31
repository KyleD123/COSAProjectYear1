package controllers;

import com.cosacpmg.MainWindow;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.Player;
import models.PlayerValidator;


import java.io.IOException;
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
    private Text txtPlayer1,txtPlayer2;

    @FXML
    private FlowPane fpBench;

    @FXML
    private VBox vGoalie, vLeftDefense, vRightDefense, vLeftWing, vRightWing, vCenter;
    private VBox[] positions = {vGoalie, vLeftDefense, vRightDefense, vLeftWing, vRightWing, vCenter};

    private Player fakePlayer = new Player();

    private PlayerController playerController;

    private Player obCurrentPlayer;

    public HashMap<Text, Player> playerMap = new HashMap<>();

    private VBox target = new VBox();

    private PlayerValidator obValid;

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


        txtPlayer1.setText(playerMap.get(txtPlayer1).getsFirstName() + ", " + playerMap.get(txtPlayer1).getsLastName());
        txtPlayer2.setText(playerMap.get(txtPlayer2).getsFirstName() + ", " + playerMap.get(txtPlayer2).getsLastName());

    }
    //Create instances of the other controllers to set things (Call the data controllers and set values_
    //Player Controller - Because we are updating a player ONLY




    //Drag and Drop Methods
    //Create separate Methods - Drag and Drop Amounts

    @FXML
    public void setOnDragDetected(MouseDragEvent e)
    {
//        e.getSource();
//        e.getTarget();
        VBox vTarget = (VBox) e.getTarget();
        Text source = (Text) e.getSource();
        Dragboard db = source.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        content.putString(source.getText());
        db.setContent(content);
        e.consume();

    }

    @FXML
    public void setOnDragOver(DragEvent e)
    {
        if(e.getDragboard().hasString()){
            e.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    public void setOnDragDropped(DragEvent e)
    {
        VBox vTarget = (VBox) e.getTarget();
        Text source = (Text) e.getSource();
        Player p = playerMap.get(source);
        String str = e.getDragboard().getString();
//        target.getChildren().add(new Text(str));

        switch (vTarget.getId())
        {
            case "vGoalie":
            {
                p.setsPosition("Goalie");
                source.setText(str);
                vGoalie.getChildren().add(source);
                target = vGoalie;
                break;
            }
            case "vLeftDefense":
            {
                p.setsPosition("Left Defense");
                source.setText(str);
                vLeftDefense.getChildren().add(source);
                target = vLeftDefense;
                break;
            }
            case "vRightDefense":
            {
                p.setsPosition("Right Defense");
                source.setText(str);
                vRightDefense.getChildren().add(source);
                target = vRightDefense;
                break;
            }
            case "vLeftWing":
            {
                p.setsPosition("Left Wing");
                source.setText(str);
                vLeftWing.getChildren().add(source);
                target = vLeftWing;
                break;
            }
            case "vRightWing":
            {
                p.setsPosition("Right Wing");
                source.setText(str);
                vRightWing.getChildren().add(source);
                target = vRightWing;
                break;
            }
            case "vCenter":
            {
                p.setsPosition("Center");
                source.setText(str);
                vCenter.getChildren().add(source);
                target = vCenter;
                break;
            }
        }

        //call controller to modify player add player object in


    }

    @FXML
    public void setOnDragDone(MouseDragEvent e) throws SQLException, IOException {
        Text txt = (Text)e.getSource();
        fpBench.getChildren().remove(txt);

        Player player = playerMap.get(txt);

        modifyPosition(player);
    }



    public void modifyPosition(Player player) throws IOException, SQLException {

        player.setsPosition(target.getId());

        HashMap<String, String> listOfErrors = obValid.getErrors(player);
        //If the annotations have errors, then it shows what kind of error you made.
        if (listOfErrors.size() > 0)
        {
            lblError3.setText(listOfErrors.get("sPosition"));
        }
        else
        {
            lblError3.setText("");

//            responsePrompt(playerController.modifyPlayer(player));
            
        }
    }

}
