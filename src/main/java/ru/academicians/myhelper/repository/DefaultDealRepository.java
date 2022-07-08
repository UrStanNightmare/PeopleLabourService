package ru.academicians.myhelper.repository;

import ru.academicians.myhelper.model.SubscriptionRecord;
import ru.academicians.myhelper.repository.model.Deal;

import java.util.List;

public interface DefaultDealRepository {
    Deal getDealDetailsById(long id);

    List<SubscriptionRecord> getAllSubscribersByDealId(long id);

    long createNewDeal(Deal deal);

    String addUserSubscription(long dealId, long subscriberId);
}
