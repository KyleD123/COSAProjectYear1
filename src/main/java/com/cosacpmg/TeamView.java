package com.cosacpmg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class TeamView extends Application
{
    private Scene obMainScene;

    private Stage obMainStage = new Stage();


    @Override
    public void start(Stage stage) throws IOException, SQLException
    {
        FXMLLoader mainLoader = new FXMLLoader(this.getClass().getResource("team-window.fxml"));
        obMainScene = new Scene(mainLoader.load());
        stage.setTitle("eSchedule - Team");
        stage.setScene(obMainScene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }

}
