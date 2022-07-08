package ru.academicians.myhelper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.academicians.myhelper.model.AddServiceRequest;
import ru.academicians.myhelper.model.DealInfoResponse;
import ru.academicians.myhelper.model.SubscriptionRecord;
import ru.academicians.myhelper.repository.DefaultDealRepository;
import ru.academicians.myhelper.repository.model.Deal;
import ru.academicians.myhelper.repository.model.User;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

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
        List<SubscriptionRecord> allSubscribersByDealId = dealsRepository.getAllSubscribersByDealId(id);

        DealInfoResponse response = new DealInfoResponse();

        response.updateData(dealDetailsById);

        for (SubscriptionRecord subscriptionRecord : allSubscribersByDealId) {
            response.addSubscriber(subscriptionRecord.getSubscriberId());
        }

        return response;
    }
}
