package BugiSquad.HaksikMatnam.common.mapper;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyTimeConverter {

    public static Long minutesFromNow(LocalDateTime scheduledMealTime) {
        return Duration.between(LocalDateTime.now(), scheduledMealTime).toMinutes();
    }

    public static String siBunFormat(LocalDateTime promisedTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월dd일 HH:mm");
        String formattedTime = promisedTime.format(formatter);
        return formattedTime;
    }
}
