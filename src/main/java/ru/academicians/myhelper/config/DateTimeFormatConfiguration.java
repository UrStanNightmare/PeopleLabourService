package ru.academicians.myhelper.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.academicians.myhelper.defaults.DefaultRequirements;

@Configuration
public class DateTimeFormatConfiguration implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setTimeFormatter(DefaultRequirements.DEFAULT_DATE_TIME_FORMATTER);
        registrar.registerFormatters(registry);
    }
}
