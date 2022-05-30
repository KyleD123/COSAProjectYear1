package controllers;

import com.cosacpmg.MainWindow;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Tournament;

import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class TournamentViewController implements Initializable
{
    @FXML
    private ImageView btnAdd, cancelBtn, btnEdit;

    @FXML
    private ComboBox cBoxIds;

    @FXML
    private Label txtTournamentName, txtStartDate, txtEndDate, txtTournamentInfoTitle;

    private Stage obMainStage  = new Stage();

    private TournamentController tournControl;

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        ConnectionSource dbConn = null;
        try {
            dbConn = new JdbcPooledConnectionSource("jdbc:sqlite:eSchedule.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tournControl = new TournamentController(dbConn);

        populateDropDownMenu();
    }


    public void switchCreateScene(MouseEvent mouseEvent) throws Exception
    {

        FXMLLoader newScene = new FXMLLoader(MainWindow.class.getResource("add-tournament-screen-layout.fxml"));
        obMainStage = (Stage) btnAdd.getScene().getWindow();
        obMainStage.setScene(new Scene(newScene.load(), 1366,768));
        obMainStage.show();
    }

    public void switchModifyScene(MouseEvent mouseEvent) throws Exception
    {
        FXMLLoader newScene = new FXMLLoader(MainWindow.class.getResource("modify-tournament-screen-layout.fxml"));
        obMainStage = (Stage) btnEdit.getScene().getWindow();
        obMainStage.setScene(new Scene(newScene.load(), 1366,768));
        obMainStage.show();
    }

    public void populateDropDownMenu()
    {

        List<Tournament> list = tournControl.getAllTournament();
        cBoxIds.getItems().addAll(list);

    }


    public void changeInformation(javafx.event.ActionEvent actionEvent) {
        int nID = Integer.parseInt(cBoxIds.getValue().toString());
        Tournament nShow = tournControl.getTournamentById((long) nID);
        txtTournamentInfoTitle.setText("Information for Tournament ID " + nID);


        Date dStart = nShow.getStartDate();
        Date dEnd = nShow.getEndDate();
        DateFormat canadianDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        txtTournamentName.setText(nShow.getTournamentName() == null ? "" : nShow.getTournamentName());
        txtStartDate.setText(canadianDateFormat.format(dStart));
        txtEndDate.setText(canadianDateFormat.format(dEnd));
    }

}
