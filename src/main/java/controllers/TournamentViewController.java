package controllers;

import com.cosacpmg.MainWindow;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Tournament;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class TournamentViewController implements Initializable {
    @FXML
    private ImageView btnAdd, cancelBtn, btnEdit, btnCreateSchedule;

    @FXML
    private ComboBox cBoxIds;

    @FXML
    private Label txtTournamentName, txtStartDate, txtEndDate, txtTournamentInfoTitle;

    private Stage obTournStage = new Stage();

    private TournamentController tournControl;

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ConnectionSource dbConn = null;
        try {
            dbConn = new JdbcPooledConnectionSource(MainWindow.CONNECT_STRING);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tournControl = new TournamentController(dbConn);

        populateDropDownMenu();
    }


    public void switchCreateScene(MouseEvent mouseEvent) throws Exception {

        FXMLLoader newScene = new FXMLLoader(MainWindow.class.getResource("add-tournament-screen-layout.fxml"));
        obTournStage = (Stage) btnAdd.getScene().getWindow();
        obTournStage.setScene(new Scene(newScene.load(), 1366, 768));
        obTournStage.show();
    }

    public void switchModifyScene(MouseEvent mouseEvent) throws Exception {
        FXMLLoader newScene = new FXMLLoader(MainWindow.class.getResource("modify-tournament-screen-layout.fxml"));
        obTournStage = (Stage) btnEdit.getScene().getWindow();
        obTournStage.setScene(new Scene(newScene.load(), 1366, 768));
        obTournStage.show();
    }

    public void populateDropDownMenu() {

        cBoxIds.getItems().clear();
        List<Tournament> list = tournControl.getAllTournament();
        cBoxIds.getItems().addAll(list);

    }


    public void changeInformation(javafx.event.ActionEvent actionEvent)
    {
        Tournament nShow = tournControl.getTournamentById((long) ((Tournament) cBoxIds.getValue()).getnTournamentID());
        txtTournamentInfoTitle.setText("Information for Tournament ID " + nShow.getnTournamentID());

        Date dStart = nShow.getdStartDate();
        Date dEnd = nShow.getdEndDate();
        DateFormat canadianDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        txtTournamentName.setText(nShow.getsTournamentName() == null ? "" : nShow.getsTournamentName());
        txtStartDate.setText(canadianDateFormat.format(dStart));
        txtEndDate.setText(canadianDateFormat.format(dEnd));

        if (btnCreateSchedule.isVisible() == false)
        {
            btnCreateSchedule.setVisible(true);
        }
    }

    public void switchCreateScheduleScene() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("create-game-screen-layout.fxml"));
        obTournStage = (Stage) btnCreateSchedule.getScene().getWindow();
        obTournStage.setScene(new Scene(fxmlLoader.load(), 1366,768));
        obTournStage.show();
        obTournStage.setOnCloseRequest(e -> {
            try {
                ((MainWindowController) MainWindow.mainLoader.getController()).populateScheduleTable(null);


            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Tournament tournSelected = tournControl.getTournamentById((long) ((Tournament) cBoxIds.getValue()).getnTournamentID());

        CreateGameController obPassInControl = fxmlLoader.getController();
        obPassInControl.setTournamentReference(tournSelected);
        obPassInControl.initalizeLayoutContents();
    }

}
