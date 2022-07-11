package ru.academicians.myhelper.repository.mapper.deal;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.academicians.myhelper.repository.model.Deal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class DealEntityRowMapper implements RowMapper<Deal> {

    @Override
    public Deal mapRow(ResultSet rs, int i) throws SQLException {
        Deal result = new Deal();
        result.setId(rs.getLong("id"));
        result.setCity(rs.getString("service_city"));

        Instant service_time = rs.getTimestamp("service_date").toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(service_time, ZoneOffset.UTC);
        result.setDate(localDateTime);
        result.setDescription(rs.getString("service_desc"));
        result.setName(rs.getString("service_name"));
        result.setPrice(rs.getBigDecimal("service_price").toBigInteger());
        result.setOwner(rs.getLong("owner_id"));

        return result;
    }
}
