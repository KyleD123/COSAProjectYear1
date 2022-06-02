package controllers;

import com.cosacpmg.MainWindow;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
    private ListView<Player> lvPlayers;

    @FXML
    private VBox vGoalie, vLeftDefense, vRightDefense, vLeftWing, vRightWing, vCenter;
    private VBox[] positions = {vGoalie, vLeftDefense, vRightDefense, vLeftWing, vRightWing, vCenter};

    private Player fakePlayer = new Player();

    private PlayerController playerController;

    private Player obCurrentPlayer;

    public HashMap<Text, Player> playerMap = new HashMap<>();

    private VBox target = new VBox();

    private PlayerValidator obValid;

    private Text txtPlayer;

    public static Text source;

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
        populateListView();


        List<Player> lstPlayers =  playerController.getAllPlayersByTeam(TeamViewController.obCurrentTeam);

        //double check
        for(Player obPlay : lstPlayers)
        {
            Text txtPlayer = new Text(obPlay.getsFirstName() + " " + obPlay.getsLastName());
            txtPlayer.setOnDragDetected(this::setOnDragDetected);
            txtPlayer.setOnDragOver(this::setOnDragOver);
            txtPlayer.setOnDragDropped(this::setOnDragDropped);
            txtPlayer.setOnDragDone(this::setOnDragDone);
            fpBench.setHgap(25);
            fpBench.getChildren().add(txtPlayer);
            playerMap.put(txtPlayer, obPlay);
        }
    }

    /**
     * This method is to populate the listview with all available players that have their sTeamName set as " "
     *
     * @param
     * @return
     */
    public void populateListView()
    {
        lvPlayers.getItems().clear();
        lvPlayers.getItems().addAll(playerController.getPlayersWithoutTeams());
    }
    /**
     * This method is to add the selected player from the listview to the flowpane (bench) at the top, assigning the player a team by setting the
     * sTeamName in player object to be what the team value is from the selected dropdown before
     *
     * @param
     * @return
     */
    public void addPlayerToTeam() throws SQLException {

        Player obSelected = lvPlayers.getSelectionModel().getSelectedItem();
        obSelected.setObTeam(TeamViewController.obCurrentTeam);
        if(playerController.modifyPlayer(obSelected))
        {
            txtPlayer = new Text();
            txtPlayer.setText(obSelected.toString());
            txtPlayer.setOnDragDetected(this::setOnDragDetected);
            txtPlayer.setOnDragOver(this::setOnDragOver);
            txtPlayer.setOnDragDropped(this::setOnDragDropped);
            txtPlayer.setOnDragDone(this::setOnDragDone);

            fpBench.getChildren().add(txtPlayer);
            fpBench.setHgap(25);
            playerMap.put(txtPlayer,obSelected);
            populateListView();
            //only need this if we are repopulating the team list when re-selected a team
//            listOfPlayerOnTeam.add(obSelected);
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText(null);
            alert.setContentText("Player was not modified in the database! Please try again.");
            alert.show();
        }
    }
    //Create instances of the other controllers to set things (Call the data controllers and set values_
    //Player Controller - Because we are updating a player ONLY

    //Drag and Drop Methods
    //Create separate Methods - Drag and Drop Amounts
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
    public void setOnDragDropped(DragEvent e)  {
        Player p = playerMap.get(source);
        String str = e.getDragboard().getString();


        if(e.getGestureTarget() instanceof VBox)
        {
            VBox vTarget = (VBox) e.getGestureTarget();
            if(vTarget.getChildren().size() < 1)
            {
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
            else if(vTarget.getId().equals("vDelete")){
                p.setObTeam(null);
                p.setsPosition(null);
                if(playerController.modifyPlayer(p))
                {
                    ((Pane) source.getParent()).getChildren().remove(source);
                    populateListView();
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

    /**
     * This method is to go back to the main team window
     * @param mouseEvent
     * @throws IOException
     */
    public void goHome(MouseEvent mouseEvent) throws IOException
    {
        FXMLLoader mainLoader =  new FXMLLoader(MainWindow.class.getResource("main-team-layout.fxml"));
        Stage obMainStage = (Stage) fpBench.getScene().getWindow();
        obMainStage.setScene(new Scene(mainLoader.load(), 1366,768));
        obMainStage.show();
    }


}
