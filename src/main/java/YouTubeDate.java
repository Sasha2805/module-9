import javax.xml.bind.DatatypeConverter;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class ISO8601 {

    public static Date getDateFromISO8601(String dateISO){
        return DatatypeConverter.parseDateTime(dateISO).getTime();
    }

    public static String getStringDateFromISO8601(String dateISO){
        dateISO = dateISO.replace("Z", "0");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy 'at' HH:mm:ss");
        return simpleDateFormat.format(getDateFromISO8601(dateISO).getTime());
    }

}
