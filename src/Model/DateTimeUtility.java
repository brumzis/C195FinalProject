package Model;

import com.sun.marlin.FloatArrayCache;
import javafx.scene.control.Alert;

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
}
