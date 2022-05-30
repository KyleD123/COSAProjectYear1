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

    public static final String CONNECT_STRING = "jdbc:sqlite:eSchedule.db";
    private static Stage obStage;
    @Override
    public void start(Stage stage) throws IOException {
        this.obStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("main-window-layout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1366, 768);
        stage.setTitle("eSchedule");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }



}
