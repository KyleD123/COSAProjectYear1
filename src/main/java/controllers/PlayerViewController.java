package controllers;

import com.cosacpmg.MainWindow;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class PlayerViewController implements Initializable {

    @FXML
    private ImageView btnPlayerAdd, btnPlayerEdit, btnPlayerCancel;

    private Stage obMainStage = new Stage();

    private PlayerController playerController;

    public void switchCreateScene(MouseEvent mouseEvent) throws Exception
    {
        FXMLLoader newScene = new FXMLLoader(MainWindow.class.getResource("create-player-screen-layout.fxml"));
        obMainStage = (Stage) btnPlayerAdd.getScene().getWindow();
        obMainStage.setScene(new Scene(newScene.load(), 1366, 768));
        obMainStage.show();
    }

    public void switchModifyScene(MouseEvent mouseEvent) throws Exception
    {
        FXMLLoader newScene = new FXMLLoader(MainWindow.class.getResource("player-number-entry-screen-layout.fxml"));
        obMainStage = (Stage) btnPlayerEdit.getScene().getWindow();
        obMainStage.setScene(new Scene(newScene.load(), 1366,768));
        obMainStage.show();
    }

    public void closeWindow(MouseEvent mouseEvent) throws Exception
    {
        obMainStage = (Stage) btnPlayerEdit.getScene().getWindow();
        obMainStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ConnectionSource databaseConn = null;
        try {
            databaseConn = new JdbcPooledConnectionSource(MainWindow.CONNECT_STRING);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        playerController = new PlayerController(databaseConn);

    }

}
