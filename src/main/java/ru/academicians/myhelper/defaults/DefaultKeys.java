package ru.academicians.myhelper.defaults;

public class DefaultKeys {
    //Tables
    public static final String USERS_TABLE_NAME = "users";
    public static final String SERVICES_TABLE_NAME = "services";

    //User

    public static final String ID_KEY = "id";
    public static final String FIRST_NAME_KEY = "first_name";
    public static final String LAST_NAME_KEY = "last_name";
    public static final String PATRONYMIC_KEY = "patronymic";

    //Deal

    public static final String SERVICE_OWNER_ID_KEY = "owner_id";
    public static final String SERVICE_NAME_KEY = "service_name";
    public static final String SERVICE_DESCRIPTION_KEY = "service_desc";
    public static final String SERVICE_CITY_KEY = "service_city";
    public static final String SERVICE_PRICE_KEY = "service_price";
    public static final String SERVICE_DATE_KEY = "service_date";


    //Exceptions
    public static final String USER_NOT_FOUND_STRING = "User not found!";
    public static final String USER_CANT_SUBSCRIBE_SELF_STRING = "User can't subscribe to his own deal!";
    public static final String DEAL_NOT_FOUND_STRING = "Deal not found!";




    private DefaultKeys() {
    }
}
