package it.sia.helper;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class TimeHelper {
    
    public static LocalDateTime localTimestamp()        {
        //  LocalDateTime to Timestamp
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
        return timestamp.toLocalDateTime();
    }
    public static String fileTimestamp() {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(formatter.format(date));
        return formatter.format(date);
    }
}