package Model;


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
