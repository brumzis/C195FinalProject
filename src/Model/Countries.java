package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Countries {

    private int countryID;
    private String countryName;
    static ObservableList<String> countryList = FXCollections.observableArrayList();


    Countries(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
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

    public static ObservableList<String> loadCountries() throws SQLException {
        String sql = "SELECT Country FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
            countryList.add(rs.getString(1));
        return countryList;
    }

}
