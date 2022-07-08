package ru.academicians.myhelper.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigInteger;
import java.time.LocalDateTime;

import static ru.academicians.myhelper.defaults.DefaultRequirements.*;

public class AddServiceRequest {
    @NotNull
    private long ownerId;

    @NotNull
    @NotBlank
    @Pattern(regexp = DEAL_NAME_REGEXP)
    private String name;

    @NotNull
    @NotBlank
    @Pattern(regexp = DEAL_DESCRIPTION_REGEXP)
    private String description;

    @NotNull
    @NotBlank
    @Pattern(regexp = DEAL_CITY_REGEXP)
    private String city;

    @NotNull
    @Min(value = SERVICE_PRICE_MINIMAL)
    private BigInteger price;

    @NotNull
    @DateTimeFormat(pattern = DATE_PATTERN)
    @JsonFormat(pattern = DATE_PATTERN)
    private LocalDateTime date;

    public AddServiceRequest() {
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
