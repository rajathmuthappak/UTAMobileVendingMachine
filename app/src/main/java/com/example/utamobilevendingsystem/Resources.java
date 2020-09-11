package com.example.utamobilevendingsystem;

public class Resources {

    // Database name
    public static final String DATABASE_NAME = "utaVendingMachine.db";

    // DATABASE VERSION
    public static final int DATABASE_VERSION = 1;

    // USER_CREDENTIALS
    public static final String TABLE_USER_CREDS = "user_creds";
    public static final String USER_CREDS_USER_ID = "user_id";  // Primary Key
    public static final String USER_CREDS_USERNAME = "username";
    public static final String USER_CREDS_PASSWORD = "password";
    public static final String USER_CREDS_ROLE = "role";

    // USER_DETAILS
    public static final String TABLE_USER_DETAILS = "user_details";
    public static final String USER_DETAILS_ID = "user_id"; //Foreign Key  -> USER_CREDS_USER_ID
    public static final String USER_DETAILS_USERNAME="username";
    public static final String USER_DETAILS_FNAME = "first_name";
    public static final String USER_DETAILS_LNAME = "last_name";
    public static final String USER_DETAILS_UTA_ID = "uta_id";
    public static final String USER_DETAILS_DOB = "dob";
    public static final String USER_DETAILS_PHONE = "phone";
    public static final String USER_DETAILS_EMAIL_ID = "emailid";
    public static final String USER_DETAILS_ADDRESS = "address";
    public static final String USER_DETAILS_CITY = "city";
    public static final String USER_DETAILS_STATE = "state";
    public static final String USER_DETAILS_ZIP = "zip";

    // VEHICLE INVENTORY
    public static final String TABLE_VEHICLE_INVENTORY = "vehicle_inventory";
    public static final String VEHICLE_INVENTORY_ID = "id";
    public static final String VEHICLE_INVENTORY_VEHICLE_ID = "vehicle_id";
    public static final String VEHICLE_INVENTORY_ITEM_ID = "item_id";
    public static final String VEHICLE_INVENTORY_QUANTITY = "quantity";

    // CART
    public static final String TABLE_CART = "cart";
    public static final String CART_ID = "cart_ID";
    public static final String CART_ITEM_ID = "cart_item_id";
    public static final String CART_QUANTITY = "quantity";

    // ORDER
    public static final String TABLE_ORDER = "orders";
    public static final String ORDER_ID = "order_id";
    public static final String ORDER_ITEM_ID = "order_item_id";
    public static final String ORDER_ITEM_QUANTITY = "order_item_quantity";
    public static final String ORDER_ITEM_PRICE = "order_item_price"; // Product of item price and quantity
    public static final String ORDER_STATUS_ID = "order_status_id"; // foreign KEY -> STATUS_ID
    public static final String ORDER_VEHICLE_ID = "order_vehicle_id"; //foreign key -> vehicle - vehicle id
    // STATUS
    public static final String TABLE_STATUS = "status";
    public static final String STATUS_ID = "status_id";
    public static final String STATUS_DESCRIPTION = "description";

    // ITEM
    public static final String TABLE_ITEM = "item";
    public static final String ITEM_ID = "item_id";
    public static final String ITEM_NAME = "item_name";
    public static final String ITEM_PRICE = "item_price";

    // VEHICLE
    public static final String TABLE_VEHICLE = "vehicle";
    public static final String VEHICLE_ID = "vehicle_id";
    public static final String VEHICLE_NAME = "name";
    public static final String VEHICLE_TYPE = "type";
    public static final String VEHICLE_AVAILABILITY = "availability";
    public static final String VEHICLE_LOCATION_ID = "location_id";
    public static final String VEHICLE_USER_ID = "user_id";
    public static final String VEHICLE_SCHEDULE_TIME = "schedule_time";

    // LOCATION
    public static final String TABLE_LOCATION = "location";
    public static final String LOCATION_ID = "location_id";
    public static final String LOCATION_NAME = "locationName";
    public static final String LOCATION_SCHEDULE = "schedule";

    // PAYMENTS
    public static final String TABLE_PAYMENTS = "payments";
    public static final String PAYMENTS_ID = "id";
    public static final String PAYMENTS_USER_ID = "user_id";
    public static final String CARD_NUMBER = "card_number";
    public static final String EXPIRATION_DATE = "expiration_date";
    public static final String CARD_TYPE = "card_type";

    // OPERATOR VEHICLE
    public static final String TABLE_OPERATOR_VEHICLE = "operator_vehicle";
    public static final String OPERATOR_VEHICLE_USER_ID = "user_id";
    public static final String OPERATOR_VEHICLE_VEHICLE_ID = "vehicle_id";


