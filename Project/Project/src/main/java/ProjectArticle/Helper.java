/*
    RMIT University Vietnam
    Course: INTE2512 Object-Oriented Programming
    Semester: 2021B
    Assessment: Final Project
    Author:
    - Pham Duy Anh - s3802674
    - Pham Dang Khoa - s3884419
    - Nguyen Minh Hien - s3877996
    - Nathan Candre - s3938364
    Acknowledgement:
    [1]: https://jsoup.org/cookbook/extracting-data/selector-syntax
    [2]: https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ThreadPoolExecutor.html
    [3]: https://www.tutorialspoint.com/javafx/javafx_css.htm
    [4]: https://www.javatpoint.com/javafx-playing-video
    [5] All lecture and lab slides from RMIT univeristy
*/
package ProjectArticle;

import javafx.application.Application;
import javafx.stage.Stage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/** This class will handle the conversion and format of the time **/

public class Helper extends Application {
    private static final Helper myInstance = new Helper();
    public static Helper getInstance() { return myInstance;}
    
    //Get the time duration of the article
    public static String getConvertedTimeDuration(String date) {
        StringBuilder res = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            Date startDate = sdf.parse(formatTime(date));
            Date endDate = new Date();
            long timeDuration = endDate.getTime() - startDate.getTime();

            long seconds = (timeDuration / 1000) % 60;
            long minutes = (timeDuration / (1000 * 60)) % 60;
            long hours = (timeDuration / (1000 * 60 * 60)) % 24;
            long years = (timeDuration / (1000L * 60 * 60 * 24 * 365));
            long days = (timeDuration / (1000 * 60 * 60 * 24)) % 365;
            long months = (timeDuration / (1000L * 60 * 60 * 24 * 30));

            if (years != 0) {
                if (timeDuration != 1) {
                    res.append(years).append(" years ago");
                } else {
                    res.append(years).append(" year ago");
                }
            } else if (months != 0) {
                if (months != 1) {
                    res.append(months).append(" months ago");
                } else {
                    res.append(months).append(" month ago");
                }
            } else if (days != 0) {
                if (days != 1) {
                    res.append(days).append(" days ago");
                } else {
                    res.append(days).append(" day ago");
                }
            } else if (hours != 0) {
                if (hours != 1) {
                    res.append(hours).append(" hours ago");
                } else {
                    res.append(hours).append(" hour ago");
                }
            } else if (minutes != 0) {
                if (minutes != 1) {
                    res.append(minutes).append(" minutes ago");
                } else {
                    res.append(minutes).append(" minute ago");
                }
            } else if (seconds != 0) {
                if (seconds != 1) {
                    res.append(seconds).append(" seconds ago");
                } else {
                    res.append(seconds).append(" second ago");
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res.toString();
    }

    //Format the time to calculate time duration
    public static String formatTime(String unixTime) {
        ZonedDateTime dateTime = Instant.ofEpochMilli(Long.parseLong(unixTime)*1000).atZone(ZoneId.of("GMT+7"));
        String formatted = dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        return formatted;
    }

    public static String convertTime1(String time){
        long unixTime = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = sdf.parse(time);
            unixTime = date.getTime() / 1000;

        } catch (ParseException e){
            e.printStackTrace();
        }
        return String.valueOf(unixTime);
    }

    // This function will convert from readable date (E, dd MMM yyyy HH:mm:ss Z) to time String (Sun, 22 May 2022 23:59:00 +0700)
    public static String convertTime2(String time){
        long unixTime = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
            Date date = sdf.parse(time);
            unixTime = date.getTime() / 1000;

        } catch (ParseException e){
            e.printStackTrace();
        }
        return String.valueOf(unixTime);
    }

    // // This function will convert from readable date (HH:mm dd/MM/yyyy) to time String (23:59 22/5/2022)
    public static String convertTime3(String time){
        long unixTime = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            Date date = sdf.parse(time);
            unixTime = date.getTime() / 1000;

        } catch (ParseException e){
            e.printStackTrace();
        }
        return String.valueOf(unixTime);
    }


    @Override
    public void start(Stage stage) throws Exception {

    }
}
