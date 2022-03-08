package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Country {

    private int countryID;
    private String countryName;
    public static ObservableList<Country> countryObjectList = FXCollections.observableArrayList();
    public static ObservableList<String> countryNameList = FXCollections.observableArrayList();


    Country(int countryID, String name) {
        countryName = name;
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

    public static ObservableList<Country> loadCountryObjects() throws SQLException {
        String sql = "SELECT * FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Country c = new Country(rs.getInt(1), rs.getString(2));
            countryObjectList.add(c);
        }
        return countryObjectList;
    }


    public static ObservableList<String> loadCountryNames() throws SQLException {
        String sql = "SELECT Country FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
            countryNameList.add(rs.getString(1));
        return countryNameList;
    }
}
