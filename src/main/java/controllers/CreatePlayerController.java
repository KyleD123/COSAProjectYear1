package controllers;
import com.cosacpmg.MainWindow;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Player;
import models.PlayerValidator;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class CreatePlayerController implements Initializable
{
    @FXML
    private ImageView btnPlayerAddSave, btnPlayerAddCancel;

    @FXML
    private TextField txtFirstAdd, txtLastAdd, txtPositionAdd, txtNumAdd, txtParentAdd, txtContactAdd;

    @FXML
    private Label lblError1, lblError2, lblError3, lblError4, lblError5, lblError6;

    private PlayerController playerController;
    private PlayerValidator obValid;

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

    public void savePlayer(MouseEvent mouseEvent) throws IOException
    {
        String sFirstName = txtFirstAdd.getText();
        String sLastName = txtLastAdd.getText();
//        String sPosition = txtPositionAdd.getText();
        int nPlayerNum = Integer.parseInt(txtNumAdd.getText());
        String sParent = txtParentAdd.getText();
        String sContact = txtContactAdd.getText();

        Player obPlayer = new Player();
        obPlayer.setsFirstName(sFirstName);
        obPlayer.setsLastName(sLastName);
//        obPlayer.setsPosition(sPosition);
        obPlayer.setnPlayerNumber(nPlayerNum);
        obPlayer.setsParentInfo(sParent);
        obPlayer.setsEmergencyContact(sContact);

        HashMap<String, String> listOfErrors = obValid.getErrors(obPlayer);

        if (listOfErrors.size() > 0)
        {
            txtFirstAdd.setText(null);
            txtLastAdd.setText(null);
//            txtPositionAdd.setText(null);
            txtNumAdd.setText(null);
            txtParentAdd.setText(null);
            txtContactAdd.setText(null);

            lblError1.setText(listOfErrors.get("sFirstName"));
            lblError2.setText(listOfErrors.get("sLastName"));
//            lblError3.setText(listOfErrors.get("sPosition"));
            lblError4.setText(listOfErrors.get("nPlayerNumber"));
            lblError5.setText(listOfErrors.get("sParentInfo"));
            lblError6.setText(listOfErrors.get("sEmergencyContact"));
        }

        else
        {
            lblError1.setText("");
            lblError2.setText("");
//            lblError3.setText("");
            lblError4.setText("");
            lblError5.setText("");
            lblError6.setText("");
            responsePrompt(playerController.createPlayer(obPlayer));
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
            Stage obMainStage = (Stage) btnPlayerAddCancel.getScene().getWindow();
            obMainStage.setScene(new Scene(mainLoader.load(), 1366,768));
            obMainStage.show();
        }

    }

    public void cancel(MouseEvent mouseEvent) throws IOException
    {
        FXMLLoader mainLoader =  new FXMLLoader(MainWindow.class.getResource("main-player-screen-layout.fxml"));
        Stage obMainStage = (Stage) btnPlayerAddCancel.getScene().getWindow();
        obMainStage.setScene(new Scene(mainLoader.load(), 1366,768));
        obMainStage.show();
    }
}
