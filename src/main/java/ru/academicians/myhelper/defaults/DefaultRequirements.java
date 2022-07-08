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
    public static final String DEAL_NAME_REGEXP = "^[a-zA-Zа-яА-Я-.?! ]{1,"+ MAX_SERVICE_NAME_LENGTH + "}$";
    public static final String DEAL_DESCRIPTION_REGEXP = "^[a-zA-Zа-яА-Я-.?! ]{1,"+ MAX_SERVICE_DESCRIPTION_LENGTH + "}$";
    public static final String DEAL_CITY_REGEXP = "^[a-zA-Zа-яА-Я0-9- ]{1,"+ MAX_SERVICE_CITY_LENGTH + "}$";

    public static final long SERVICE_PRICE_MINIMAL = 0L;

    public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static final DateTimeFormatter TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern(DATE_PATTERN)
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
            .toFormatter(new Locale("ru"))
            .withResolverStyle(ResolverStyle.STRICT);

    private DefaultRequirements(){}
}
