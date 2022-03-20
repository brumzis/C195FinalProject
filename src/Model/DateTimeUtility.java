package Model;

import com.sun.marlin.FloatArrayCache;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;


public class DateTimeUtility {


    public static boolean validateAppointmentTime(LocalDateTime myDateTime) {
        LocalDateTime newDateTime = DateTimeUtility.convertToUSEastern(myDateTime);
        if (newDateTime.getHour() >= 8 && newDateTime.getHour() <= 22 && newDateTime.getDayOfWeek() != DayOfWeek.SATURDAY && newDateTime.getDayOfWeek() != DayOfWeek.SUNDAY)
            return true;
        else
            return false;
    }


    public static boolean compareTimes(LocalDateTime start, LocalDateTime end) {
        if (start.isBefore(end))
            return true;
        else
            return false;
    }


    public static LocalDateTime convertToUTC(LocalDateTime myDateTime) {
        ZonedDateTime myZDT = ZonedDateTime.of(myDateTime, ZoneId.systemDefault());
        ZoneId utcZone = ZoneId.of("UTC");
        ZonedDateTime utcZDT = ZonedDateTime.ofInstant(myZDT.toInstant(), utcZone);
        return utcZDT.toLocalDateTime();
    }


    public static LocalDateTime convertFromUTC(LocalDateTime myDateTime) {
        ZoneId utcZone = ZoneId.of("UTC");
        ZonedDateTime ZTD = ZonedDateTime.of(myDateTime, utcZone);
        ZonedDateTime myZDT = ZonedDateTime.ofInstant(ZTD.toInstant(), ZoneId.systemDefault());
        return myZDT.toLocalDateTime();
    }


    public static LocalDateTime convertToUSEastern(LocalDateTime myDateTime) {
        ZonedDateTime myZDT = ZonedDateTime.of(myDateTime, ZoneId.systemDefault());
        ZoneId usEasternZone = ZoneId.of("US/Eastern");
        ZonedDateTime easternZDT = ZonedDateTime.ofInstant(myZDT.toInstant(), usEasternZone);
        return easternZDT.toLocalDateTime();
    }


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