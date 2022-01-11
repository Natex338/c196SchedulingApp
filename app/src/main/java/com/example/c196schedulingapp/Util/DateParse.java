package com.example.c196schedulingapp.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateParse {

    public static Date dateParse(String date){
        String dateFromString =date;
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Date myDate = null;
        try{
            myDate= sdf.parse(dateFromString);
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        return myDate;
    }

    public static String dateParseString ( Date date){
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String myDate = "";
            myDate= sdf.format(date);
        return myDate;
    }

}
