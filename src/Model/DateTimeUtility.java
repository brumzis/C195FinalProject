package Model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;


public class DateTimeUtility {

    public static java.sql.Timestamp getTimeStamp() {
        ZoneId myZone = ZoneId.of("UTC");
        LocalDateTime myLocalDateTime = LocalDateTime.now(myZone);
        java.sql.Timestamp myTimeStamp = Timestamp.valueOf(myLocalDateTime);
        return myTimeStamp;
    }

    public static java.sql.Date getDate() {
        java.sql.Date myDate = java.sql.Date.valueOf(LocalDate.now());
        return myDate;
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
}
