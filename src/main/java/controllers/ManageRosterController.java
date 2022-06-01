package controllers;

import com.cosacpmg.MainWindow;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
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

    @FXML
    private ImageView Home;

    int nCounter =1;

    private PlayerController playerControl;

    public HashMap<Text, Player> playerMap = new HashMap<>();

    List<Player> listOfPlayerOnTeam = new ArrayList<>();



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
     */
    public void addPlayerToTeam() throws SQLException {
        Player obSelected = PlayerList.getSelectionModel().getSelectedItem();
        Text obPlayer = new Text();
        obPlayer.setText(obSelected.toString());
        obPlayer.setOnDragDetected(event -> {setOnDragDetected((MouseDragEvent)event);});
        obPlayer.setOnDragOver(event -> {setOnDragOver(event);});
        obPlayer.setOnDragDropped(event -> {setOnDragDropped(event);});
        obPlayer.setOnDragDone(event -> {setOnDraggedDone(event);});
        obPlayer.setId(("text" + nCounter));

        fpBench.getChildren().add(obPlayer);
        playerMap.put(obPlayer,obSelected);

        obSelected.setsTeamName(TeamViewController.obCurrentTeam);
        playerControl.modifyPlayer(obSelected);
        populateListView();

        listOfPlayerOnTeam.add(obSelected);

        nCounter++;
    }

    public void removePlayerFromTeam()
    {

    }


    /**
     * This method is used to repopulate a team once the program is closed or if someone goes back to the main
     * page, it will show all the players that are on that selected team again once selected
     */
    public void populateTeamList()
    {
        listOfPlayerOnTeam = playerControl.getAllPlayers().stream().filter(x -> x.getsTeamName().equals(TeamViewController.obCurrentTeam.toString())).collect(Collectors.toList());
        for (int i = 0; i<listOfPlayerOnTeam.size(); i++)
        {
            if (listOfPlayerOnTeam.get(i).getsTeamName().equals(TeamViewController.obCurrentTeam))
            {
                Player obSelected = listOfPlayerOnTeam.get(i);
                Text obPlayer = new Text();
                obPlayer.setText(obSelected.toString());
                obPlayer.setOnDragDetected(event -> {setOnDragDetected((MouseDragEvent)event);});
                obPlayer.setOnDragOver(event -> {setOnDragOver(event);});
                obPlayer.setOnDragDropped(event -> {setOnDragDropped(event);});
                obPlayer.setOnDragDone(event -> {setOnDraggedDone(event);});
                obPlayer.setId(("text"+nCounter));

                fpBench.getChildren().add(obPlayer);
                playerMap.put(obPlayer,obSelected);
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