    // USER - ORDER table
    public static final String TABLE_USER_ORDER = "user_order";
    public static final String USER_ORDER_USERID = "user_id";
    public static final String USER_ORDER_ORDERID = "order_id";

    // USER - CART table
    public static final String TABLE_USER_CART = "user_cart";
    public static final String USER_CART_USERID = "user_id";
    public static final String USER_CART_ID = "cart_id";

    public static final String CREATE_TABLE_USER_CREDENTIALS = "CREATE TABLE IF NOT EXISTS " + TABLE_USER_CREDS
            + "(" + USER_CREDS_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + USER_CREDS_USERNAME + " TEXT,"
            + USER_CREDS_PASSWORD + " TEXT,"
            + USER_CREDS_ROLE + " TEXT "
            + ")";

    public static final String CREATE_TABLE_USER_DETAILS = "CREATE TABLE IF NOT EXISTS " + TABLE_USER_DETAILS
            + "(" + USER_DETAILS_ID + " INTEGER PRIMARY KEY, "
            + USER_DETAILS_USERNAME + " TEXT, "
            + USER_DETAILS_FNAME + " TEXT, "
            + USER_DETAILS_LNAME + " TEXT, "
            + USER_DETAILS_UTA_ID + " INTEGER, "
            + USER_DETAILS_DOB + " TEXT, "
            + USER_DETAILS_PHONE + " TEXT, "
            + USER_DETAILS_EMAIL_ID + " TEXT, "
            + USER_DETAILS_ADDRESS + " TEXT, "
            + USER_DETAILS_CITY + " TEXT, "
            + USER_DETAILS_STATE + " TEXT, "
            + USER_DETAILS_ZIP + " TEXT, "
            + "CONSTRAINT fk_userdetails FOREIGN KEY("+USER_DETAILS_ID+") REFERENCES "+ TABLE_USER_CREDS+"(" +USER_CREDS_USER_ID+")"
            + ")";

    public static final String CREATE_TABLE_ITEM = "CREATE TABLE IF NOT EXISTS " + TABLE_ITEM
            + "(" + ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ITEM_NAME + " TEXT, "
            + ITEM_PRICE + " INTEGER" + ")";

    public static final String CREATE_TABLE_STATUS = "CREATE TABLE IF NOT EXISTS " + TABLE_USER_CREDS
            + "(" + STATUS_ID + " INTEGER PRIMARY KEY, "
            + STATUS_DESCRIPTION + " TEXT " + ")";

    public static final String CREATE_TABLE_LOCATION = "CREATE TABLE IF NOT EXISTS " + TABLE_LOCATION
            + "(" + LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + LOCATION_NAME + " TEXT, "
            + LOCATION_SCHEDULE + " INTEGER" + ")";

    public static final String CREATE_TABLE_VEHICLE = "CREATE TABLE IF NOT EXISTS " + TABLE_VEHICLE
            + "(" + VEHICLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + VEHICLE_NAME + " TEXT, "
            + VEHICLE_TYPE + " TEXT, "
            + VEHICLE_AVAILABILITY + " TEXT, "
            + VEHICLE_LOCATION_ID + " INTEGER, "
            + VEHICLE_USER_ID + " INTEGER, "
            + VEHICLE_SCHEDULE_TIME + " TEXT , "
            + "CONSTRAINT fk_vehicle FOREIGN KEY ("+VEHICLE_LOCATION_ID+") REFERENCES "+ TABLE_LOCATION+"(" +LOCATION_ID+"), "
            + "CONSTRAINT fk_vehicle_user FOREIGN KEY ("+VEHICLE_USER_ID+") REFERENCES "+ TABLE_USER_CREDS+"(" +USER_CREDS_USER_ID+")"
            + ")";

    public static final String CREATE_TABLE_VEHICLE_INVENTORY = "CREATE TABLE IF NOT EXISTS " + TABLE_VEHICLE_INVENTORY
            + "(" + VEHICLE_INVENTORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + VEHICLE_INVENTORY_VEHICLE_ID + " INTEGER, "
            + VEHICLE_INVENTORY_ITEM_ID + " INTEGER, "
            + VEHICLE_INVENTORY_QUANTITY + " INTEGER, "
            + "CONSTRAINT fk_operator_vehicle_vehicle FOREIGN KEY ("+VEHICLE_INVENTORY_VEHICLE_ID+") REFERENCES "+ TABLE_VEHICLE+"(" +VEHICLE_ID+"), "
            + "CONSTRAINT fk_operator_vehicle_item FOREIGN KEY ("+VEHICLE_INVENTORY_ITEM_ID+") REFERENCES "+ TABLE_ITEM+"(" +ITEM_ID+")"
            + ")";

