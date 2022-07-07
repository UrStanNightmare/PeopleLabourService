package ru.academicians.myhelper.repository.model;

import ru.academicians.myhelper.defaults.DefaultRequirements;

import javax.persistence.*;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import static ru.academicians.myhelper.defaults.DefaultKeys.*;

@Entity
@Table(name = USERS_TABLE_NAME)
public class User {

    @Id
    @SequenceGenerator(name = "userIdSeq",
            sequenceName = "users_id_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "userIdSeq")
    private Long id;
    @Column(name = LAST_NAME_KEY,
            nullable = false,
            length = DefaultRequirements.MAX_NAME_LENGTH)
    private String lastName;

    @Column(name = FIRST_NAME_KEY,
            nullable = false,
            length = DefaultRequirements.MAX_NAME_LENGTH)
    private String firstName;

    @Column(name = PATRONYMIC_KEY,
            nullable = true,
            length = DefaultRequirements.MAX_NAME_LENGTH)
    private String patronymic;

    @OneToMany(mappedBy = "owner",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<Deal> deals = new LinkedHashSet<>();


    public User() {
    }


    public User(String lastName, String firstName, String patronymic) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
    }

    public User(String lastName, String firstName, String patronymic, Set<Deal> deals) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.deals = deals;
    }

    public User(Long id, String lastName, String firstName, String patronymic, Set<Deal> deals) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.deals = deals;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Set<Deal> getServices() {
        return deals;
    }

    public void setServices(Set<Deal> deals) {
        this.deals = deals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && lastName.equals(user.lastName) && firstName.equals(user.firstName) && Objects.equals(patronymic, user.patronymic) && Objects.equals(deals, user.deals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, firstName, patronymic, deals);
    }
}
