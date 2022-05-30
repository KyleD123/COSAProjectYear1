package controllers;

import com.cosacpmg.MainWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class PlayerViewController {

    @FXML
    private ImageView btnPlayerAdd, btnPlayerEdit, btnPlayerCancel;

    private Stage obMainStage = new Stage();


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
}
