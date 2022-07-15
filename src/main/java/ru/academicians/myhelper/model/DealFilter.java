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

public class DealFilter {
    @DateTimeFormat(pattern = DATE_PATTERN)
    @JsonFormat(pattern = DATE_PATTERN)
    private LocalDateTime startDate;

    @DateTimeFormat(pattern = DATE_PATTERN)
    @JsonFormat(pattern = DATE_PATTERN)
    private LocalDateTime endDate;

    @Size(min = 1, max=MAX_SERVICE_CITY_LENGTH)
    @Pattern(regexp = DEAL_CITY_REGEXP_WITH_NULL)
    private String city;

    @Min(value = SERVICE_PRICE_MINIMAL)
    private BigInteger priceGreaterThan;

    @Min(value = SERVICE_PRICE_MINIMAL)
    private BigInteger priceLessThan;

    public DealFilter(LocalDateTime startDate, LocalDateTime endDate, String city, BigInteger priceGreaterThan, BigInteger priceLessThan) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.city = city;
        this.priceGreaterThan = priceGreaterThan;
        this.priceLessThan = priceLessThan;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public String getCity() {
        return city;
    }

    public BigInteger getPriceGreaterThan() {
        return priceGreaterThan;
    }

    public BigInteger getPriceLessThan() {
        return priceLessThan;
    }

    public Map<String, Object> toHashMap(){
        Map<String, Object> result = new HashMap<>();
        if (startDate != null){result.put("service_date_s", this.startDate);}
        if (endDate != null){result.put("service_date_e", this.endDate);}
        if (city != null){result.put("service_city", this.city);}
        if (priceGreaterThan != null){result.put("service_price_g", this.priceGreaterThan);}
        if (priceLessThan != null){result.put("service_price_l", this.priceLessThan);}

        return result;
    }

    @Override
    public String toString() {
        return "DealFilter{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", city='" + city + '\'' +
                ", priceGreaterThan=" + priceGreaterThan +
                ", priceLessThan=" + priceLessThan +
                '}';
    }
}
