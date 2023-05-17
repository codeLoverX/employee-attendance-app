package com.example.demo.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateFormatter {
    public static String checkAMPMRemoveSpaceUpperCase(String time) throws Exception {
//      1. replaces all spaces
//      2. checks if AM or PM given
//      3. capitalLetter
        String _time = time.concat("");
        _time = _time.replaceAll("\\s", "").toUpperCase();
        Pattern pattern = Pattern.compile("AM|PM");   // the pattern to search for
        Matcher matcher = pattern.matcher(time);
        boolean isAMPMGiven = matcher.find();
        System.out.println(isAMPMGiven);
        if (!isAMPMGiven) {
            throw new Exception("AM or PM must be mentioned");
        }
        System.out.println(_time);
        return _time;
    }

    public static String convertToAMPM(LocalTime time) throws Exception {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        return time.format(dateTimeFormatter);
    }

    public static String convertToDayAndDate(LocalDate currentdate){
        return currentdate.getDayOfMonth() + " " +currentdate.getMonth()+ ", " +currentdate.getYear();

    }
}
