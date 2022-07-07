package ru.academicians.myhelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.academicians.myhelper.repository.model.User;

import java.util.List;

@Repository
public interface DefaultUserRepository extends JpaRepository<User, Long> {

    User getUserById(long id);

    User getUserByLastNameAndFirstNameAndPatronymic(String lastName, String firstName, String patronymic);
}
