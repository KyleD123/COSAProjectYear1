package controllers;

import com.cosacpmg.MainWindow;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Player;
import models.Team;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class TeamViewController implements Initializable
{
    @FXML
    private ImageView btnAdd, btnSave, btnCancel, btnEdit;

    @FXML
    private TextField txtName, txtNameEdit;

    @FXML
    private ComboBox cboName;

    @FXML
    private Label lblTeamID;

    private Stage obMainStage = new Stage();
    private TeamController teamControl;
    private PlayerController playerControl;

    private String sValue;

    public static String obCurrentTeam;



    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        ConnectionSource databaseConn = null;
        try
        {
            databaseConn = new JdbcPooledConnectionSource(MainWindow.CONNECT_STRING);

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        teamControl = new TeamController(databaseConn);
        playerControl = new PlayerController(databaseConn);
        populateDropDownMenu();
    }


    public void switchCreateScene(MouseEvent mouseEvent) throws Exception
    {
        FXMLLoader newScene = new FXMLLoader(MainWindow.class.getResource("add-main-team-layout.fxml"));
        obMainStage = (Stage) btnAdd.getScene().getWindow();
        obMainStage.setScene(new Scene(newScene.load(), 1366, 768));
        obMainStage.show();
    }

    public void switchModifyScene(MouseEvent mouseEvent) throws Exception
    {
        FXMLLoader newScene = new FXMLLoader(MainWindow.class.getResource("edit-main-team-layout.fxml"));
        obMainStage = (Stage) btnEdit.getScene().getWindow();
        obMainStage.setScene(new Scene(newScene.load(), 1366,768));
        obMainStage.show();

        ModifyTeamController obControl = newScene.getController();
        String sValue = cboName.getValue().toString();

        obControl.setID(Integer.parseInt(sValue.substring(sValue.indexOf(":") + 2)));

        obControl.populateTextFields();
    }

    public void switchRosterScene(MouseEvent mouseEvent) throws Exception
    {
        FXMLLoader newScene = new FXMLLoader(MainWindow.class.getResource("manage-roster-layout.fxml"));
//        obMainStage = (Stage) btnRoster.getScene().getWindow();
        obMainStage.setScene(new Scene(newScene.load(), 1366, 768));
        obMainStage.show();

        sValue = cboName.getValue().toString();
        obCurrentTeam = sValue;


        ObservableList<Player> ListOfPlayer = FXCollections.observableArrayList(playerControl.getAllPlayers());

//        lstPlayers.setItems(ListOfPlayer);

        ListView<Player> lstPlayers = new ListView<Player>(ListOfPlayer);
        lstPlayers.setItems(ListOfPlayer);

    }

    public void populateDropDownMenu()
    {
        List<Team> list = teamControl.getAllTeam();
        cboName.getItems().addAll(list);
    }

    public void changeInformation(javafx.event.ActionEvent actionEvent) {
        long nID = Integer.parseInt(cboName.getValue().toString());
        Team nShow = teamControl.getTeamByID((long) nID);
        lblTeamID.setText("" + nID);
    }

    public void cancel(MouseEvent mouseEvent) throws IOException
    {
//        FXMLLoader mainLoader =  new FXMLLoader(TeamView.class.getResource("main-team-layout.fxml"));
        obMainStage = (Stage) btnCancel.getScene().getWindow();
//        obMainStage.setScene(new Scene(mainLoader.load(), 1366,768));
        obMainStage.close();
    }


}