    public static final String CREATE_TABLE_OPERATOR_VEHICLE = "CREATE TABLE IF NOT EXISTS " + TABLE_OPERATOR_VEHICLE
            + "(" + OPERATOR_VEHICLE_USER_ID + " INTEGER, "
            + OPERATOR_VEHICLE_VEHICLE_ID + " INTEGER, "
            + "CONSTRAINT fk_table_operator_vehicle_user_id FOREIGN KEY ("+OPERATOR_VEHICLE_USER_ID+") REFERENCES "+ TABLE_USER_CREDS+"(" +USER_CREDS_USER_ID+"), "
            + "CONSTRAINT fk_table_operator_vehicle_vehicle_id FOREIGN KEY ("+OPERATOR_VEHICLE_VEHICLE_ID+") REFERENCES "+ TABLE_VEHICLE+"(" +VEHICLE_ID+")"
            + ")";

    public static final String CREATE_TABLE_USER_CART = "CREATE TABLE IF NOT EXISTS " + TABLE_USER_CART
            + "(" + USER_CART_USERID + " INTEGER, "
            + USER_CART_ID + " INTEGER PRIMARY KEY, "
            + "CONSTRAINT fk_table_user_cart_cart_id FOREIGN KEY ("+USER_CART_USERID+") REFERENCES "+ TABLE_USER_CREDS+"(" +USER_CREDS_USER_ID+")"
            + ")";

    public static final String CREATE_TABLE_CART = "CREATE TABLE IF NOT EXISTS " + TABLE_CART
            + "(" + CART_ID + " INTEGER, "
            + CART_ITEM_ID + " INTEGER, "
            + CART_QUANTITY + " INTEGER, "
            + "CONSTRAINT fk_table_cart  FOREIGN KEY ("+CART_ID+") REFERENCES "+ TABLE_USER_CART+"(" +USER_CART_ID+"), "
            + "CONSTRAINT fk_table_cart  FOREIGN KEY ("+CART_ITEM_ID+") REFERENCES "+ TABLE_ITEM+"(" +ITEM_ID+")"
            + ")";

    public static final String CREATE_TABLE_ORDER = "CREATE TABLE IF NOT EXISTS " + TABLE_ORDER
            + "(" + ORDER_ID + " INTEGER, "
            + ORDER_ITEM_ID + " INTEGER, "
            + ORDER_VEHICLE_ID + " INTEGER, "
            + ORDER_ITEM_QUANTITY + " INTEGER, "
            + ORDER_ITEM_PRICE + " INTEGER, "
            + ORDER_STATUS_ID + " INTEGER, "
            + "CONSTRAINT fk_table_order_item FOREIGN KEY ("+ORDER_ITEM_ID+") REFERENCES "+ TABLE_ITEM+"(" +ITEM_ID+"), "
            + "CONSTRAINT fk_table_order_vehicle FOREIGN KEY ("+ORDER_VEHICLE_ID+") REFERENCES "+ TABLE_VEHICLE+"(" +VEHICLE_ID+"), "
            + "CONSTRAINT fk_table_order_status FOREIGN KEY ("+ORDER_STATUS_ID+") REFERENCES "+ TABLE_STATUS+"(" +STATUS_ID+")"
            + ")";

    public static final String CREATE_TABLE_PAYMENTS = "CREATE TABLE IF NOT EXISTS " + TABLE_PAYMENTS
            + "(" + PAYMENTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PAYMENTS_USER_ID + " INTEGER, "
            + CARD_NUMBER + " INTEGER, "
            + EXPIRATION_DATE + " TEXT, "
            + CARD_TYPE + " TEXT, "
            + "CONSTRAINT fk_table_payments FOREIGN KEY ("+PAYMENTS_USER_ID+") REFERENCES "+ TABLE_USER_CREDS+"(" +USER_CREDS_USER_ID+")"
            + ")";

    public static final String CREATE_TABLE_USER_ORDER = "CREATE TABLE IF NOT EXISTS " + TABLE_USER_ORDER
            + "(" + USER_ORDER_USERID + " INTEGER, "
            + USER_ORDER_ORDERID + " INTEGER, "
            + "CONSTRAINT fk_table_user_order_user_id FOREIGN KEY ("+USER_ORDER_USERID+") REFERENCES "+ TABLE_USER_CREDS+"(" +USER_CREDS_USER_ID+"), "
            + "CONSTRAINT fk_table_user_order_order_id FOREIGN KEY ("+USER_ORDER_ORDERID+") REFERENCES "+ TABLE_ORDER+"(" +ORDER_ID+")"
            + ")";

    public static String getDatabaseName(){
        return DATABASE_NAME;
    }

    public static int getDatabaseVersion(){
        return DATABASE_VERSION;
    }
}