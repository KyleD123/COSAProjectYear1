package controllers;

import com.cosacpmg.MainWindow;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
//import models.Game;
import models.Tournament;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Purpose: This is a controller for the main window layout to enable mouse actions and respond to user actions.
 * Author: Project Green
 */
public class MainWindowController implements Initializable
{

    public static final String CONNECT_STRING = MainWindow.CONNECT_STRING;
    private ConnectionSource dbConn;

//    private  GameController obGameControl;

    private TournamentController obTournamentControl;
    @FXML
    private ComboBox cmbTournamentBox;

//    @FXML
//    private TableView<Game> tblGames;

    /**
     * This initialize the MainWindowViewController for the FXML.
     * @param location
     * The location used to resolve relative paths for the root object, or
     * <tt>null</tt> if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or <tt>null</tt> if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        //create db connection
        try
        {
            dbConn = new JdbcPooledConnectionSource(CONNECT_STRING);
//            obGameControl = new GameController(dbConn);
            obTournamentControl = new TournamentController(dbConn);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        //This is where it displays all of the lists of the tournament
        List<Tournament> obList = obTournamentControl.getAllTournament();
        cmbTournamentBox.getItems().addAll(obList);

    }

    /**
     * Opens the Team Window

     * @throws Exception
     */
    @FXML
    public void openTeamWindow(MouseEvent mouseEvent) throws Exception
    {
        FXMLLoader mainLoader = new FXMLLoader(MainWindow.class.getResource("team-window.fxml"));
        Scene obMainScene = new Scene(mainLoader.load());
        Stage newWindow = new Stage();
        newWindow.setTitle("eSchedule - Team");
        newWindow.setScene(obMainScene);
        newWindow.show();
    }

    /**
     * Opens the Player Window

     * @throws Exception
     */
    @FXML
    public void openPlayerWindow(MouseEvent mouseEvent) throws Exception
    {
        FXMLLoader mainLoader = new FXMLLoader(MainWindow.class.getResource("main-player-screen-layout.fxml"));
        Scene obMainScene = new Scene(mainLoader.load());
        Stage newWindow = new Stage();
        newWindow.setTitle("eSchedule - Tournament");
        newWindow.setScene(obMainScene);
        newWindow.show();
    }

    /**
     * Opens the Tournament Window

     * @throws Exception
     */
    @FXML
    public void openTournamentWindow(MouseEvent mouseEvent) throws Exception
    {
        FXMLLoader mainLoader = new FXMLLoader(MainWindow.class.getResource("main-tournament-layout.fxml"));
        Scene obMainScene = new Scene(mainLoader.load());
        Stage newWindow = new Stage();
        newWindow.setTitle("eSchedule - Tournament");
        newWindow.setScene(obMainScene);
        newWindow.show();
    }

    /**
     * When a user select a Tournament ID, the table below is populated with the games it is associated with that very
     * tournament.
     * @param actionEvent
     * @throws SQLException
     */
//    @FXML
//    public void populateScheduleTable(javafx.event.ActionEvent actionEvent) throws SQLException
//    {
//        //CLEARS ANY CONTENT BEFORE DOING ANYTHING ELSE.
////        tblGames.getItems().clear();
////        tblGames.getItems().addAll(obGameControl.getAllScheduleByTournament((Tournament) cmbTournamentBox.getValue()));
//    }
}
