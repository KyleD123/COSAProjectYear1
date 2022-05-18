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
        FXMLLoader mainLoader = new FXMLLoader(this.getClass().getResource("team_window.fxml"));
        obMainScene = new Scene(mainLoader.load());
        this.obMainStage.setTitle("eSchedule - Team");
        this.obMainStage.setScene(obMainScene);
        this.obMainStage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }

}
