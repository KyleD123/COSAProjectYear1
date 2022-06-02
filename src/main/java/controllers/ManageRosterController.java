package controllers;

import com.cosacpmg.MainWindow;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import javafx.event.Event;
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
import java.util.List;
import java.util.ResourceBundle;

/**
 * Roster "View" Controller
 */
public class ManageRosterController implements Initializable
{
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

        List<Player> lstPlayers =  playerController.getAllPlayersByTeam(TeamViewController.obCurrentTeam);

        for(Player obPlay : lstPlayers)
        {
            Text txtPlayer = new Text(obPlay.getsFirstName() + " " + obPlay.getsLastName());
            txtPlayer.setOnDragDetected(this::setOnDragDetected);
            txtPlayer.setOnDragOver(this::setOnDragOver);
            txtPlayer.setOnDragDropped(this::setOnDragDropped);
            txtPlayer.setOnDragDone(this::setOnDragDone);
            fpBench.getChildren().add(txtPlayer);
            playerMap.put(txtPlayer, obPlay);
        }
    }
    //Create instances of the other controllers to set things (Call the data controllers and set values_
    //Player Controller - Because we are updating a player ONLY

    //Drag and Drop Methods
    //Create separate Methods - Drag and Drop Amounts
    public static Text source;
    @FXML
    public void setOnDragDetected(MouseEvent e)
    {
        source = (Text) e.getSource();
        if(e.getSource() instanceof Text)
        {
            source = (Text) e.getSource();
        }
        else if(e.getSource() instanceof VBox)
        {
            source = (Text)((VBox)e.getSource()).getChildren().get(0);
        }
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
    public void setOnDragDropped(DragEvent e) {
        Player p = playerMap.get(source);
        String str = e.getDragboard().getString();

        if(e.getTarget() instanceof VBox)
        {
            if(((VBox) e.getTarget()).getChildren().size() < 1)
            {
                VBox vTarget = (VBox) e.getTarget();

                switch (vTarget.getId())
                {
                    case "vGoalie":
                    {
                        p.setsPosition("Goalie");
                        break;
                    }
                    case "vLeftDefense":
                    {
                        p.setsPosition("Left Defense");
                        break;
                    }
                    case "vRightDefense":
                    {
                        p.setsPosition("Right Defense");
                        break;
                    }
                    case "vLeftWing":
                    {
                        p.setsPosition("Left Wing");
                        break;
                    }
                    case "vRightWing":
                    {
                        p.setsPosition("Right Wing");

                        break;
                    }
                    case "vCenter":
                    {
                        p.setsPosition("Center");
                        break;
                    }
                }
                if(playerController.modifyPlayer(p))
                {
                    vTarget.getChildren().add(source);
                }
            }

        }
        else
        {
            p.setsPosition(null);
            if(playerController.modifyPlayer(p))
            {
                fpBench.getChildren().add(source);
            }
        }
    }

    @FXML
    public void setOnDragDone(DragEvent e)  {
        //Does nothing
    }

}
