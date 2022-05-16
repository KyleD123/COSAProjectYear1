package models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import javax.validation.constraints.*;
import java.io.Serializable;

@DatabaseTable(tableName = "players")
public class Player implements Serializable {

    @DatabaseField(canBeNull = false)
    @Size(max = 15, message="Player First Name Too Long")
    @NotEmpty(message ="Player First Name is Required")
    private String sFirstName;
    
            sLastName, sPosition, sParentInfo, sEmergencyContact;
    private int nPlayerNumber;

    public Player(String sFirstName, String sLastName, String sPosition, String sParentInfo, String sEmergencyContact, int nPlayerNumber) {
        this.sFirstName = sFirstName;
        this.sLastName = sLastName;
        this.sPosition = sPosition;
        this.sParentInfo = sParentInfo;
        this.sEmergencyContact = sEmergencyContact;
        this.nPlayerNumber = nPlayerNumber;
    }

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
}
