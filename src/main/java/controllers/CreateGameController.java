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
import models.Game;
import models.GameValidator;
import models.Team;
import models.Tournament;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class CreateGameController implements Initializable
{
    private Tournament obTournamentReference;

    @FXML
    private DatePicker dpSelectDate;

    @FXML
    private ImageView cancelBtn;

    @FXML
    private ComboBox cmbSelectTime, cmbSelectHomeTeam, cmbSelectAwayTeam, cmbSelectLocation;

    @FXML
    private Label lblError1, lblError2, lblError3, lblError4, lblDetermineTourn, lblTournDateInfo;

    private TeamController teamControl;
    private GameController gameController;

    private GameValidator obValid;

    private static ArrayList<Integer> ACCEPTABLE_DAYS;

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        ConnectionSource dbConn = null;
        try
        {
            dbConn = new JdbcPooledConnectionSource(MainWindow.CONNECT_STRING);
            teamControl = new TeamController(dbConn);
            gameController = new GameController(dbConn);
            obValid = new GameValidator();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }


    }

    /**
     * Purpose: This method will place all of the contents needed for the combo boxes and the label above
     */
    public void initalizeLayoutContents()
    {
        lblDetermineTourn.setText("Creating Scheduled Game for Tournament " + obTournamentReference.getnTournamentID() + " - " + obTournamentReference.getsTournamentName());
        DateFormat canadianDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        lblTournDateInfo.setText("Tournament Date: " + canadianDateFormat.format(obTournamentReference.getdStartDate()) + " to " + canadianDateFormat.format(obTournamentReference.getdEndDate()));

        //Setting up the time
        ArrayList<String> timeAllocated = new ArrayList<>();

        //Adds in the time with the :00 in the end in two hour increments
        for (int i = 6; i <= 21; i+=2)
        {
            timeAllocated.add(Integer.toString(i) + ":00");
        }
        cmbSelectTime.getItems().addAll(timeAllocated);

        //Setting up teams for both combo boxes
        List<Team> queriedTeams = teamControl.getAllTeam();
        cmbSelectAwayTeam.getItems().addAll(queriedTeams);
        cmbSelectHomeTeam.getItems().addAll(queriedTeams);

        //Setting up the locations. This part will be hardcoded for now at least.
        ArrayList<String> locations = new ArrayList<>();
        locations.add("ACT Arena RINK A");
        locations.add("ACT Arena RINK B");
        locations.add("Archibald RINK A");
        locations.add("Archibald RINK B");
        locations.add("Cosmo RINK A");
        locations.add("Cosmo RINK B");
        locations.add("Gordie Howe Kinsmen RINK A");
        locations.add("Gordie Howe Kinsmen RINK B");
        locations.add("Lions RINK A");
        locations.add("Lions RINK B");
        cmbSelectLocation.getItems().addAll(locations);

        //This portion is for populating the arraylist of days that are considered acceotable to our
        //Client's requirement
        ACCEPTABLE_DAYS = new ArrayList<>();
        ACCEPTABLE_DAYS.add(0);
        ACCEPTABLE_DAYS.add(5);
        ACCEPTABLE_DAYS.add(6);
    }

    /**
     * This saveGame method is a mouse event handler that takes in the values of the comboboxes and textvalues
     * and translate them into an appropriate game object.
     *
     * If it's validated to be correct, then it calls the GameController database controller to add it in.
     * If it's not, then it throws a series of error for the labels.
     * @param mouseEvent
     * @throws IOException
     */
    public void saveGame(MouseEvent mouseEvent) throws IOException
    {
        Game obTempGame = new Game();

        //This is where it removes the exisitng labels if its filled out.
        lblError1.setText("");
        lblError2.setText("");
        lblError3.setText("");
        lblError4.setText("");
        try
        {
            ZoneId defaultZoneId = ZoneId.systemDefault();
            LocalDate dateSelect = dpSelectDate.getValue();
            Date obDateConv = Date.from(dateSelect.atStartOfDay(defaultZoneId).toInstant());

            //This portion checks if the date you have selected is before the tournament start date or after the tournament end date.
            //If its before or after, show up an error.
            //If not, continue on.
            if (obDateConv.before(obTournamentReference.getdStartDate()) || obDateConv.after(obTournamentReference.getdEndDate()))
            {
                lblError1.setText("Date must be within the Tournament's scheduled dates");
            }

            else
            {
                //The client has said to only schedule games that falls in Friday, Saturday, Sunday
                //This part of the code is NOT commented out. It may seem it looks like it is commented out, but it is a date object's deprecated method
                if (!ACCEPTABLE_DAYS.contains(obDateConv.getDay()))
                {
                    lblError1.setText("The selected date must be between Friday - Sunday");
                }

                else
                {
                    String getTimeAsString = cmbSelectTime.getValue().toString();

                    //This part of the code is NOT commented out. It may seem it looks like it is commented out, but it is a date object's deprecated method
                    //The purpose of this is to set the time of this date's object into an appropriate time base on given hour of the user.
                    obDateConv.setHours(Integer.parseInt(getTimeAsString.substring(0, getTimeAsString.indexOf(":"))));

                    //This part creates a temporary game
                    obTempGame.setTHomeTeam((Team) cmbSelectHomeTeam.getValue());
                    obTempGame.setTAwayTeam((Team) cmbSelectAwayTeam.getValue());
                    obTempGame.setDEventDate(obDateConv);
                    obTempGame.setTTournament(obTournamentReference);
                    obTempGame.setSLocation(cmbSelectLocation.getValue().toString());

                    //This creates a series of errors message if it detects that a user placed have invalid information
                    HashMap<String, String> listOfErrors = obValid.getErrors(obTempGame);

                    //If the annotations have errors, then it shows what kind of error you made.
                    if (listOfErrors.size() > 0)
                    {
                        lblError1.setText(listOfErrors.get("dEventDate"));
                        lblError3.setText(listOfErrors.get(""));
                        lblError4.setText(listOfErrors.get("sLocation"));
                    }

                    //if all works, then lets send it to the database, show a prompt, and when you click close, and we go back to the mainTournamentWindow
                    else
                    {
                        lblError1.setText("");
                        lblError2.setText("");
                        lblError3.setText("");
                        lblError4.setText("");
                        responsePrompt(gameController.createGame(obTempGame));
                    }
                }

            }


        }

        //Catches any blanks in their fields as they return NullPointerException when adding information into the objects.
        catch (NullPointerException exp)
        {
            if (dpSelectDate.getValue() == null)
            {
                lblError1.setText("Provide an event date");
            }

            if (cmbSelectTime.getValue() == null)
            {
                lblError2.setText("Provide a time");
            }

            if (cmbSelectHomeTeam.getValue() == null && cmbSelectAwayTeam.getValue() == null)
            {
                lblError3.setText("Select a Home and/or Away team");
            }


            if (cmbSelectLocation.getValue() == null)
            {
                lblError4.setText("Select a location");
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
        FXMLLoader mainLoader =  new FXMLLoader(MainWindow.class.getResource("main-tournament-layout.fxml"));
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
            alert.setContentText("Successfully Scheduled!");
        }

        else
        {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error has occured");
            alert.setHeaderText(null);
            alert.setContentText("Game already scheduled at a particular location and/or time. Press OK to cancel.");
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


    public void setTournamentReference(Tournament obRef)
    {
        this.obTournamentReference = obRef;
    }
}
