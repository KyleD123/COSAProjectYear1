package controllers;
import com.cosacpmg.MainWindow;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class CreateTeamController implements Initializable
{
    @FXML
    private ImageView btnAdd, btnCancel, btnEdit;

    @FXML
    private TextField txtName, txtCoachName;

    @FXML
    private Label error1;

    private TeamController teamController;
    private TeamValidator obValid;

    /**
     * When initiating the CreateTournamentController, this method initializes:
     * the database connection, TournamentController, and the TournamentValidator beforehand so it is ready to be used
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ConnectionSource dbConn = null;
        try {
            dbConn = new JdbcPooledConnectionSource(MainWindow.CONNECT_STRING);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        teamController = new TeamController(dbConn);
        obValid = new TeamValidator();
    }

    /**
     * When the save button is pressed, it firstly grabs the value of the first and second DatePicker as a date, creates
     * a temporary Team object, sets the StartDate and EndDate based on those values (including the Name textfield's value),
     * and goes to a verification if its valid.
     *
     * If it is valid, it creates the team and assuming all went well (if the dates are not conflicting each other), it shows up a message
     * that it is successful!
     *
     * If it is not valid, it removes all and tells the user what errors they made so they can correct it.
     *
     * @param mouseEvent
     * @throws IOException
     */
    public void saveTeam(MouseEvent mouseEvent) throws IOException
    {
        try
        {
            Team obTemp = new Team();
            obTemp.setCoachName(txtCoachName.getText());
            obTemp.setTeamName(txtName.getText());

            HashMap<String, String> listOfErrors = obValid.getErrors(obTemp);

            //If the annotations have errors, then it shows what kind of error you made.
            if (listOfErrors.size() > 0)
            {
                error1.setText(listOfErrors.get("sTeamName"));
            }
            else
            {
                error1.setText("");
                responsePrompt(teamController.createTeam(obTemp));
            }
        }
        catch (NullPointerException exp)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error has occured");
            alert.setHeaderText(null);
            alert.setContentText("Something went wrong with the database. Press OK to cancel");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
            {
                FXMLLoader mainLoader =  new FXMLLoader(MainWindow.class.getResource("main-team-layout.fxml"));
                Stage obMainStage = (Stage) btnCancel.getScene().getWindow();
                obMainStage.setScene(new Scene(mainLoader.load(), 1366,768));
                obMainStage.show();
            }
        }
    }

    /**
     * This is a mouse click event handler to go back to the main team window.
     * @param mouseEvent
     * @throws IOException
     */
    public void cancel(MouseEvent mouseEvent) throws IOException
    {
        FXMLLoader mainLoader =  new FXMLLoader(MainWindow.class.getResource("main-team-layout.fxml"));
        Stage obMainStage = (Stage) btnCancel.getScene().getWindow();
        obMainStage.setScene(new Scene(mainLoader.load(), 1366,768));
        obMainStage.show();
    }

    /**
     * This is a generic boolean based response prompt. It checks if its true and if its false.
     * Each true and false case has its own alert content differently.
     *
     * When we pressed yes, we go back to the main tournament view.
     * @throws IOException
     */
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
            alert.setTitle("Error has occured");
            alert.setHeaderText(null);
            alert.setContentText("The Team entry has already existed. Press OK to cancel");
        }

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            FXMLLoader mainLoader =  new FXMLLoader(MainWindow.class.getResource("main-team-layout.fxml"));
            Stage obMainStage = (Stage) btnCancel.getScene().getWindow();
            obMainStage.setScene(new Scene(mainLoader.load(), 1366,768));
            obMainStage.show();
        }

    }


}
