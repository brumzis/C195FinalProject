package Model;

/**
 * Country class allows for the creation of a Country object. The Contact object has two attributes,
 * corresponding to the 2 relevant columns in the database. countryID and countryName.
 * Getters and setters for each attribute follow.
 *
 */
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
