package controllers;

import com.cosacpmg.TeamView;
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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class CreateTournamentController implements Initializable
{

    @FXML
    private ImageView btnAdd, cancelBtn, btnEdit;





    @FXML
    private TextField txtName;

    @FXML
    private Label lblAddExist;



    private TeamController teamController;
    private TeamValidator obValid;

    /**
     * When initiating the CreateTournamentController, this method initializes:
     * the database connection, TournamentController, and the TournamentValidator beforehand so it is ready to be used
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        ConnectionSource dbConn = null;
        try {
            dbConn = new JdbcPooledConnectionSource("jdbc:sqlite:teams.db");
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
     * If it is valid, it creates the tournament and assuming all went well (if the dates are not conflicting each other), it shows up a message
     * that it is successful!
     *
     * If it is not valid, it removes all and tells the user what errors they made so they can correct it.
     *
     * @param mouseEvent
     * @throws IOException
     */
    public void saveTournament(MouseEvent mouseEvent) throws IOException {
        try
        {


            Team obTemp = new Team();
            obTemp.setTeamName("Rosetown Giants");
            obTemp.setTeamName("Grant O'Brian");
            obTemp.setTeamID(1);


            HashMap<String, String> listOfErrors = obValid.getErrors(obTemp);

            //If the annotations have errors, then it shows what kind of error you made.
            if (listOfErrors.size() > 0)
            {




                lblAddExist.setText("TEAM NAME EXISTS");

            }

            //if all works, then lets send it to the database, show a prompt, and when you click close, and we go back to the mainTournamentWindow
            else
            {
                lblAddExist.setText("");
                responsePrompt(teamController.createTeam(obTemp));
            }
        }
        catch (NullPointerException exp)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error has occured");
            alert.setHeaderText(null);
            alert.setContentText("Something went wrong with the database. Either the connection dropped or the one of the fields is missing. Press OK to cancel");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
            {
                FXMLLoader mainLoader =  new FXMLLoader(TeamView.class.getResource("mainTournamentLayout.fxml"));
                Stage obMainStage = (Stage) cancelBtn.getScene().getWindow();
                obMainStage.setScene(new Scene(mainLoader.load(), 1366,768));
                obMainStage.show();
            }
        }


    }

    /**
     * This is a mouse click event handler to go back to the main tournament window.
     * @param mouseEvent
     * @throws IOException
     */
    public void cancel(MouseEvent mouseEvent) throws IOException
    {
        FXMLLoader mainLoader =  new FXMLLoader(TeamView.class.getResource("mainTournamentLayout.fxml"));
        Stage obMainStage = (Stage) cancelBtn.getScene().getWindow();
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
            alert.setContentText("The entry entry has already existed. Check your start date and end date if it is filled with a certain tournament. Press OK to cancel");
        }

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            FXMLLoader mainLoader =  new FXMLLoader(TeamView.class.getResource("mainTournamentLayout.fxml"));
            Stage obMainStage = (Stage) cancelBtn.getScene().getWindow();
            obMainStage.setScene(new Scene(mainLoader.load(), 1366,768));
            obMainStage.show();
        }

    }
}
