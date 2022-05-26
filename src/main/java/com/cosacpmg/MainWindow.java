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
    private static Stage obStage;
    @Override
    public void start(Stage stage) throws IOException {
        this.obStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("main-window-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 700);
        stage.setTitle("eSchedule");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
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
        FXMLLoader mainLoader = new FXMLLoader(PlayerView.class.getResource("main-player-screen-layout.fxml"));
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
        FXMLLoader mainLoader = new FXMLLoader(TournamentView.class.getResource("main-tournament-layout.fxml"));
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
