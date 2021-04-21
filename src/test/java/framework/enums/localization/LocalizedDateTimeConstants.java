package framework.enums.localization;

import org.apache.commons.lang3.StringUtils;
import org.testng.util.Strings;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс с локализованными значениями даты и времени мероприятия и их методами-обработчиками
 */
public class LocalizedDateTimeConstants {

    /**
     * Мапа для хранения локализованных значений продолжительности мероприятия
     */
    private static final Map<String, String> eventDurationLocales = new HashMap<String, String>() {{
        put("час", "hour");
        put("часа", "hours");
        put("часов", "hours");
        put("минуты", "minutes");
        put("минут", "minutes");
        put("минута", "minute");
    }};

    /**
     * Мапа для хранения локализованных значений месяцев даты начала мероприятия
     */
    private static final Map<String, String> eventStartDateLocales = new HashMap<String, String>() {{
        put("янв", "Jan");
        put("фев", "Feb");
        put("мар", "Mar");
        put("апр", "Apr");
        put("мая", "May");
        put("июнь", "Jun");
        put("июл", "Jul");
        put("авг", "Aug");
        put("сент", "Sep");
        put("окт", "Oct");
        put("ноя", "Nov");
        put("дек", "Dec");
    }};

    /**
     * Заменяет в исходном тексте все найденные значения промежутков времени на английские
     *
     * @param text Исходный текст
     * @return Текст с локализованными значениями
     */
    public String getLocalizedDurationValues(String text) {
        if (!Strings.isNullOrEmpty(text)) {
            String stringPattern = "[^\\s\\d]+";
            Pattern pattern = Pattern.compile(stringPattern);
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                String key = matcher.group();
                text = eventDurationLocales.get(key) != null ? text.replaceFirst(key, eventDurationLocales.get(key)) : text;
            }
        }
        return text;
    }

    /**
     * Заменяет в исходном тексте все найденные значения месяцев на английские
     *
     * @param text Исходный текст
     * @return Текст с локализованными значениями
     */
    public String getLocalizedDateValues(String text) {
        if (!Strings.isNullOrEmpty(text)) {
            text = StringUtils.replaceEach(text, eventStartDateLocales.keySet().toArray(new String[0]), eventStartDateLocales.values().toArray(new String[0]));
        }
        return text;
    }
}
