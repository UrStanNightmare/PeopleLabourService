package ru.academicians.myhelper.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.academicians.myhelper.defaults.DefaultKeys;
import ru.academicians.myhelper.exception.ItemNotFoundException;
import ru.academicians.myhelper.model.AddServiceRequest;
import ru.academicians.myhelper.model.AllDealsResponse;
import ru.academicians.myhelper.model.DealInfoResponse;
import ru.academicians.myhelper.model.SubscriptionRecord;
import ru.academicians.myhelper.repository.DefaultDealRepository;
import ru.academicians.myhelper.repository.model.Deal;
import ru.academicians.myhelper.repository.model.User;
import ru.academicians.myhelper.service.DefaultDealsService;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class DealsService implements DefaultDealsService {
    private DefaultDealRepository dealsRepository;

    @Autowired
    public DealsService(DefaultDealRepository dealsRepository) {
        this.dealsRepository = dealsRepository;
    }

    @Override
    public long createNewDeal(AddServiceRequest request, User user) {

        String name = request.getName().trim();
        String description = request.getDescription().trim();
        String city = request.getCity().trim();
        BigInteger price = request.getPrice();
        LocalDateTime date = request.getDate();

        long saveDealId = dealsRepository.createNewDeal(new Deal(name, description, city, price, user.getId(), date));

        return saveDealId;
    }

    @Override
    public Deal findDealById(long id) {
        return dealsRepository.getDealDetailsById(id);
    }

    @Override
    public String addSubscription(long dealId, long subscriberId) {
        return dealsRepository.addUserSubscription(dealId, subscriberId);
    }

    @Override
    public DealInfoResponse getDealInformation(long id) {
        Deal dealDetailsById = dealsRepository.getDealDetailsById(id);
        if (dealDetailsById == null) {
            throw new ItemNotFoundException(DefaultKeys.DEAL_NOT_FOUND_STRING);
        }

        DealInfoResponse response = new DealInfoResponse();

        response.updateData(dealDetailsById);

        return response;
    }

    @Override
    public List<Deal> findDealsByOwnerId(long id) {
        return dealsRepository.findDealsByUserId(id);
    }

    @Override
    public boolean isSubscriptionExists(long dealId, long subscriberId) {
        SubscriptionRecord subscriptionInfoByDealIdAndSubscriberId = dealsRepository.getSubscriptionInfoByDealIdAndSubscriberId(dealId, subscriberId);
        if (subscriptionInfoByDealIdAndSubscriberId != null) {
            return true;
        }
        return false;
    }

    @Override
    public AllDealsResponse getAllDealsInfo() {
        AllDealsResponse response = new AllDealsResponse();

        List<Deal> deals = dealsRepository.findAllDeals();
        for (Deal deal : deals) {
            response.addDeal(new DealInfoResponse(deal));
        }

        return response;
    }
}
