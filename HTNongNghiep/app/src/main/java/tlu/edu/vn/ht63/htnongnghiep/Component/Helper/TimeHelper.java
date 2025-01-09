package tlu.edu.vn.ht63.htnongnghiep.Component.Helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeHelper {
    private Date date1, date2;

    public TimeHelper(String dateStr1, String dateStr2, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        this.date1 = dateFormat.parse(dateStr1);
        this.date2 = dateFormat.parse(dateStr2);
    }

    public long getDifferenceInMilliseconds() {
        return Math.abs(date2.getTime() - date1.getTime());
    }

    public long getDifferenceInSeconds() {
        return TimeUnit.MILLISECONDS.toSeconds(getDifferenceInMilliseconds());
    }

    public long getDifferenceInMinutes() {
        return TimeUnit.MILLISECONDS.toMinutes(getDifferenceInMilliseconds());
    }

    public long getDifferenceInHours() {
        return TimeUnit.MILLISECONDS.toHours(getDifferenceInMilliseconds());
    }

    public long getDifferenceInDays() {
        return TimeUnit.MILLISECONDS.toDays(getDifferenceInMilliseconds());
    }

    private LocalDateTime convertToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public String getTimeDifferenceDisplay() {
        LocalDateTime dateTime1 = convertToLocalDateTime(this.date1);
        LocalDateTime dateTime2 = convertToLocalDateTime(this.date2);
        if (dateTime1.isAfter(dateTime2)) {
            LocalDateTime temp = dateTime1;
            dateTime1 = dateTime2;
            dateTime2 = temp;
        }

        if (dateTime1.getYear() == dateTime2.getYear()) {
            if (dateTime1.toLocalDate().isEqual(dateTime2.toLocalDate())) {
                if (dateTime1.getHour() == dateTime2.getHour()) {
                    if (dateTime1.getMinute() == dateTime2.getMinute()) {
                        long seconds = ChronoUnit.SECONDS.between(dateTime1, dateTime2);
                        return seconds + " giây trước";
                    }
                    long minutes = ChronoUnit.MINUTES.between(dateTime1, dateTime2);
                    return minutes + " phút trước";
                }
                long hours = ChronoUnit.HOURS.between(dateTime1, dateTime2);
                return hours + " giờ trước";
            }
            long days = ChronoUnit.DAYS.between(dateTime1.toLocalDate(), dateTime2.toLocalDate());
            return days + " ngày trước";
        }
        long years = ChronoUnit.YEARS.between(dateTime1, dateTime2);
        return years + " năm trước";
    }
}
