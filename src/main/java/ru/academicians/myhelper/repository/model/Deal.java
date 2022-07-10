package ru.academicians.myhelper.repository.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import static ru.academicians.myhelper.defaults.DefaultRequirements.DATE_PATTERN;
public class Deal {
    @NotNull
    private Long id;

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
    private BigInteger price;

    private Long owner;

    @DateTimeFormat(pattern = DATE_PATTERN)
    @JsonFormat(pattern = DATE_PATTERN)
    private LocalDateTime date;
    public Deal() {
    }

    private Set<Long> subscribersSet;

    public Deal(String name, String description, String city, BigInteger price, Long owner, LocalDateTime date) {
        this.name = name;
        this.description = description;
        this.city = city;
        this.price = price;
        this.owner = owner;
        this.date = date;
    }

    public Deal(Long id, String name, String description, String city, BigInteger price, Long owner, LocalDateTime date, Set<Long> subscribersSet) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.city = city;
        this.price = price;
        this.owner = owner;
        this.date = date;
        this.subscribersSet = subscribersSet;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    public long getOwner() {
        return owner;
    }

    public void setOwner(long user) {
        this.owner = user;
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

    public Set<Long> getSubscribersSet() {
        return subscribersSet;
    }

    public void setSubscribersSet(Set<Long> subscribersSet) {
        this.subscribersSet = subscribersSet;
    }

    public void addSubscriberId(long subscriberId) {
        if (this.subscribersSet == null){
            this.subscribersSet = new LinkedHashSet<>();
        }
        this.subscribersSet.add(subscriberId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deal deal = (Deal) o;
        return id.equals(deal.id) && name.equals(deal.name) && description.equals(deal.description) && city.equals(deal.city) && price.equals(deal.price) && owner.equals(deal.owner) && date.equals(deal.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, city, price, owner, date);
    }

    @Override
    public String toString() {
        return "Deal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", city='" + city + '\'' +
                ", price=" + price +
                ", owner=" + owner +
                ", date=" + date +
                '}';
    }
}
