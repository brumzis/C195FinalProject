package Model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;


public class DateTime {

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
}
