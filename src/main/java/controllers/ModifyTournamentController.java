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
import models.Tournament;
import models.TournamentValidator;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyTournamentController  implements Initializable {
    @FXML
    private ImageView btnAdd, cancelBtn, btnEdit;

    @FXML
    private DatePicker txtStartEdit;

    @FXML
    private DatePicker txtEndEdit;

    @FXML
    private TextField txtNameEdit;

    @FXML
    private Label lblEditIDInfo;

    @FXML
    private Label lblError1;

    @FXML
    private Label lblError2;

    @FXML
    private Label lblError3;

    private TournamentController tournControl;

    private Tournament toBeEdited;

    private TournamentValidator obValid;


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
            dbConn = new JdbcPooledConnectionSource(MainWindow.CONNECT_STRING);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tournControl = new TournamentController(dbConn);

        obValid = new TournamentValidator();
        populateTextFields();
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
    public void saveTournament(MouseEvent mouseEvent) throws IOException
    {
        try
        {
            LocalDate nStartLocale = txtStartEdit.getValue();
            Instant instant = Instant.from(nStartLocale.atStartOfDay(ZoneId.systemDefault()));

            LocalDate nEndLocale = txtEndEdit.getValue();
            Instant instant2 = Instant.from(nEndLocale.atStartOfDay(ZoneId.systemDefault()));

            toBeEdited.setStartDate(Date.from(instant));
            toBeEdited.setEndDate(Date.from(instant2));
            toBeEdited.setTournamentName(txtNameEdit.getText());


            HashMap<String, String> listOfErrors = obValid.getErrors(toBeEdited);

            //If the annotations have errors, then it shows what kind of error you made.
            if (listOfErrors.size() > 0)
            {
                txtStartEdit.setValue(null);
                txtEndEdit.setValue(null);
                txtStartEdit.setFocusTraversable(true);

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

                responsePrompt(tournControl.modifyTournament(toBeEdited));
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

        FXMLLoader mainLoader =  new FXMLLoader(MainWindow.class.getResource("main-tournament-layout.fxml"));
        Stage obMainStage = (Stage) cancelBtn.getScene().getWindow();
        obMainStage = (Stage) cancelBtn.getScene().getWindow();
        obMainStage.setScene(new Scene(mainLoader.load(), 1366,768));
        obMainStage.show();
    }



    public void populateTextFields()
    {

        //This date object is just fake stuff for testing purposes
        toBeEdited = tournControl.getLastEntry();

        DateFormat canadianDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        txtStartEdit.setPromptText(canadianDateFormat.format(toBeEdited.getStartDate()));
        txtEndEdit.setPromptText(canadianDateFormat.format(toBeEdited.getEndDate()));
        txtNameEdit.setText(toBeEdited.getTournamentName() == null ? "" : toBeEdited.getTournamentName());
        lblEditIDInfo.setText("Editing Information for Tournament ID#: " + toBeEdited.getTournamentId());
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
            alert.setContentText("The entry entry has already existed. Check your start date and end date if it is filled with a certain tournament. Press OK to cancel");

        }


        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            FXMLLoader mainLoader =  new FXMLLoader(MainWindow.class.getResource("main-tournament-layout.fxml"));
            Stage obMainStage = (Stage) cancelBtn.getScene().getWindow();
            obMainStage.setScene(new Scene(mainLoader.load(), 1366,768));
            obMainStage.show();
        }
    }

}
