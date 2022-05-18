package controllers;

import com.cosacpmg.TournamentView;
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
import models.Tournament;
import models.TournamentValidator;

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
    private DatePicker txtStart;

    @FXML
    private DatePicker txtEnd;

    @FXML
    private TextField txtName;

    @FXML
    private Label lblError1;

    @FXML
    private Label lblError2;

    @FXML
    private Label lblError3;

    private TournamentController tournControl;
    private TournamentValidator obValid;

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
            dbConn = new JdbcPooledConnectionSource("jdbc:sqlite:eSchedule.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tournControl = new TournamentController(dbConn);
        obValid = new TournamentValidator();

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
            LocalDate nStartLocale = txtStart.getValue();
            Instant instant = Instant.from(nStartLocale.atStartOfDay(ZoneId.systemDefault()));

            LocalDate nEndLocale = txtEnd.getValue();
            Instant instant2 = Instant.from(nEndLocale.atStartOfDay(ZoneId.systemDefault()));

            Tournament obTemp = new Tournament();
            obTemp.setStartDate(Date.from(instant));
            obTemp.setEndDate(Date.from(instant2));
            obTemp.setTournamentName(txtName.getText());


            HashMap<String, String> listOfErrors = obValid.getErrors(obTemp);

            //If the annotations have errors, then it shows what kind of error you made.
            if (listOfErrors.size() > 0)
            {
                txtStart.setValue(null);
                txtEnd.setValue(null);
                txtStart.setFocusTraversable(true);
                txtName.setFocusTraversable(true);

                lblError1.setText(listOfErrors.get("startDate"));
                lblError2.setText(listOfErrors.get("endDate"));
                lblError3.setText(listOfErrors.get("tournamentName"));
            }

            //if all works, then lets send it to the database, show a prompt, and when you click close, and we go back to the mainTournamentWindow
            else
            {
                lblError1.setText("");
                lblError2.setText("");
                lblError3.setText("");
                responsePrompt(tournControl.createTournament(obTemp));
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
                FXMLLoader mainLoader =  new FXMLLoader(TournamentView.class.getResource("main-tournament-layout.fxml"));
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
        FXMLLoader mainLoader =  new FXMLLoader(TournamentView.class.getResource("main-tournament-layout.fxml"));
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
            FXMLLoader mainLoader =  new FXMLLoader(TournamentView.class.getResource("main-tournament-layout.fxml"));
            Stage obMainStage = (Stage) cancelBtn.getScene().getWindow();
            obMainStage.setScene(new Scene(mainLoader.load(), 1366,768));
            obMainStage.show();
        }

    }
}
