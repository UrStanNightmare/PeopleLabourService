package ru.academicians.myhelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.academicians.myhelper.repository.model.Deal;

import java.util.List;

@Repository
public interface DefaultServiceRepository extends JpaRepository<Deal, Long> {
    Deal findDealById(long id);

    @Query(value =
            "SELECT users.id, users.first_name, users.last_name, users.patronymic, deal_info.deal_id," +
                    " deal_info.service_city, deal_info.service_date, deal_info.service_desc, deal_info.service_name," +
                    " deal_info.service_price, deal_info.owner_id " +
                    " FROM users RIGHT JOIN (" +
                    " SELECT services_subscribers.deal_id, services_subscribers.subscribers_id," +
                    " services.service_city, services.service_date, services.service_desc, services.service_name," +
                    " services.service_price, services.owner_id " +
                    " FROM services_subscribers " +
                    "  LEFT JOIN services " +
                    " ON services_subscribers.deal_id = services.id " +
                    " WHERE deal_id = ?1 " +
                    ") AS deal_info " +
                    "ON deal_info.subscribers_id = users.id;",
            nativeQuery = true)
    List<Object[]> getDealDetailsById(long id);
}