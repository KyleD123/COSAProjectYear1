package controllers;

import com.cosacpmg.TeamView;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Team;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TeamViewController implements Initializable
{
    @FXML
    private ImageView btnAdd, btnSave, btnCancel, btnEdit;

    @FXML
    private TextField txtName, txtNameEdit;

    @FXML
    private ChoiceBox cBoxName;

    private Stage obMainStage = new Stage();
    private TeamController teamControl;

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        ConnectionSource databaseConn = null;
        try
        {
            databaseConn = new JdbcPooledConnectionSource("jdbc:sqlite:teams.db");

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        teamControl = new TeamController(databaseConn);
    }

    public void switchCreateScene(MouseEvent mouseEvent) throws Exception
    {
        FXMLLoader newScene = new FXMLLoader(TeamView.class.getResource("edit_team_window.fxml"));
        obMainStage = (Stage) btnSave.getScene().getWindow();
        obMainStage.setScene(new Scene(newScene.load()), 1366, 768);
        obMainStage.show();
    }

    public voide

}
