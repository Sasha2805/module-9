import javax.xml.bind.DatatypeConverter;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class YouTubeDate {
    private static final int COUNT_MS_IN_DAY = 86400000;

    public static Date getDateFromISO8601(String dateISO){
        return DatatypeConverter.parseDateTime(dateISO).getTime();
    }

    public static String getStringDateFromISO8601(String dateISO){
        dateISO = dateISO.replace("Z", "0");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy 'at' HH:mm:ss");
        return simpleDateFormat.format(getDateFromISO8601(dateISO).getTime());
    }

    public static String getDateForPublish(int countDays){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        Date date = new Date();
        date.setTime(date.getTime() - (COUNT_MS_IN_DAY * countDays));
        return simpleDateFormat.format(date.getTime());
    }

}
