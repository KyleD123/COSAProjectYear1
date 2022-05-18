package com.cosacpmg;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import java.io.IOException;
public class PlayerView extends Application
{
    private Scene obMainScene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader mainLoader = new FXMLLoader(this.getClass().getResource("player_window.fxml"));
        obMainScene = new Scene(mainLoader.load());
        stage.setTitle("eSchedule - Player");
        stage.setScene(obMainScene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
