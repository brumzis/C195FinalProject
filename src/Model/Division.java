package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Division {

    private int division_ID;
    private String divisionName;
    private int country_ID;
    static ObservableList<String> divisionList = FXCollections.observableArrayList();
    static ObservableList<String> specificDivisionList = FXCollections.observableArrayList();


    Division(int division_ID, String divisionName, int country_ID) {
        this.division_ID = division_ID;
        this.divisionName = divisionName;
        this.country_ID = country_ID;
    }

    public int getDivision_ID() {
        return division_ID;
    }

    public String getDivisionName() {
        return  divisionName;
    }

    public int getCountry_ID() {
        return country_ID;
    }

    public void setDivision_ID(int division_ID) {
        this.division_ID = division_ID;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public void setCountry_ID(int country_ID) {
        this.country_ID = country_ID;
    }

    public static ObservableList<String> loadDivisions() throws SQLException {
        String sql = "SELECT Division FROM First_Level_Divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
            divisionList.add(rs.getString(1));
        return divisionList;
    }

    public static ObservableList<String> loadSpecificDivisions(int country_ID) throws SQLException {
        specificDivisionList.clear();
        String sql = "SELECT Division FROM First_Level_Divisions WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, country_ID);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
            specificDivisionList.add(rs.getString(1));
        return specificDivisionList;
    }

}
