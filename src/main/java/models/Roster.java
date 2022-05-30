package models;

import com.j256.ormlite.table.DatabaseTable;
import javafx.scene.control.TextField;

@DatabaseTable(tableName = "players")
public class Roster
{

    private TextField txtGoalie, txtRightDefense, txtLeftDefense, txtRightWing, txtLeftWing, txtCenter;




}
