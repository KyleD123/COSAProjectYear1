package controllers;
import com.cosacpmg.PlayerView;
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
import java.util.ResourceBundle;

public class AddPlayerController implements Initializable
{
    @FXML
    private ImageView btnPlayerAddSave, btnPlayerAddCancel;

    @FXML
    private TextField txtFirstAdd, txtLastAdd, txtPositionAdd, txtNumAdd, txtParentAdd, txtContactAdd;

    private PlayerController playerController;
    private PlayerValidator obValid;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        ConnectionSource databaseConn = null;
        try {
            databaseConn = new JdbcPooledConnectionSource("jdbc:sqlite:players.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        playerController = new PlayerController(databaseConn);
    }

    public void savePlayer(MouseEvent mouseEvent) throws IOException
    {
        String sFirstName = txtFirstAdd.getText();
        String sLastName = txtLastAdd.getText();
        String sPosition = txtPositionAdd.getText();
        int nPlayerNum = Integer.parseInt(txtNumAdd.getText());
        String sParent = txtParentAdd.getText();
        String sContact = txtContactAdd.getText();

        Player obPlayer = new Player();
        obPlayer.setsFirstName(sFirstName);
        obPlayer.setsLastName(sLastName);
        obPlayer.setsPosition(sPosition);
        obPlayer.setnPlayerNumber(nPlayerNum);
        obPlayer.setsParentInfo(sParent);
        obPlayer.setsEmergencyContact(sContact);

        HashMap<String, String> listOfErrors = obValid.getErrors(obPlayer);

        if (listOfErrors.size() > 0)
        {
            txtFirstAdd.setText("");
            txtLastAdd.setText("");
            txtPositionAdd.setText("");
            txtNumAdd.setText("");
            txtParentAdd.setText("");
            txtContactAdd.setText("");

            txtFirstAdd.setPromptText(listOfErrors.get("sFirstName"));
            txtLastAdd.setPromptText(listOfErrors.get("sLastName"));
            txtPositionAdd.setPromptText(listOfErrors.get("sPosition"));
            txtNumAdd.setPromptText(listOfErrors.get("nPlayerNumber"));
            txtParentAdd.setPromptText(listOfErrors.get("sParentInfo"));
            txtContactAdd.setPromptText(listOfErrors.get("sEmergencyContact"));
        }
        else
        {
            //responsePrompt(playerController.createPlayer(obPlayer));
        }
    }
}
