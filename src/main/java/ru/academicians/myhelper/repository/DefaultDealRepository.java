package ru.academicians.myhelper.repository;

import ru.academicians.myhelper.model.SubscriptionRecord;
import ru.academicians.myhelper.repository.model.Deal;

import java.util.List;

public interface DefaultDealRepository {
    Deal getDealDetailsById(long id);

    SubscriptionRecord getSubscriptionInfoByDealIdAndSubscriberId(long dealId, long subscriberId);

    long createNewDeal(Deal deal);

    String addUserSubscription(long dealId, long subscriberId);

    List<Deal> findDealsByUserId(long id);

    List<Deal> findAllDeals();
}
