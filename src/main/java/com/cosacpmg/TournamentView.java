package com.cosacpmg;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
import java.io.IOException;
import java.sql.SQLException;

public class TournamentView extends Application
{

    private Scene obMainScene;



    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader mainLoader = new FXMLLoader(this.getClass().getResource("main-tournament-layout.fxml"));
        obMainScene = new Scene(mainLoader.load());
        stage.setTitle("eSchedule - Tournament");
        stage.setScene(obMainScene);
        stage.setResizable(false);
        stage.show();

    }
    public static void main(String[] args)
    {
        launch();
    }


}
