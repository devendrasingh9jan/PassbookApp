package com.example.demoapp.constants;

public class PassbookConstants {

    public static final String COLUMN_PHONE = "phone";

    public PassbookConstants() {
    }

    public static final String DATABASE_NAME = "Passbook";
    public static final int DATABASE_VERSION = 14;

    // Columns of user table
    public static final String TABLE_USER = "user";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_GENDER = "gender";

    // Columns of fixed-deposit table
    public static final String TABLE_FIXED_DEPOSIT = "fixed_deposit";
    public static final String COLUMN_USER_ID = "user_id";  // Foreign key column
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_TENURE = "tenure";
    public static final String COLUMN_RATE = "rate";
    public static final String COLUMN_MATURITY_AMOUNT = "maturity_amount";
    public static final String COLUMN_CREATED_DATE = "created_date";
    public static final String COLUMN_END_DATE = "end_date";
    public static final String COLUMN_BANK_WITH_ADDRESS = "bank_with_address";
    public static final String COLUMN_DAYS_LEFT = "days_left";

    public static final String COLUMN_NOTES ="notes";

}
