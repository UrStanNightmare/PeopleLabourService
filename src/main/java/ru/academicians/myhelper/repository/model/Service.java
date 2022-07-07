package ru.academicians.myhelper.repository.model;

import javax.persistence.*;

import static ru.academicians.myhelper.defaults.DefaultKeys.SERVICES_TABLE_NAME;

@Entity
@Table(name = SERVICES_TABLE_NAME)
public class Service {
    @Id
    @SequenceGenerator(name = "servicesIdSeq",
            sequenceName = "services_id_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "servicesIdSeq")
    private Long id;

    private Long ownerId;
}
