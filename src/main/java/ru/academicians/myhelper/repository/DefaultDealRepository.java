package ru.academicians.myhelper.repository;

import ru.academicians.myhelper.model.SubscriptionRecord;
import ru.academicians.myhelper.repository.model.Deal;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface DefaultDealRepository {
    Deal getDealDetailsById(long id);

    SubscriptionRecord getSubscriptionInfoByDealIdAndSubscriberId(long dealId, long subscriberId);

    long createNewDeal(Deal deal);

    String addUserSubscription(long dealId, long subscriberId);

    List<Deal> findDealsByUserId(long id);

    List<Deal> findAllDeals(Map<String, Object> filterObjects);

    Deal findDealByOwnerIdAndCityAndDateAndDescriptionAndNameAndPrice(
            Long id,
            String city,
            LocalDateTime date,
            String description,
            String name,
            BigInteger price);

    int deleteDealById(long id);

    String deleteDealSubscriptionInfoByIdAndSubscriberId(long dealId, Long id);

    String updateDealData(long id, Map<String, Object> updateArgs);
}
