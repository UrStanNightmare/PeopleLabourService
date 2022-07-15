package ru.academicians.myhelper.defaults;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.util.Locale;

public class DefaultRequirements {
    public static final int MAX_NAME_LENGTH = 30;
    public static final int MAX_SERVICE_NAME_LENGTH = 50;
    public static final int MAX_SERVICE_DESCRIPTION_LENGTH = 200;
    public static final int MAX_SERVICE_CITY_LENGTH = 20;


    public static final String NAME_REGEXP = "^[a-zA-Zа-яА-Я-]{1,"+ MAX_NAME_LENGTH + "}$";
    public static final String NAME_REGEXP_WITH_NULL = "^[a-zA-Zа-яА-Я\\s\\-]*$";
    public static final String DEAL_NAME_REGEXP = "^[a-zA-Zа-яА-Я-.?!, -]{1,"+ MAX_SERVICE_NAME_LENGTH + "}$";
    public static final String DEAL_NAME_REGEXP_WITH_NULL = "^[a-zA-Zа-яА-Я-.?!,\\s\\- ]*$";
    public static final String DEAL_DESCRIPTION_REGEXP = "^[a-zA-Zа-яА-Я-.?!, ]{1,"+ MAX_SERVICE_DESCRIPTION_LENGTH + "}$";
    public static final String DEAL_DESCRIPTION_REGEXP_WITH_NULL = "^[a-zA-Zа-яА-Я.?!,\\s\\- ]*$";
    public static final String DEAL_CITY_REGEXP = "^[a-zA-Zа-яА-Я0-9- ]{5,"+ MAX_SERVICE_CITY_LENGTH + "}$";
    public static final String DEAL_CITY_REGEXP_WITH_NULL = "^[a-zA-Zа-яА-Я0-9\\s\\- ]*$";

    public static final String LOG_IN_REGEXP = "^[a-zA-Z0-9 -]{1,"+ MAX_NAME_LENGTH + "}$";
    public static final String LOG_IN_REGEXP_WITH_NULL = "^[a-zA-Z0-9 \\s\\-]*$";
    public static final String PASSWORD_REGEXP = "^[a-zA-Zа-яА-Я0-9 -]{5,"+ MAX_NAME_LENGTH + "}$";
    public static final String PASSWORD_REGEXP_WITH_NULL = "^[a-zA-Zа-яА-Я0-9 \\s\\-]*$";

    public static final String PHONE_NUMBER_REGEXP = "^(\\+7|7|8)?[\\s\\-]?[489][0-9]{2}[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{2}[\\s\\-]?[0-9]{2}$";
    public static final String PHONE_NUMBER_REGEXP_WITH_NULL = "^(((\\+7|7|8)?[\\s\\-]?[489][0-9]{2}[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{2}[\\s\\-]?[0-9]{2})|[\\s\\-])?$";

    public static final long SERVICE_PRICE_MINIMAL = 0L;

    public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String DB_TEXT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern(DATE_PATTERN)
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
            .appendFraction(ChronoField.NANO_OF_SECOND, 3, 3, false)
            .toFormatter(new Locale("ru"))
            .withResolverStyle(ResolverStyle.STRICT);

    public static final DateTimeFormatter DB_TEXT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DB_TEXT_DATE_PATTERN);

    public static final String AVATAR_FOLDER_PATH = "data/avatars/";
    private DefaultRequirements(){}
}
