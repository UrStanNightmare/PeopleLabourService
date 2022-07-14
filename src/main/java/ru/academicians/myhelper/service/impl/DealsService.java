package ru.academicians.myhelper.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import ru.academicians.myhelper.defaults.DefaultKeys;
import ru.academicians.myhelper.exception.ItemNotFoundException;
import ru.academicians.myhelper.model.*;
import ru.academicians.myhelper.repository.DefaultDealRepository;
import ru.academicians.myhelper.repository.model.Deal;
import ru.academicians.myhelper.service.DefaultDealsService;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static ru.academicians.myhelper.defaults.DefaultKeys.*;

@Service
public class DealsService implements DefaultDealsService {
    private DefaultDealRepository dealsRepository;

    @Autowired
    public DealsService(DefaultDealRepository dealsRepository) {
        this.dealsRepository = dealsRepository;
    }

    @Override
    public Long createNewDeal(AddServiceRequest request, DetailedUserInfoResponse user) {

        Deal deal = dealsRepository.findDealByOwnerIdAndCityAndDateAndDescriptionAndNameAndPrice(
                user.getId(),
                request.getCity(),
                request.getDate(),
                request.getDescription(),
                request.getName(),
                request.getPrice()
        );

        if (deal != null) {
            throw new IllegalArgumentException(DEAL_ALREADY_EXISTS);
        }

        String name = request.getName().trim();
        String description = request.getDescription().trim();
        String city = request.getCity().trim();
        BigInteger price = request.getPrice();
        LocalDateTime date = request.getDate();

        return dealsRepository.createNewDeal(new Deal(name, description, city, price, user.getId(), date));
    }

    @Override
    public Deal findDealById(long id) {
        Deal dealDetailsById = dealsRepository.getDealDetailsById(id);

        if (dealDetailsById == null) {
            throw new ItemNotFoundException(DEAL_NOT_FOUND_STRING);
        }
        return dealDetailsById;
    }

    @Override
    public String addSubscription(Deal deal, long subscriberId) {
        if (deal.getOwner() == subscriberId) {
            throw new IllegalArgumentException(USER_CANT_SUBSCRIBE_SELF_STRING);
        }

        if (isSubscriptionExists(deal.getId(), subscriberId)) {
            throw new IllegalArgumentException(USER_ALREADY_SUBSCRIBED_STRING);
        }


        return dealsRepository.addUserSubscription(deal.getId(), subscriberId);
    }

    @Override
    public String unsubscribeUser(long dealId, DetailedUserInfoResponse user) {
        SubscriptionRecord subscriptionInfoByDealIdAndSubscriberId = dealsRepository.getSubscriptionInfoByDealIdAndSubscriberId(dealId, user.getId());
        if (subscriptionInfoByDealIdAndSubscriberId == null) {
            return "nothing";
        }

        return dealsRepository.deleteDealSubscriptionInfoByIdAndSubscriberId(dealId, user.getId());
    }

    @Override
    public String updateDealWithData(DealInfoResponse dealInformation, UpdateDealRequest request) {
        Map<String, Object> updateArgs = request.toHashMap();
        if (updateArgs.isEmpty()) {
            return "nothing";
        }

        return dealsRepository.updateDealData(dealInformation.getId(), updateArgs);
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
    public AllDealsResponse getAllDealsInfo(DealFilter filter) {
        AllDealsResponse response = new AllDealsResponse();

        Map<String, Object> filterObjects = null;

        if (filter != null) {
            filterObjects = filter.toHashMap();
            response.setFilter(filterObjects);
        }

        List<Deal> deals = dealsRepository.findAllDeals(filterObjects);
        for (Deal deal : deals) {
            response.addDeal(new DealInfoResponse(deal));
        }

        return response;
    }

    @Override
    public int deleteDealCascade(long id, long idFromToken) {
        Deal dealDetailsById = dealsRepository.getDealDetailsById(id);

        if (dealDetailsById == null) {
            throw new ItemNotFoundException(DEAL_NOT_FOUND_STRING);
        }

        if (dealDetailsById.getOwner() != idFromToken) {
            throw new BadCredentialsException(BAD_TOKEN);
        }

        if (dealDetailsById == null) {
            throw new IllegalArgumentException(CAN_T_DELETE_DEAL);
        }

        return dealsRepository.deleteDealById(id);
    }


}
