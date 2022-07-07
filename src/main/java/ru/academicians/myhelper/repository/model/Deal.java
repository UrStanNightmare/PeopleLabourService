package ru.academicians.myhelper.repository.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import static ru.academicians.myhelper.defaults.DefaultKeys.*;
import static ru.academicians.myhelper.defaults.DefaultRequirements.*;

@Entity
@Table(name = SERVICES_TABLE_NAME)
public class Deal {
    @Id
    @SequenceGenerator(name = "servicesIdSeq",
            sequenceName = "services_id_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "servicesIdSeq")
    private Long id;

    @Column(name = SERVICE_NAME_KEY,
            nullable = false,
            length = MAX_SERVICE_NAME_LENGTH)
    private String name;

    @Column(name = SERVICE_DESCRIPTION_KEY,
            nullable = false,
            length = MAX_SERVICE_DESCRIPTION_LENGTH)
    private String description;

    @Column(name = SERVICE_CITY_KEY,
            nullable = false,
            length = MAX_SERVICE_CITY_LENGTH)
    private String city;

    @Min(value = SERVICE_PRICE_MINIMAL)
    @Column(name = SERVICE_PRICE_KEY,
            nullable = false)
    private BigInteger price;

    @ManyToOne
    @JoinColumn(name = SERVICE_OWNER_ID_KEY)
    private User owner;

    @DateTimeFormat(pattern = DATE_PATTERN)
    @JsonFormat(pattern = DATE_PATTERN)
    @Column(name = SERVICE_DATE_KEY,
            nullable = false)
    private LocalDateTime date;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<User> subscribers = new LinkedHashSet<>();

    public Deal() {
    }

    public Deal(String name, String description, String city, BigInteger price, User owner, LocalDateTime date) {
        this.name = name;
        this.description = description;
        this.city = city;
        this.price = price;
        this.owner = owner;
        this.date = date;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User user) {
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

    public Set<User> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<User> subscribers) {
        this.subscribers = subscribers;
    }

    public void addSubscriber(User subscriber) {
        if (subscribers == null) {
            subscribers = new LinkedHashSet<>();
        }
        subscribers.add(subscriber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deal deal = (Deal) o;
        return id.equals(deal.id) && name.equals(deal.name) && description.equals(deal.description) && city.equals(deal.city) && price.equals(deal.price) && owner.equals(deal.owner) && date.equals(deal.date) && Objects.equals(subscribers, deal.subscribers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, city, price, owner, date, subscribers);
    }
}
