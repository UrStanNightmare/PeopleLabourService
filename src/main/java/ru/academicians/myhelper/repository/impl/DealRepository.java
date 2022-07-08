package ru.academicians.myhelper.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.academicians.myhelper.model.SubscriptionRecord;
import ru.academicians.myhelper.repository.model.Deal;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DealRepository implements ru.academicians.myhelper.repository.DefaultDealRepository {

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Deal> dealRowMapper;
    private RowMapper<SubscriptionRecord> subscriptionRecordRowMapper;

    @Autowired
    public DealRepository(JdbcTemplate jdbcTemplate, RowMapper<Deal> dealRowMapper, RowMapper<SubscriptionRecord> subscriptionRecordRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.dealRowMapper = dealRowMapper;
        this.subscriptionRecordRowMapper = subscriptionRecordRowMapper;
    }


    @Override
    public Deal getDealDetailsById(long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM deals WHERE id = ?;", new Object[]{id}, dealRowMapper);
    }

    @Override
    public List<SubscriptionRecord> getAllSubscribersByDealId(long id){
        return jdbcTemplate.query(
                "SELECT * from  deals_subscribers WHERE deal_id = ?",
                new Object[]{id},
                subscriptionRecordRowMapper);
    }

    @Override
    public long createNewDeal(Deal deal) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO deals(service_city, service_date, service_desc, service_name, service_price, owner_id) VALUES (? ,? ,? ,? ,? ,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, deal.getCity());
            LocalDateTime date = deal.getDate();
            Timestamp timestamp = Timestamp.valueOf(date);
            ps.setTimestamp(2, timestamp);
            ps.setString(3, deal.getDescription());
            ps.setString(4, deal.getName());
            ps.setBigDecimal(5, new BigDecimal(deal.getPrice()));
            ps.setLong(6, deal.getOwner());
            return ps;
        }, keyHolder);


        long id = (Long) keyHolder.getKeys().get("id");

        return id;
    }

    @Override
    public String addUserSubscription(long dealId, long subscriberId) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO deals_subscribers(deal_id, subscribers_id) VALUES (? ,?)");
            ps.setLong(1, dealId);
            ps.setLong(2, subscriberId);

            return ps;
        });

        return dealId + " to " + subscriberId;
    }
}
