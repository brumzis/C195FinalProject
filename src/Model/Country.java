package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Country {

    private int countryID;
    private String countryName;


    Country(int countryID, String countryName) {
        this.countryName = countryName;
        this.countryID = countryID;
    }

    public int getCountryID () {
        return countryID;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }



}
