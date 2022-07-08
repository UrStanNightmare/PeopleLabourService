package ru.academicians.myhelper.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.academicians.myhelper.repository.DefaultUserRepository;
import ru.academicians.myhelper.repository.model.User;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class UserRepository implements DefaultUserRepository {

    private JdbcTemplate jdbcTemplate;
    private RowMapper<User> userRowMapper;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate, RowMapper<User> userRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRowMapper = userRowMapper;
    }

    @Override
    public User selectByIdWithoutDeals(long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?;", new Object[]{id}, userRowMapper);
    }

    @Override
    public long saveUser(User user) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO users(first_name, last_name, patronymic) VALUES (? ,? ,? )", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPatronymic());
            return ps;
        }, keyHolder);


        long id = (Long) keyHolder.getKeys().get("id");

        return id;
    }
}
