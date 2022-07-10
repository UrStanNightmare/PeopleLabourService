package ru.academicians.myhelper.service;

import ru.academicians.myhelper.model.AddServiceRequest;
import ru.academicians.myhelper.model.AllDealsResponse;
import ru.academicians.myhelper.model.DealInfoResponse;
import ru.academicians.myhelper.repository.model.Deal;
import ru.academicians.myhelper.repository.model.User;

import java.util.List;

public interface DefaultDealsService {
    long createNewDeal(AddServiceRequest data, User user);

    Deal findDealById(long id);

    String addSubscription(long dealId, long subscriberId);

    DealInfoResponse getDealInformation(long id);

    List<Deal> findDealsByOwnerId(long id);

    boolean isSubscriptionExists(long dealId, long subscriberId);

    AllDealsResponse getAllDealsInfo();
}
