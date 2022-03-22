package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;

/**
 * The DateTimeUtility is an abstract class create to handle the processing of different datetime objects,
 * localdatetime objects, zoneddatetime objects, and perform various conversions from different local time zones
 * to UTC, and back from UTC to a local time zone. The DateTimeUtility class also has methods that validate
 * user entered times to make sure the appointments fall within the given specifications.
 *
 */
public abstract class DateTimeUtility {

    /**
     * Accepts a LocalDateTime object and checks to see if it falls between 8AM and 10PM Eastern Standard Time.
     * The LocalDateTime objects in the database are stored in UTC, so they must first be converted to
     * eastern time before the check can be made.
     *
     * @param myDateTime is a LocalDateTime object
     * @return boolean: true if appointment falls within business hours (America/Eastern time zone)
     */
    public static boolean validateAppointmentTime(LocalDateTime myDateTime) {

        LocalDateTime newDateTime = DateTimeUtility.convertToUSEastern(myDateTime);  //time passed in is first converted to eastern time.

        if (newDateTime.getHour() >= 8 && newDateTime.getHour() <= 22 && newDateTime.getDayOfWeek() != DayOfWeek.SATURDAY && newDateTime.getDayOfWeek() != DayOfWeek.SUNDAY)
            return true;
        else
            return false;
    }


    /**
     * Simple method to ensure that the start time entered by the user comes before the end time
     *
     * @param start         the appointment start time
     * @param end           the appointment end time
     * @return true/false   returns true is the start time comes before the end time
     */
    public static boolean compareTimes(LocalDateTime start, LocalDateTime end) {
        if (start.isBefore(end))
            return true;
        else
            return false;
    }

    /**
     * Method converts the LocalDatetime passed in to its corresponding time in UTC
     *
     * @param myDateTime    A LocalDateTime object
     * @return LocalDateTime object that has been converted to UTC Time.
     */
    public static LocalDateTime convertToUTC(LocalDateTime myDateTime) {
        ZonedDateTime myZDT = ZonedDateTime.of(myDateTime, ZoneId.systemDefault());
        ZoneId utcZone = ZoneId.of("UTC");
        ZonedDateTime utcZDT = ZonedDateTime.ofInstant(myZDT.toInstant(), utcZone);
        return utcZDT.toLocalDateTime();
    }

    /**
     * Method takes a UTC time and converts it to the local time on the machine
     *
     * @param myDateTime    time passed in is in UTC.
     * @return returns a LocalDatetime object with the correspoinding local time on the machine
     */
    public static LocalDateTime convertFromUTC(LocalDateTime myDateTime) {
        ZoneId utcZone = ZoneId.of("UTC");
        ZonedDateTime ZTD = ZonedDateTime.of(myDateTime, utcZone);
        ZonedDateTime myZDT = ZonedDateTime.ofInstant(ZTD.toInstant(), ZoneId.systemDefault());
        return myZDT.toLocalDateTime();
    }

    /**
     * Converts a LocalDateTime to America/Eastern time
     *
     * @param myDateTime - a locatdatetime object
     * @return returns a localdatetime object converted to US/Eastern time
     */
    public static LocalDateTime convertToUSEastern(LocalDateTime myDateTime) {
        ZonedDateTime myZDT = ZonedDateTime.of(myDateTime, ZoneId.systemDefault());
        ZoneId usEasternZone = ZoneId.of("US/Eastern");
        ZonedDateTime easternZDT = ZonedDateTime.ofInstant(myZDT.toInstant(), usEasternZone);
        return easternZDT.toLocalDateTime();
    }

    /**
     * This method makes sure an appointment cannot be scheduled during the same time an existing
     * appointment is taking place with a given customer. It accepts a customer ID, a start, and
     * an end time. It grabs all appointments for that customer from the database, then
     * compares the new start and end times with all existing appointments for that customer.
     * The method returns true if no conflicts are found.
     *
     * @param newStartTime - the starting appointment time to be checked
     * @param newEndTime - the ending time to be checked
     * @param apptCustomerID - the customer ID needing to be checked
     *
     */
    public static boolean checkOverlap(int apptCustomerID, LocalDateTime newStartTime, LocalDateTime newEndTime) throws SQLException {
        String sql = "SELECT Start, End FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, apptCustomerID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            LocalDateTime existingStart = (LocalDateTime)rs.getObject(1);
            LocalDateTime existingEnd = (LocalDateTime)rs.getObject(2);
            if(newStartTime.isAfter(existingStart) && newStartTime.isBefore(existingEnd))
                return false;
            if(newEndTime.isAfter(existingStart) && newEndTime.isBefore(existingEnd))
                return false;
        }
        return true;
    }

}
