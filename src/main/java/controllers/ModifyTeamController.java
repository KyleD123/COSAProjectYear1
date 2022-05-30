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
import models.Team;
import models.TeamValidator;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyTeamController implements Initializable {
    @FXML
    private ImageView btnAdd, btnCancel, btnEdit;

    @FXML
    private TextField txtNameEdit, txtCoachName;

    @FXML
    private Label lblTeamID;

    @FXML
    private Label lblNameExists;

    private TeamController teamController;

    private Team toBeEdited;

    private TeamValidator obValid;

    private int teamID;

    /**
     * When initiating the CreateTournamentController, this method initializes:
     * the database connection, TournamentController, and the TournamentValidator beforehand so it is ready to be used
     * @param location
     * @param resources
     */
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        ConnectionSource dbConn = null;
        try {
            dbConn = new JdbcPooledConnectionSource("jdbc:sqlite:eSchedule.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        teamController = new TeamController(dbConn);

        obValid = new TeamValidator();
    }

    /**
     * When the save button is pressed, it firstly grabs the value of the first and second DatePicker as a date, creates
     * a temporary Tournament object, sets the StartDate and EndDate based on those values (including the Name textfield's value),
     * and goes to a verification if its valid.
     *
     * If it is valid, it modifies the tournament and assuming all went well (if the dates are not conflicting each other), it shows up a message
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
            toBeEdited.setTeamName(txtNameEdit.getText());
            toBeEdited.setCoachName(txtCoachName.getText());

            HashMap<String, String> listOfErrors = obValid.getErrors(toBeEdited);

            //If the annotations have errors, then it shows what kind of error you made.
            if (listOfErrors.size() > 0)
            {
                lblNameExists.setText("Team Name Exists");
            }

            //if all works, then lets send it to the database, show a prompt, and when you click close, and we go back to the mainTournamentWindow
            else
            {
                lblNameExists.setText("");
                responsePrompt(teamController.modifyTeam(toBeEdited));
            }
        }
        catch (NullPointerException exp)
        {
            responsePrompt(false);
        }
    }

    /**
     * This is a mouse click event handler to go back to the main tournament window.
     * @param mouseEvent
     * @throws IOException
     */
    public void cancel(MouseEvent mouseEvent) throws IOException
    {
        FXMLLoader mainLoader =  new FXMLLoader(MainWindow.class.getResource("main-team-layout.fxml"));
        Stage obMainStage = (Stage) btnCancel.getScene().getWindow();
        obMainStage = (Stage) btnCancel.getScene().getWindow();
        obMainStage.setScene(new Scene(mainLoader.load(), 1366,768));
        obMainStage.show();
    }

    public void populateTextFields()
    {
        toBeEdited = teamController.getTeamByID((long)teamID);

        txtNameEdit.setText(toBeEdited.getTeamName() == null ? "" : toBeEdited.getTeamName());
        lblTeamID.setText("Editing Information for Team ID#: " + toBeEdited.getTeamID());
        txtCoachName.setText(toBeEdited.getCoachName());
    }

    public void responsePrompt(boolean status) throws IOException
    {
        Alert alert;
        if (status)
        {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successfull!");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Overwritten!");
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

    public void setID(int id)
    {
        this.teamID = id;
    }

}

