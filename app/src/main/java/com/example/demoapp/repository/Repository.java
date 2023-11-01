package com.example.demoapp.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.demoapp.constants.PassbookConstants;

public class Repository extends SQLiteOpenHelper {

    public Repository(@Nullable Context context) {
        super(context, PassbookConstants.DATABASE_NAME, null, PassbookConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String user_create_query = "CREATE TABLE " + PassbookConstants.TABLE_USER + " (" +
                PassbookConstants.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PassbookConstants.COLUMN_NAME + " TEXT," +
                PassbookConstants.COLUMN_EMAIL + " TEXT," +
                PassbookConstants.COLUMN_PHONE + " TEXT," +
                PassbookConstants.COLUMN_PASSWORD + " TEXT," +
                PassbookConstants.COLUMN_GENDER + " TEXT)";
        db.execSQL(user_create_query);
        Log.d("user-table","user table created");

        String fixed_deposit_create_query = "CREATE TABLE " + PassbookConstants.TABLE_FIXED_DEPOSIT + " (" +
                PassbookConstants.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PassbookConstants.COLUMN_NUMBER + " INTEGER, " +
                PassbookConstants.COLUMN_TENURE + " INTEGER, " +
                PassbookConstants.COLUMN_RATE + " REAL, " +
                PassbookConstants.COLUMN_MATURITY_AMOUNT + " REAL, " +
                PassbookConstants.COLUMN_CREATED_DATE + " TEXT, " +
                PassbookConstants.COLUMN_END_DATE + " TEXT, " +
                PassbookConstants.COLUMN_BANK_WITH_ADDRESS + " TEXT, " +
                PassbookConstants.COLUMN_DAYS_LEFT + " INTEGER, " +
                PassbookConstants.COLUMN_AMOUNT + " INTEGER, " +
                PassbookConstants.COLUMN_USER_ID + " INTEGER, " +  // Foreign key reference
                "FOREIGN KEY (" + PassbookConstants.COLUMN_USER_ID + ") REFERENCES " +
                PassbookConstants.TABLE_USER + "(" + PassbookConstants.COLUMN_ID + "))";
        db.execSQL(fixed_deposit_create_query);
        Log.d("fixed_deposit-table","fixed_deposit table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PassbookConstants.TABLE_USER);
        Log.d("user-table","user table dropped");
        db.execSQL("DROP TABLE IF EXISTS " + PassbookConstants.TABLE_FIXED_DEPOSIT);
        Log.d("fixed_deposit-table","fixed_deposit table dropped");
        onCreate(db);
    }

}
