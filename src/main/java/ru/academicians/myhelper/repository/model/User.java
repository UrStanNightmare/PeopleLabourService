package ru.academicians.myhelper.repository.model;

import ru.academicians.myhelper.defaults.DefaultKeys;
import ru.academicians.myhelper.defaults.DefaultRequirements;

import javax.persistence.*;

@Entity
@Table(name = DefaultKeys.USERS_TABLE_NAME)
public class User {

    @Id
    @SequenceGenerator(name = "userIdSeq",
            sequenceName = "users_id_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "userIdSeq")
    private Long id;
    @Column(name = DefaultKeys.LAST_NAME_KEY,
            nullable = false,
            length = DefaultRequirements.MAX_NAME_LENGTH)
    private String lastName;

    @Column(name = DefaultKeys.FIRST_NAME_KEY,
            nullable = false,
            length = DefaultRequirements.MAX_NAME_LENGTH)
    private String firstName;

    @Column(name = DefaultKeys.PATRONYMIC_KEY,
            nullable = true,
            length = DefaultRequirements.MAX_NAME_LENGTH)
    private String patronymic;

    public User() {
    }

    public User(String lastName, String firstName, String patronymic) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
    }

    public User(long id, String lastName, String firstName, String patronymic) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
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
}
