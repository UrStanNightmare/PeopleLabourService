package ru.academicians.myhelper.service;

import org.postgresql.util.PGTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.academicians.myhelper.exception.ItemNotFoundException;
import ru.academicians.myhelper.model.AddServiceRequest;
import ru.academicians.myhelper.model.DealInfoResponse;
import ru.academicians.myhelper.repository.DefaultServiceRepository;
import ru.academicians.myhelper.repository.model.Deal;
import ru.academicians.myhelper.repository.model.User;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static ru.academicians.myhelper.defaults.DefaultKeys.DEAL_NOT_FOUND_STRING;
import static ru.academicians.myhelper.defaults.DefaultRequirements.TIME_FORMATTER;

@Service
public class DealsService implements DefaultDealsService {
    private DefaultServiceRepository dealsRepository;

    @Autowired
    public DealsService(DefaultServiceRepository dealsRepository) {
        this.dealsRepository = dealsRepository;
    }

    @Override
    public long createNewDeal(AddServiceRequest request, User user) {

        String name = request.getName().trim();
        String description = request.getDescription().trim();
        String city = request.getCity().trim();
        BigInteger price = request.getPrice();
        LocalDateTime date = request.getDate();

        Deal saveDeal = dealsRepository.save(new Deal(name, description, city, price, user, date));

        return saveDeal.getId();
    }

    @Override
    public Deal findDealById(long id) {
        return dealsRepository.findDealById(id);
    }

    @Override
    public long subscribeUserToDealAndSave(User user, Deal deal) {
        deal.addSubscriber(user);

        Deal save = dealsRepository.save(deal);

        return save.getId();
    }

    @Override
    public DealInfoResponse getDealInformation(long id) {
        List<Object[]> dealDetailsById = dealsRepository.getDealDetailsById(id);

        boolean firstDone = false;

        if (dealDetailsById == null || dealDetailsById.isEmpty()) {
            throw new ItemNotFoundException(DEAL_NOT_FOUND_STRING);
        }

        DealInfoResponse response = new DealInfoResponse();

        for (Object[] objects : dealDetailsById) {
            if (!firstDone) {
                firstDone = true;
                long deal_id = ((BigInteger) objects[4]).longValue();
                String service_city = (String) objects[5];
                LocalDateTime service_date = ((Timestamp) objects[6]).toLocalDateTime();
                String service_desc = (String) objects[7];
                String service_name = (String) objects[8];
                BigInteger service_price = ((BigDecimal) objects[9]).toBigInteger();
                long owner_id = ((BigInteger) objects[10]).longValue();



                response.setId(deal_id);
                response.setOwnerId(owner_id);
                response.setName(service_name);
                response.setDescription(service_desc);
                response.setCity(service_city);
                response.setDate(service_date);
                response.setPrice(service_price);
            }

            long subscriberId = ((BigInteger) objects[0]).longValue();
            String first_name = (String) objects[1];
            String last_name = (String) objects[2];
            String patronymic = (String) objects[3];

            response.addSubscriber(new User(subscriberId, last_name, first_name, patronymic, null));

        }

        return response;
    }
}
