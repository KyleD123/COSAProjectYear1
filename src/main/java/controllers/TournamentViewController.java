package controllers;

import com.cosacpmg.TournamentView;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Tournament;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class TournamentViewController implements Initializable
{
    @FXML
    private ImageView btnAdd, cancelBtn, btnEdit;

    @FXML
    private DatePicker txtStart;

    @FXML
    private DatePicker txtEnd;

    @FXML
    private DatePicker txtStartEdit;

    @FXML
    private DatePicker txtEndEdit;

    @FXML
    private TextField txtName, txtNameEdit;

    @FXML
    private ChoiceBox cBoxIDs;


    private Stage obMainStage  = new Stage();

    private TournamentController tournControl;

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        ConnectionSource dbConn = null;
        try {
            dbConn = new JdbcPooledConnectionSource("jdbc:sqlite:tournaments.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tournControl = new TournamentController(dbConn);
    }


    public void switchCreateScene(MouseEvent mouseEvent) throws Exception
    {

        FXMLLoader newScene = new FXMLLoader(TournamentView.class.getResource("addTournamentScreenLayout.fxml"));
        obMainStage = (Stage) btnAdd.getScene().getWindow();
        obMainStage.setScene(new Scene(newScene.load(), 1366,768));
        obMainStage.show();
    }

    public void switchModifyScene(MouseEvent mouseEvent) throws Exception
    {
        FXMLLoader newScene = new FXMLLoader(TournamentView.class.getResource("modifyTournamentScreenLayout.fxml"));
        obMainStage = (Stage) btnEdit.getScene().getWindow();
        obMainStage.setScene(new Scene(newScene.load(), 1366,768));
        obMainStage.show();
    }

    public void populateDropDownMenu()
    {
        List<Tournament> list = tournControl.getAllTournament();
    }
}
