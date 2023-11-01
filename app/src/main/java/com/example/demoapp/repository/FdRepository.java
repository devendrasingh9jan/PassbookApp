package com.example.demoapp.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.demoapp.constants.PassbookConstants;
import com.example.demoapp.model.FixedDeposit;
import com.example.demoapp.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FdRepository extends Repository{

    public FdRepository(@Nullable Context context) {
        super(context);
    }


    public Boolean insert(FixedDeposit fixedDeposit){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PassbookConstants.COLUMN_USER_ID, fixedDeposit.getUserId());
        values.put(PassbookConstants.COLUMN_NUMBER, fixedDeposit.getNumber());
        values.put(PassbookConstants.COLUMN_AMOUNT, fixedDeposit.getAmount());
        values.put(PassbookConstants.COLUMN_TENURE, fixedDeposit.getTenure());
        values.put(PassbookConstants.COLUMN_RATE, fixedDeposit.getRate());
        values.put(PassbookConstants.COLUMN_MATURITY_AMOUNT, fixedDeposit.getMaturityAmount());
        values.put(PassbookConstants.COLUMN_CREATED_DATE, fixedDeposit.getCreatedDate().toString());
        values.put(PassbookConstants.COLUMN_END_DATE, fixedDeposit.getEndDate().toString());
        values.put(PassbookConstants.COLUMN_BANK_WITH_ADDRESS, fixedDeposit.getBankWithAddress());
        values.put(PassbookConstants.COLUMN_DAYS_LEFT, fixedDeposit.getDaysLeft());
        long insert = db.insert(PassbookConstants.TABLE_FIXED_DEPOSIT, null, values);
        db.close();
        if (insert!=0){
            return true;
        } else {
            return false;
        }
    }

    public List<FixedDeposit> getByNumber(Long number) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = PassbookConstants.COLUMN_NUMBER + "=?";
        String[] selectionArgs = new String[]{String.valueOf(number)};
        Cursor cursor = db.query(PassbookConstants.TABLE_FIXED_DEPOSIT, null, selection, selectionArgs, null, null, null);
        List<FixedDeposit> fixedDepositList = getFixedDeposits(cursor);
        db.close();
        return fixedDepositList;
    }

    @Nullable
    private static List<FixedDeposit> getFixedDeposits(Cursor cursor) {
        List<FixedDeposit> fixedDepositList = new ArrayList<>();

        if (cursor == null) {
            return fixedDepositList;
        }
        int idColumnIndex = cursor.getColumnIndex(PassbookConstants.COLUMN_ID);
        int userIdColumnIndex = cursor.getColumnIndex(PassbookConstants.COLUMN_USER_ID);
        int numberColumnIndex = cursor.getColumnIndex(PassbookConstants.COLUMN_NUMBER);
        int amountColumnIndex = cursor.getColumnIndex(PassbookConstants.COLUMN_AMOUNT);
        int tenureColumnIndex = cursor.getColumnIndex(PassbookConstants.COLUMN_TENURE);
        int rateColumnIndex = cursor.getColumnIndex(PassbookConstants.COLUMN_RATE);
        int maturityAmountColumnIndex = cursor.getColumnIndex(PassbookConstants.COLUMN_MATURITY_AMOUNT);
        int createdDateColumnIndex = cursor.getColumnIndex(PassbookConstants.COLUMN_CREATED_DATE);
        int endDateColumnIndex = cursor.getColumnIndex(PassbookConstants.COLUMN_END_DATE);
        int bankWithAddressColumnIndex = cursor.getColumnIndex(PassbookConstants.COLUMN_BANK_WITH_ADDRESS);
        int daysLeftColumnIndex = cursor.getColumnIndex(PassbookConstants.COLUMN_DAYS_LEFT);

        if (idColumnIndex ==-1 || userIdColumnIndex == -1 || numberColumnIndex == -1 || amountColumnIndex == -1
                || tenureColumnIndex == -1 || rateColumnIndex == -1 || maturityAmountColumnIndex == -1
                || createdDateColumnIndex == -1 || endDateColumnIndex == -1
                || bankWithAddressColumnIndex == -1 || daysLeftColumnIndex == -1) {
            return fixedDepositList;
        }

        if (cursor.moveToFirst()) {
            do {
                Integer id = cursor.getInt(idColumnIndex);
                Long userId = cursor.getLong(userIdColumnIndex);
                Long number = cursor.getLong(numberColumnIndex);
                Double amount = cursor.getDouble(amountColumnIndex);
                Integer tenure = cursor.getInt(tenureColumnIndex);
                Double rate = cursor.getDouble(rateColumnIndex);
                Double maturityAmount = cursor.getDouble(maturityAmountColumnIndex);
                String createdDateString = cursor.getString(createdDateColumnIndex);
                String endDateString = cursor.getString(endDateColumnIndex);
                String bankWithAddress = cursor.getString(bankWithAddressColumnIndex);
                Integer daysLeft = cursor.getInt(daysLeftColumnIndex);

                LocalDate createdDate = LocalDate.parse(createdDateString);
                LocalDate endDate = LocalDate.parse(endDateString);

                FixedDeposit fixedDeposit = new FixedDeposit(id, number, amount, tenure, rate, maturityAmount,
                        createdDate, endDate, bankWithAddress, daysLeft,userId);
                fixedDepositList.add(fixedDeposit);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return fixedDepositList;
    }

}
