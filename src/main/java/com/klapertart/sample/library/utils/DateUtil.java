package com.klapertart.sample.library.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Date;

/**
 * @author tritr
 * @since 10/17/2023
 */

@Component
public class DateUtil {
    public  long plusLocalDateTime(long dateTime, String intervalTypes, long value){
        long longDate = 0L;

        // convert to local date time
        LocalDateTime date = milisToLocalDateTime(dateTime);

        // manipulate datetime
        LocalDateTime dateResult = null;
        switch (intervalTypes.toUpperCase()){
            case "DAY" :
                dateResult = date.plusDays(value);
                break;
            case "HOUR"  :
                dateResult = date.plusHours(value);
                break;
            default:
                dateResult = date.plusMinutes(value);
                break;
        }

        // convert to milis
        longDate = localDateTimeToMilis(dateResult); //dateResult.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        return longDate;
    }

    public  long minusLocalDateTime(long dateTime, String intervalTypes, long value){
        long longDate = 0L;

        // convert to local date time
        LocalDateTime date = milisToLocalDateTime(dateTime);

        // manipulate datetime
        LocalDateTime dateResult = null;
        switch (intervalTypes.toUpperCase()){
            case "DAY" :
                dateResult = date.minusDays(value);
                break;
            case "HOUR"  :
                dateResult = date.minusHours(value);
                break;
            default:
                dateResult = date.minusMinutes(value);
                break;
        }

        // convert to milis
        longDate = localDateTimeToMilis(dateResult); //dateResult.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        return longDate;
    }

    public  Date milisToDate(long time){
        return new Date(time);
    }

    public  String dateToString(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Const.DT_YOY_PATTERN);
        return simpleDateFormat.format(date);
    }

    public  LocalDateTime strToLocalDateTime(String dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Const.DT_Y_PATTERN).withResolverStyle(ResolverStyle.STRICT);
        return LocalDateTime.parse(dateTime, formatter);
    }

    public  String localDateTimeToString(LocalDateTime dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Const.DT_Y_PATTERN).withResolverStyle(ResolverStyle.STRICT);
        return dateTime.format(formatter);
    }

    public  LocalDateTime milisToLocalDateTime(long time){
        LocalDateTime localDateTime = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDateTime;
    }

    public  long localDateTimeToMilis(LocalDateTime dateTime){
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public  LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

}
