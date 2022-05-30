package controllers;

import com.cosacpmg.MainWindow;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MinPlayerNumEntryController implements Initializable
{
    @FXML
    private ImageView btnPlayerNumEnter, btnPlayerNumCancel;

    @FXML
    private ComboBox cmbPlayerNum;

    private PlayerController playerController;
    private MinPlayerEntryController obMinPlayerEntry;

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
        populateListBox();
    }

    @FXML
    public void changeToEntryScene(MouseEvent mouseEvent) throws IOException
    {
        int nID = Integer.parseInt(cmbPlayerNum.getValue().toString());
        Stage obMainStage = (Stage) btnPlayerNumCancel.getScene().getWindow();
        obMainStage.close();

        FXMLLoader loader = new FXMLLoader(MainWindow.class.getResource("player-selector-screen-layout.fxml"));
        Parent root = loader.load();
        obMinPlayerEntry = loader.getController();

        obMinPlayerEntry.storeNumber(nID);
        obMinPlayerEntry.populateListWithNamesEqualToNum();
        obMainStage.setScene(new Scene(root));
        obMainStage.show();
    }

    /**
     * This is a mouse click event handler to go back to the main Player Window
     * @param mouseEvent
     * @throws IOException
     */
    public void cancel(MouseEvent mouseEvent) throws IOException
    {
        FXMLLoader mainLoader =  new FXMLLoader(MainWindow.class.getResource("main-player-screen-layout.fxml"));
        Stage obMainStage = (Stage) btnPlayerNumCancel.getScene().getWindow();
        obMainStage.setScene(new Scene(mainLoader.load(), 1366,768));
        obMainStage.show();
    }

    public void populateListBox()
    {
        List<Integer> list = playerController.getAllPlayers().stream().map(x -> x.getnPlayerNumber()).sorted().distinct().collect(Collectors.toList());
        cmbPlayerNum.getItems().addAll(list);
    }
}
