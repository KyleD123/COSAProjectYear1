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
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
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
    private ListView<Player> PlayerList;

    @FXML
    private FlowPane fpBench;

    private PlayerController playerControl;

    public HashMap<Text, Player> playerMap = new HashMap<>();

    //only need this method if we are repopulating the team list when re-selecting a team
    private List<Player> listOfPlayerOnTeam = new ArrayList<>();
    private ArrayList<String> selectedPlayers = new ArrayList<>();

    private Text txtPlayer;



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
        //only need this method if we are repopulating the team list when re-selecting a team
      populateTeamList();

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
     *
     * @param
     * @return
     */
    public void addPlayerToTeam() throws SQLException {

        Player obSelected = PlayerList.getSelectionModel().getSelectedItem();
        obSelected.setsTeamName(TeamViewController.obCurrentTeam);
        if(playerControl.modifyPlayer(obSelected))
        {
            txtPlayer = new Text();
            txtPlayer.setText(obSelected.toString());
            txtPlayer.setOnDragDetected(this::setOnDragDetected);
            txtPlayer.setOnDragOver(this::setOnDragOver);
            txtPlayer.setOnDragDropped(this::setOnDragDropped);
            txtPlayer.setOnDragDone(this::setOnDraggedDone);
            txtPlayer.setOnMouseClicked(e -> {setOnMouseClicked(e,txtPlayer);});

            fpBench.getChildren().add(txtPlayer);
            fpBench.setHgap(25);
            playerMap.put(txtPlayer,obSelected);
            populateListView();
            //only need this if we are repopulating the team list when re-selected a team
            listOfPlayerOnTeam.add(obSelected);
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

    private void setOnMouseClicked(MouseEvent mouseEvent, Text txtPlayer)
    {
        System.out.println("Gabe is shit");
        txtPlayer.setFill(Color.RED);
        selectedPlayers.add(txtPlayer.getText());


    }

    /**
     * This method is used to remove a player from a team, setting the sTeamName to be null or ""
     *
     * @param
     * @return
     */
    public void removePlayerFromTeam()
    {

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

    public void populateTeamList()
    {
        listOfPlayerOnTeam = playerControl.getAllPlayers().stream().filter(x -> x.getsTeamName().equals(TeamViewController.obCurrentTeam.toString())).collect(Collectors.toList());
        for (int i = 0; i<listOfPlayerOnTeam.size(); i++)
        {
            if (listOfPlayerOnTeam.get(i).getsTeamName().equals(TeamViewController.obCurrentTeam))
            {
                Player obSelected = listOfPlayerOnTeam.get(i);
                Text txtPlayer = new Text();
                txtPlayer.setText(obSelected.toString());

                txtPlayer.setOnDragDetected(this::setOnDragDetected);
                txtPlayer.setOnDragOver(this::setOnDragOver);
                txtPlayer.setOnDragDropped(this::setOnDragDropped);
                txtPlayer.setOnDragDone(this::setOnDraggedDone);
                txtPlayer.setOnMouseClicked(e -> {setOnMouseClicked(e,txtPlayer);});

                fpBench.getChildren().add( txtPlayer);
                playerMap.put(txtPlayer,obSelected);
            }
        }
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

    @FXML
    public void setOnDragDetected(MouseEvent event)
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
