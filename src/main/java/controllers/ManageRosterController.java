package controllers;

import com.cosacpmg.MainWindow;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Player;



import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ManageRosterController implements Initializable
{

    @FXML
    private ListView<Player> lvPlayers;

    @FXML
    private FlowPane fpBench;

    private PlayerController playerController;

    public HashMap<Text, Player> playerMap = new HashMap<>();

//    //only need this method if we are repopulating the team list when re-selecting a team
//    private List<Player> listOfPlayerOnTeam = new ArrayList<>();

    private Text txtPlayer;

    public static Text source;




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

        playerController = new PlayerController(databaseConn);
        populateListView();

        List<Player> lstPlayers =  playerController.getAllPlayersByTeam(TeamViewController.obCurrentTeam);

        for(Player obPlay : lstPlayers)
        {
            Text txtPlayer = new Text(obPlay.getsFirstName() + " " + obPlay.getsLastName());
            txtPlayer.setOnDragDetected(this::setOnDragDetected);
            txtPlayer.setOnDragOver(this::setOnDragOver);
//            txtPlayer.setOnDragDropped(this::setOnDragDropped);
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
//            txtPlayer.setOnDragDropped(this::setOnDragDropped);
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


    /**
     * This method is used to repopulate a team once the program is closed or if someone goes back to the main
     * page, and the team is reselected to manage the roster
     * it will show all the players that are on that selected team again
     *
     * @param
     * @return
     */
    //only need this method if we are repopulating the team list when re-selecting a team

//    public void populateTeamList()
//    {
//        for (int i = 0; i<listOfPlayerOnTeam.size(); i++)
//        {
//            if (listOfPlayerOnTeam.get(i).getObTeam().equals(TeamViewController.obCurrentTeam))
//            {
//                Player obSelected = listOfPlayerOnTeam.get(i);
//                Text txtPlayer = new Text();
//                txtPlayer.setText(obSelected.toString());
//
//                txtPlayer.setOnDragDetected(this::setOnDragDetected);
//                txtPlayer.setOnDragOver(this::setOnDragOver);
////                txtPlayer.setOnDragDropped(this::setOnDragDropped);
//                txtPlayer.setOnDragDone(this::setOnDraggedDone);
//
//                fpBench.getChildren().add( txtPlayer);
//                playerMap.put(txtPlayer,obSelected);
//            }
//        }
//    }

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

    @FXML
    public void setOnDragDetected(MouseEvent event)
    {


    }

    @FXML
    public void setOnDragOver(DragEvent e)
    {

    }

    @FXML
    public void setOnDragDropped(DragEvent e) throws SQLException {
        Player p = playerMap.get(source);
    String str = e.getDragboard().getString();

        if(e.getTarget() instanceof VBox)
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
            case "vDelete":
            {
                p.setObTeam(null);
                p.setsPosition(null);
            }
        }
        if(playerController.modifyPlayer(p))
        {
            if(vTarget.getId() != "vDelete") {
                vTarget.getChildren().add(source);
            } else {
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
    public void setOnDragDone(DragEvent e)
    {

    }

}
