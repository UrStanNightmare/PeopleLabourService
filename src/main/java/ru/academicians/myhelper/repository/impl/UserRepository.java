package ru.academicians.myhelper.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;
import ru.academicians.myhelper.model.DetailedUserInfoResponse;
import ru.academicians.myhelper.repository.DefaultUserRepository;
import ru.academicians.myhelper.repository.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Repository
public class UserRepository implements DefaultUserRepository {

    private JdbcTemplate jdbcTemplate;
    private RowMapper<User> userRowMapper;
    private RowMapper<User> userSecurityRowMapper;

    private RowMapper<DetailedUserInfoResponse> detailedUserInfoResponseRowMapper;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate,
                          @Qualifier("userEntityRowMapper") RowMapper<User> userRowMapper,
                          @Qualifier("userSecurityRowMapper") RowMapper<User> userSecurityRowMapper,
                          RowMapper<DetailedUserInfoResponse> detailedUserInfoResponseRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRowMapper = userRowMapper;
        this.userSecurityRowMapper = userSecurityRowMapper;
        this.detailedUserInfoResponseRowMapper = detailedUserInfoResponseRowMapper;
    }


    @Override
    public DetailedUserInfoResponse getDetailedUserInfoById(long id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT users_with_param.id, users_with_param.first_name, " +
                            "users_with_param.last_name, users_with_param.middle_name, " +
                            "users_with_param.login,\n" +
                            "array_to_string(array_agg(distinct \"deal_info\"), ',') as deals " +
                            "FROM " +
                            " (SELECT users.id, first_name, last_name, middle_name, login, " +
                            " CONCAT(deals.id,'|', " +
                            " deals.service_city,'|', " +
                            " deals.service_date,'|', " +
                            " deals.service_desc,'|', " +
                            " deals.service_name,'|', " +
                            "  deals.service_price) as deal_info " +
                            " FROM users " +
                            " LEFT JOIN deals " +
                            " ON users.id = deals.owner_id) as users_with_param " +
                            "WHERE users_with_param.id = ? " +
                            "GROUP BY users_with_param.id, users_with_param.first_name, " +
                            "users_with_param.last_name, users_with_param.middle_name, " +
                            "users_with_param.login;",
                    new Object[]{id},
                    detailedUserInfoResponseRowMapper);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public User findUserById(long id) {
        try {
            String userSQLQuery = "SELECT * FROM users WHERE id=?";
            return jdbcTemplate.queryForObject(userSQLQuery, new Object[]{id},
                    (ResultSet rs, int rowNum) -> {
                        User user = new User();
                        user.setId(rs.getLong("id"));
                        user.setFirstName(rs.getString("first_name"));
                        user.setLastName(rs.getString("last_name"));
                        user.setMiddleName(rs.getString("middle_name"));
                        user.setLogin(rs.getString("login"));
                        user.setPassword(rs.getString("password"));
                        return user;
                    });

        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public String updateUserData(long id, Map<String, Object> updateData) {
        StringBuilder result = new StringBuilder();

        StringBuilder sqlStatementBuilder = new StringBuilder();
        sqlStatementBuilder.append("UPDATE users SET ");

        Object[] updateObjects = new Object[updateData.size() + 1];
        int i = 0;
        for (Map.Entry<String, Object> updateDataEntry : updateData.entrySet()) {
            String key = updateDataEntry.getKey();
            sqlStatementBuilder.append(key + " = ?, ");

            result.append(key + " ");

            updateObjects[i] = updateDataEntry.getValue();

            i++;
        }
        updateObjects[i] = id;

        sqlStatementBuilder.setLength(sqlStatementBuilder.length() - 2);
        sqlStatementBuilder.append(" WHERE id = ?;");

        jdbcTemplate.update(sqlStatementBuilder.toString(), updateObjects);

        return result.toString();
    }

    @Override
    public long saveUser(User user) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO users(first_name, last_name, middle_name, login, password) VALUES (? ,? ,?, ?, ? )",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getMiddleName());
            ps.setString(4, user.getLogin());
            ps.setString(5, user.getPassword());
            return ps;
        }, keyHolder);


        long id = (Long) keyHolder.getKeys().get("id");

        return id;
    }

    @Override
    public User findUserByLogin(String login) {
        try {
            Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
            String userSQLQuery = "SELECT * FROM users WHERE login=?";
            User dbUser = jdbcTemplate.queryForObject(userSQLQuery, new String[]{login},
                    (ResultSet rs, int rowNum) -> {
                        User user = new User();
                        user.setLogin(login);
                        user.setPassword(rs.getString("password"));
                        user.setId(rs.getLong("id"));
                        return user;
                    });
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
            GrantedAuthority grantedIdAuthority = new SimpleGrantedAuthority("id:" + dbUser.getId().toString());
            grantedAuthoritiesList.add(grantedAuthority);
            grantedAuthoritiesList.add(grantedIdAuthority);
            dbUser.setGrantedAuthoritiesList(grantedAuthoritiesList);
            return dbUser;

        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public int deleteUserAndSubscriptionDataById(Long id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id = ?;",
                id);
    }
}
