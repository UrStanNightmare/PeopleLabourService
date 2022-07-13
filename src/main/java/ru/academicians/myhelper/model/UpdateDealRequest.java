package ru.academicians.myhelper.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static ru.academicians.myhelper.defaults.DefaultRequirements.*;

public class UpdateDealRequest {
    @Size(min = 5, max=MAX_SERVICE_NAME_LENGTH)
    @Pattern(regexp = DEAL_NAME_REGEXP_WITH_NULL)
    private String name;

    @Size(min = 5, max=MAX_SERVICE_DESCRIPTION_LENGTH)
    @Pattern(regexp = DEAL_DESCRIPTION_REGEXP_WITH_NULL)
    private String description;

    @Size(min = 5, max=MAX_SERVICE_CITY_LENGTH)
    @Pattern(regexp = DEAL_CITY_REGEXP_WITH_NULL)
    private String city;

    @Min(value = SERVICE_PRICE_MINIMAL)
    private BigInteger price;

    @DateTimeFormat(pattern = DATE_PATTERN)
    @JsonFormat(pattern = DATE_PATTERN)
    private LocalDateTime date;

    public UpdateDealRequest(String name, String description, String city, BigInteger price, LocalDateTime date) {
        this.name = name;
        this.description = description;
        this.city = city;
        this.price = price;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCity() {
        return city;
    }

    public BigInteger getPrice() {
        return price;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "UpdateDealRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", city='" + city + '\'' +
                ", price=" + price +
                ", date=" + date +
                '}';
    }

    public Map<String, Object> toHashMap(){
        Map<String, Object> result = new HashMap<>();
        if (name != null){result.put("service_name", this.name);}
        if (description != null){result.put("service_desc", this.description);}
        if (city != null){result.put("service_city", this.city);}
        if (price != null){result.put("service_price", this.price);}
        if (date != null){result.put("service_date", this.date);}

        return result;
    }
}
