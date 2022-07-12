package ru.academicians.myhelper.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.academicians.myhelper.model.SubscriptionRecord;
import ru.academicians.myhelper.repository.DefaultDealRepository;
import ru.academicians.myhelper.repository.model.Deal;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DealRepository implements DefaultDealRepository {

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Deal> detailedDealRowMapper;

    private RowMapper<Deal> dealRowMapper;
    private RowMapper<SubscriptionRecord> subscriptionRecordRowMapper;

    @Autowired
    public DealRepository(JdbcTemplate jdbcTemplate, 
                          @Qualifier("detailedDealEntityRowMapper") RowMapper<Deal> detailedDealRowMapper,
                          @Qualifier("dealEntityRowMapper") RowMapper<Deal> dealRowMapper,
                          RowMapper<SubscriptionRecord> subscriptionRecordRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.detailedDealRowMapper = detailedDealRowMapper;
        this.dealRowMapper = dealRowMapper;
        this.subscriptionRecordRowMapper = subscriptionRecordRowMapper;
    }


    @Override
    public Deal getDealDetailsById(long id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT id, service_city, service_date, service_desc, service_name, service_price, " +
                            "owner_id, array_to_string(array_agg(distinct \"subscribers_id\"), ',') as subscribers " +
                            "FROM deals " +
                            "LEFT JOIN deals_subscribers " +
                            "ON deals.id = deals_subscribers.deal_id " +
                            "WHERE id = ? " +
                            "GROUP BY id;",
                    new Object[]{id},
                    detailedDealRowMapper);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public SubscriptionRecord getSubscriptionInfoByDealIdAndSubscriberId(long dealId, long subscriberId) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM deals_subscribers WHERE deal_id = ? AND subscribers_id = ?;",
                    new Object[]{dealId, subscriberId},
                    subscriptionRecordRowMapper);
        }catch(EmptyResultDataAccessException ex){
            return null;
        }
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

        Long id = (Long) keyHolder.getKeys().get("id");

        if (id == null){
            throw new RuntimeException("Can't create deal!");
        }

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

    @Override
    public List<Deal> findDealsByUserId(long id) {
        return jdbcTemplate.query(
                "SELECT * from  deals WHERE owner_id = ?",
                new Object[]{id},
                dealRowMapper);
    }

    @Override
    public Deal findDealByOwnerIdAndCityAndDateAndDescriptionAndNameAndPrice(Long ownerId, String city, LocalDateTime date, String description, String name, BigInteger price) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM deals " +
                            "WHERE owner_id = ? AND service_city = ? AND service_date = ? AND service_desc = ? " +
                            "AND service_name = ? and service_price = ?;",
                    new Object[]{ownerId, city, date, description, name, price},
                    dealRowMapper);
        }catch(EmptyResultDataAccessException ex){
            return null;
        }
    }

    @Override
    public int deleteDealById(long id) {
        return jdbcTemplate.update("DELETE FROM deals WHERE id = ?;",
                id);
    }

    @Override
    public List<Deal> findAllDeals() {
        try {
            return jdbcTemplate.query(
                    "SELECT id, service_city, service_date, service_desc, service_name, service_price, " +
                            "owner_id, array_to_string(array_agg(distinct \"subscribers_id\"), ',') as subscribers " +
                            "FROM deals " +
                            "LEFT JOIN deals_subscribers " +
                            "ON deals.id = deals_subscribers.deal_id " +
                            "GROUP BY id;",
                    detailedDealRowMapper);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
}
