package controllers;

import com.cosacpmg.PlayerView;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Player;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MinPlayerEntryController implements Initializable
{
    @FXML
    private ImageView btnPlayerSelectEnter, btnPlayerSelectCancel;

    @FXML
    private ComboBox cboPlayers;

    private PlayerController playerController;

    private int playerNumber;

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        ConnectionSource databaseConn = null;
        try {
            databaseConn = new JdbcPooledConnectionSource("jdbc:sqlite:eSchedule.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        playerController = new PlayerController(databaseConn);
        populateListWithNamesEqualToNum();

    }

    public void storeNumber(int num)
    {
        this.playerNumber = num;
    }

    public void populateListWithNamesEqualToNum()
    {
        List<Player> obPlayers = playerController.getPlayerByNumber(playerNumber);
        List<String> obNamesMatch = obPlayers.stream().map(x -> x.getsFirstName() + " " + x.getsLastName()).collect(Collectors.toList());
        cboPlayers.getItems().addAll(obNamesMatch);
    }

    public void changeToEditScene(MouseEvent mouseEvent) throws IOException
    {

        String nName = cboPlayers.getValue().toString();
        Stage obMainStage = (Stage) btnPlayerSelectEnter.getScene().getWindow();
        obMainStage.close();

        FXMLLoader loader = new FXMLLoader(PlayerView.class.getResource("modify-player-screen-layout.fxml"));
        Parent root = loader.load();
        ModifyPlayerController obPlayerEntry = loader.getController();

        obPlayerEntry.setPlayer(nName);
        obPlayerEntry.populateTextFields();
        obMainStage.setScene(new Scene(root));
        obMainStage.show();
    }

    public void cancel(MouseEvent mouseEvent) throws IOException
    {
        FXMLLoader mainLoader =  new FXMLLoader(PlayerView.class.getResource("main-player-screen-layout.fxml"));
        Stage obMainStage = (Stage) btnPlayerSelectEnter.getScene().getWindow();
        obMainStage.setScene(new Scene(mainLoader.load(), 1366,768));
        obMainStage.show();
    }




}
