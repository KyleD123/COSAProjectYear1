package models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import javax.validation.constraints.*;
import java.io.Serializable;

@DatabaseTable(tableName = "players")
public class Player implements Serializable {

    @DatabaseField(generatedId = true)
    private long lPlayerID;

    @DatabaseField(canBeNull = false)
    @Size(max = 15, message="Player First Name Too Long")
    @NotEmpty(message ="Player First Name is Required")
    private String sFirstName;

    @DatabaseField(canBeNull = false)
    @Size(max = 15, message = "Player Last Name Too Long")
    @NotEmpty(message = "Player Last Name is Required")
    private String sLastName;

    @DatabaseField(canBeNull = false)
    @Min(value = 1, message = "Player Number must be 1 or 2 digits")
    @Max(value = 99, message = "Player Number must be 1 or 2 digits")
    private int nPlayerNumber;

    @DatabaseField(canBeNull = false)
    @Size(max = 20, message = "Player Position Too Long")
    @NotEmpty(message = "Player Position is Required")
    private String sPosition;

    @DatabaseField(canBeNull = true)
    @Size(max = 40, message = "Team Name Too Long")
    private String sTeamName;

    @DatabaseField(canBeNull = false)
    @Size(max = 30, message = "Player Parent Info Too Long")
    @NotEmpty(message = "Player Parent Info is Required")
    private String sParentInfo;

    @DatabaseField(canBeNull = false)
    //@Size(max = 14, min = 12, message = "Invalid Phone Number")
    @Pattern(regexp = "[0-9]?[-]?[0-9]{3}[-][0-9]{3}[-][0-9]{4}", message = "Improper Phone Number Format")
    private String sEmergencyContact;

    public Player() {
    }

    public long getlPlayerID() { return lPlayerID; }

    public void setlPlayerID(long playerID) { this.lPlayerID = playerID; }

    public String getsFirstName() {
        return sFirstName;
    }

    public void setsFirstName(String sFirstName) {
        this.sFirstName = sFirstName;
    }

    public String getsLastName() {
        return sLastName;
    }

    public void setsLastName(String sLastName) {
        this.sLastName = sLastName;
    }

    public String getsPosition() {
        return sPosition;
    }

    public void setsPosition(String sPosition) {
        this.sPosition = sPosition;
    }

    public String getsTeamName() { return sTeamName; }

    public void setsTeamName(String sTeamName) { this.sTeamName = sTeamName;}

    public String getsParentInfo() {
        return sParentInfo;
    }

    public void setsParentInfo(String sParentInfo) {
        this.sParentInfo = sParentInfo;
    }

    public String getsEmergencyContact() {
        return sEmergencyContact;
    }

    public void setsEmergencyContact(String sEmergencyContact) {
        this.sEmergencyContact = sEmergencyContact;
    }

    public int getnPlayerNumber() {
        return nPlayerNumber;
    }

    public void setnPlayerNumber(int nPlayerNumber) {
        this.nPlayerNumber = nPlayerNumber;
    }

    public void setAllValues(Player player) {
        this.setlPlayerID(player.getlPlayerID());
        this.setsFirstName(player.getsFirstName());
        this.setsLastName(player.getsLastName());
        this.setnPlayerNumber(player.getnPlayerNumber());
        this.setsPosition(player.getsPosition());
        this.setsTeamName(player.getsTeamName());
        this.setsParentInfo(player.getsParentInfo());
        this.setsEmergencyContact(player.getsEmergencyContact());
    }
}