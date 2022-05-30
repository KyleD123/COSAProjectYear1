package controllers;

import com.cosacpmg.MainWindow;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Player;
import models.PlayerValidator;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyPlayerController implements Initializable
{

    @FXML
    private ImageView btnModifySave, btnModifyCancel;

    @FXML
    private TextField txtFirstModify, txtLastModify, txtPositionModify, txtParentModify, txtContactModify;

    @FXML
    private Label lblError1, lblError2, lblError3, lblError4, lblError5, lblError6;

    private PlayerController playerController;
    private PlayerValidator obValid;

    private Player obPlayerEdit;

    private VBox targetBox;
    private TextField txtSource;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        ConnectionSource databaseConn = null;
        try {
            databaseConn = new JdbcPooledConnectionSource(MainWindow.CONNECT_STRING);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        playerController = new PlayerController(databaseConn);
        obValid = new PlayerValidator();
    }

    public void setPlayer(String sName)
    {
        this.obPlayerEdit = playerController.getPlayerByName(sName);
    }

    public void populateTextFields()
    {
        txtFirstModify.setText(obPlayerEdit.getsFirstName());
        txtLastModify.setText(obPlayerEdit.getsLastName());
//        txtPositionModify.setText(obPlayerEdit.getsPosition());
        txtParentModify.setText(obPlayerEdit.getsParentInfo());
        txtContactModify.setText(obPlayerEdit.getsEmergencyContact());
    }

    public void modifyPlayer(MouseEvent mouseEvent) throws IOException, SQLException {
        obPlayerEdit.setsFirstName(txtFirstModify.getText());
        obPlayerEdit.setsLastName(txtLastModify.getText());
//        obPlayerEdit.setsPosition(txtPositionModify.getText());
        obPlayerEdit.setsParentInfo(txtParentModify.getText());
        obPlayerEdit.setsEmergencyContact( txtContactModify.getText());

        HashMap<String, String> listOfErrors = obValid.getErrors(obPlayerEdit);
        //If the annotations have errors, then it shows what kind of error you made.
        if (listOfErrors.size() > 0)
        {
            txtFirstModify.setText(null);
            txtLastModify.setText(null);
            txtPositionModify.setText(null);
            txtParentModify.setText(null);
            txtContactModify.setText(null);

            lblError1.setText(listOfErrors.get("sFirstName"));
            lblError2.setText(listOfErrors.get("sLastName"));
//            lblError3.setText(listOfErrors.get("sPosition"));
            lblError4.setText(listOfErrors.get("sParentInfo"));
            lblError5.setText(listOfErrors.get("sEmergencyContact"));
        }

        else
        {
            lblError1.setText("");
            lblError2.setText("");
//            lblError3.setText("");
            lblError4.setText("");
            lblError5.setText("");
            responsePrompt(playerController.modifyPlayer(obPlayerEdit));
        }
    }

    public void responsePrompt(boolean status) throws IOException
    {
        Alert alert;
        if (status)
        {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successfull!");
            alert.setHeaderText(null);
            alert.setContentText("Successfully added!");
        }

        else
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error has occurred");
            alert.setHeaderText(null);
            alert.setContentText("The entry entry has already existed. Ensure that your entry is unique");
        }

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            FXMLLoader mainLoader =  new FXMLLoader(MainWindow.class.getResource("main-player-screen-layout.fxml"));
            Stage obMainStage = (Stage) btnModifyCancel.getScene().getWindow();
            obMainStage.setScene(new Scene(mainLoader.load(), 1366,768));
            obMainStage.show();
        }

    }

    public void cancel(MouseEvent mouseEvent) throws IOException
    {
        FXMLLoader mainLoader =  new FXMLLoader(MainWindow.class.getResource("main-player-screen-layout.fxml"));
        Stage obMainStage = (Stage) btnModifyCancel.getScene().getWindow();
        obMainStage.setScene(new Scene(mainLoader.load(), 1366,768));
        obMainStage.show();
    }





}
