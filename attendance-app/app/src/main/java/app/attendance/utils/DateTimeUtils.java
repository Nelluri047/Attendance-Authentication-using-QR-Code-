package app.attendance.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class DateTimeUtils {
    public static SimpleDateFormat getFormat(String format) {
        return new SimpleDateFormat(format, Locale.getDefault());
    }

    public static String convertTimeToLocalTimezone(String time) {
        SimpleDateFormat inputFormat = getFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat outputFormat = getFormat("yyyy-MM-dd HH:mm:ss");

        try {
            inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            long timeInMilliseconds = Objects.requireNonNull(inputFormat.parse(time)).getTime();


            outputFormat.setTimeZone(TimeZone.getDefault());
            return outputFormat.format(timeInMilliseconds);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static String formatDateTime(String time, String format) {
        SimpleDateFormat inputFormat = getFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = getFormat(format);

        try {
            long timeInMilliseconds = Objects.requireNonNull(inputFormat.parse(time)).getTime();
            return outputFormat.format(timeInMilliseconds);
        } catch (Exception ignored) {
            return null;
        }
    }
}
