package ru.academicians.myhelper.repository.mapper.subscription;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.academicians.myhelper.model.SubscriptionRecord;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SubscriptionRecordRowMapper implements RowMapper<SubscriptionRecord> {
    @Override
    public SubscriptionRecord mapRow(ResultSet rs, int i) throws SQLException {
        SubscriptionRecord result = new SubscriptionRecord();

        result.setDealId(rs.getLong("deal_id"));
        result.setSubscriberId(rs.getLong("subscribers_id"));

        return result;
    }
}
