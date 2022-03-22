package Model;

/**
 * Allows for the creation of a Division Object. The Division object has 3 attributes, division_ID,
 * divisionName, and country_ID. Depending on the country selected by the user, only certain
 * divisions will be displayed. Each division has a corresponding country associated with it.
 *
 */
public class Division {

    private int division_ID;
    private String divisionName;
    private int country_ID;


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


}
