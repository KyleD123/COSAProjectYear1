package com.cosacpmg;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import controllers.GameController;
import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.*;
import models.Game;
import models.Team;
import models.Tournament;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class MainWindow extends Application
{
    public static final String CONNECT_STRING = "jdbc:sqlite:eSchedule.db";
    private ConnectionSource dbConn;

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        initDB();
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("main-window-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 700);
        stage.setTitle("eSchedule");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    protected void initDB() throws SQLException {
        //create db connection
        dbConn = new JdbcPooledConnectionSource(CONNECT_STRING);
        GameController obGameControl = new GameController(dbConn);
    }

    public static void main(String[] args) {
        launch();
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

    @FXML
    public void populateScheduleTable(javafx.event.ActionEvent actionEvent)
    {

    }

}
