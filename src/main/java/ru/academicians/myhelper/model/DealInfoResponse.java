package ru.academicians.myhelper.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import ru.academicians.myhelper.repository.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import static ru.academicians.myhelper.defaults.DefaultRequirements.DATE_PATTERN;

public class DealInfoResponse {
    @NotNull
    private long id;

    @NotNull
    private long ownerId;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @NotBlank
    private String city;


    @NotNull
    @DateTimeFormat(pattern = DATE_PATTERN)
    @JsonFormat(pattern = DATE_PATTERN)
    private LocalDateTime date;

    @NotNull
    private BigInteger price;

    @Nullable
    private Set<User> subscribers;

    public DealInfoResponse() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    @Nullable
    public Set<User> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(@Nullable Set<User> subscribers) {
        this.subscribers = subscribers;
    }

    public void addSubscriber(User user) {
        if (subscribers == null){
            subscribers = new LinkedHashSet<>();
        }
        subscribers.add(user);
    }
}
