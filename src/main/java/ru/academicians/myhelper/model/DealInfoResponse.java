package ru.academicians.myhelper.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import ru.academicians.myhelper.repository.model.Deal;

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
    private Set<Long> subscribers;

    public DealInfoResponse() {
    }

    public DealInfoResponse(Deal deal) {
        this.id = deal.getId();
        this.ownerId = deal.getOwner();
        this.name = deal.getName();
        this.description = deal.getDescription();
        this.city = deal.getCity();
        this.date = deal.getDate();
        this.price = deal.getPrice();
        this.subscribers = deal.getSubscribersSet();
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
    public Set<Long> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(@Nullable Set<Long> subscribers) {
        this.subscribers = subscribers;
    }

    public void addSubscriber(long user) {
        if (subscribers == null) {
            subscribers = new LinkedHashSet<>();
        }
        subscribers.add(user);
    }

    public void updateData(Deal dealDetailsById) {
        this.id = dealDetailsById.getId();
        this.city = dealDetailsById.getCity();
        this.date = dealDetailsById.getDate();
        this.description = dealDetailsById.getDescription();
        this.name = dealDetailsById.getName();
        this.price = dealDetailsById.getPrice();
        this.ownerId = dealDetailsById.getOwner();
        this.subscribers = dealDetailsById.getSubscribersSet();

    }
}
