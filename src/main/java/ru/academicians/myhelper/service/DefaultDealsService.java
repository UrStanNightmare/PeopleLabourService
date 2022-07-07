package ru.academicians.myhelper.service;

import ru.academicians.myhelper.model.AddServiceRequest;
import ru.academicians.myhelper.model.DealInfoResponse;
import ru.academicians.myhelper.repository.model.Deal;
import ru.academicians.myhelper.repository.model.User;

public interface DefaultDealsService {
    long createNewDeal(AddServiceRequest data, User user);

    Deal findDealById(long id);

    long subscribeUserToDealAndSave(User user, Deal deal);

    DealInfoResponse getDealInformation(long id);
}
