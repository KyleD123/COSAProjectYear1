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
    public static FXMLLoader mainLoader;

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("main-window-layout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1366, 768);
        this.mainLoader = fxmlLoader;
        stage.setTitle("eSchedule");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}
