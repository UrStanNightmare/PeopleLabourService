package ru.academicians.myhelper.service;

import ru.academicians.myhelper.model.AddServiceRequest;
import ru.academicians.myhelper.model.AllDealsResponse;
import ru.academicians.myhelper.model.DealInfoResponse;
import ru.academicians.myhelper.model.DetailedUserInfoResponse;
import ru.academicians.myhelper.repository.model.Deal;

import java.util.List;

public interface DefaultDealsService {
    Long createNewDeal(AddServiceRequest data, DetailedUserInfoResponse user);

    Deal findDealById(long id);

    String addSubscription(Deal deal, long subscriberId);

    DealInfoResponse getDealInformation(long id);

    List<Deal> findDealsByOwnerId(long id);

    boolean isSubscriptionExists(long dealId, long subscriberId);

    AllDealsResponse getAllDealsInfo();

    int deleteDealCascade(long id, long idFromToken);
}
