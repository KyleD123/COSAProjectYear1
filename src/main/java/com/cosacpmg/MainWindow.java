package com.cosacpmg;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.*;
import java.io.IOException;
public class MainWindow extends Application
{
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("main-window-layout.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("eSchedule");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @FXML
    public void openTeamWindow(MouseEvent mouseEvent) throws Exception
    {
        FXMLLoader mainLoader = new FXMLLoader(TeamView.class.getResource("team-window.fxml"));
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
        System.out.println("Bruh");
    }

    /**
     * Opens the Tournament Window

     * @throws Exception
     */
    @FXML
    public void openTournamentWindow(MouseEvent mouseEvent) throws Exception
    {
//        FXMLLoader mainLoader = new FXMLLoader(TournamentView.class.getResource("main-tournament-layout.fxml"));
//        Scene obMainScene = new Scene(mainLoader.load());
//        Stage newWindow = new Stage();
//        newWindow.setTitle("eSchedule - Tournament");
//        newWindow.setScene(obMainScene);
//        newWindow.show();
    }
}
