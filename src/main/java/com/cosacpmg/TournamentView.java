package com.cosacpmg;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import controllers.TournamentController;
import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TournamentView extends Application
{

    private Scene obMainScene;
    private Stage obMainStage  = new Stage();


    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader mainLoader = new FXMLLoader(this.getClass().getResource("mainTournamentLayout.fxml"));
        obMainScene = new Scene(mainLoader.load());
        this.obMainStage.setTitle("eSchedule - Tournament");
        this.obMainStage.setScene(obMainScene);
        this.obMainStage.show();

    }
    public static void main(String[] args)
    {
        launch();
    }


}
