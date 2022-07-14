package ru.academicians.myhelper.repository.mapper.user;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.academicians.myhelper.model.DetailedUserInfoResponse;
import ru.academicians.myhelper.model.ShortDealInfoResponse;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static ru.academicians.myhelper.defaults.DefaultRequirements.DB_TEXT_DATE_TIME_FORMATTER;

@Component
public class UserWithDetailsRowMapper implements RowMapper<DetailedUserInfoResponse> {

    @Override
    public DetailedUserInfoResponse mapRow(ResultSet rs, int i) throws SQLException {
        DetailedUserInfoResponse result = new DetailedUserInfoResponse();
        result.setId(rs.getLong("id"));
        result.setFirstName(rs.getString("first_name"));
        result.setLastName(rs.getString("last_name"));
        result.setMiddleName(rs.getString("middle_name"));
        result.setLogin(rs.getString("login"));
        result.setPhoneNumber(rs.getString("phone_number"));

        String deals = rs.getString("deals");

        if (deals != null){
            String[] dealsSplit = deals.split(",");
            for (String dealLine : dealsSplit) {
                String[] dealInfoSplit = dealLine.split("\\|");
                if (dealInfoSplit.length > 0){
                    ShortDealInfoResponse shortDealInfoResponse = new ShortDealInfoResponse();
                    shortDealInfoResponse.setId(Long.parseLong(dealInfoSplit[0]));
                    shortDealInfoResponse.setCity(dealInfoSplit[1]);
                    LocalDateTime dateTime = LocalDateTime.parse(dealInfoSplit[2], DB_TEXT_DATE_TIME_FORMATTER);
                    shortDealInfoResponse.setDate(dateTime);
                    shortDealInfoResponse.setDescription(dealInfoSplit[3]);
                    shortDealInfoResponse.setName(dealInfoSplit[4]);
                    shortDealInfoResponse.setPrice(new BigDecimal(dealInfoSplit[5]).toBigInteger());

                    result.addPlacedDeals(shortDealInfoResponse);
                }else {
                    break;
                }

            }
        }
        return result;
    }
}
