package com.example.demoapp.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.demoapp.constants.PassbookConstants;
import com.example.demoapp.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository extends Repository {
    public UserRepository(@Nullable Context context) {
        super(context);
    }
    public Boolean insert(User user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PassbookConstants.COLUMN_NAME,user.getName());
        contentValues.put(PassbookConstants.COLUMN_EMAIL,user.getEmail());
        contentValues.put(PassbookConstants.COLUMN_PHONE,user.getPhone());
        contentValues.put(PassbookConstants.COLUMN_PASSWORD,user.getPassword());
        contentValues.put(PassbookConstants.COLUMN_GENDER,user.getGender());
        Optional<User> userOptional = findByEmailId(db, user.getEmail());
        long insert = 0;
        if (!userOptional.isPresent()) {
            insert = db.insert(PassbookConstants.TABLE_USER, null, contentValues);
        }
        db.close();
        if (insert != 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean delete(Long userId){
        SQLiteDatabase db = getWritableDatabase();
        int delete = db.delete(PassbookConstants.TABLE_USER, PassbookConstants.COLUMN_ID + " = " + userId, null);
        db.close();
        return delete!=1;
    }

    public Optional<User> findById(Long userId){
        SQLiteDatabase db = getWritableDatabase();
        String selection = PassbookConstants.COLUMN_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(userId)};
        Cursor cursor = db.query(PassbookConstants.TABLE_USER, null, selection, selectionArgs, null, null, null);
        Optional<User> userOptional = getUser(cursor);
        db.close();
        return userOptional;
    }
    public Optional<User> findByEmailId(String emailId){
        SQLiteDatabase db = getWritableDatabase();
        String selection = PassbookConstants.COLUMN_EMAIL + "=?";
        String[] selectionArgs = new String[]{String.valueOf(emailId)};
        Cursor cursor = db.query(PassbookConstants.TABLE_USER, null, selection, selectionArgs, null, null, null);
        Optional<User> userOptional = getUser(cursor);
        db.close();
        return userOptional;
    }

    public Optional<User> findByEmailId(SQLiteDatabase db, String emailId) {
        String selection = PassbookConstants.COLUMN_EMAIL + "=?";
        String[] selectionArgs = new String[]{String.valueOf(emailId)};
        Cursor cursor = db.query(PassbookConstants.TABLE_USER, null, selection, selectionArgs, null, null, null);
        Optional<User> userOptional = getUser(cursor);
        return userOptional;
    }

    public Optional<User> loginByEmail(String email, String password){
        SQLiteDatabase db = getWritableDatabase();
        String selection = PassbookConstants.COLUMN_EMAIL + "=?" + " AND " + PassbookConstants.COLUMN_PASSWORD + "=?";
        String[] selectionArgs = new String[]{String.valueOf(email),String.valueOf(password)};
        Cursor cursor = db.query(PassbookConstants.TABLE_USER, null, selection, selectionArgs, null, null, null);
        Optional<User> userOptional = getUser(cursor);
        db.close();
        return userOptional;
    }

    public Optional<User> loginByPhone(String phone, String password){
        SQLiteDatabase db = getWritableDatabase();
        String selection = PassbookConstants.COLUMN_PHONE + "=?" + " AND " + PassbookConstants.COLUMN_PASSWORD + "=?";
        String[] selectionArgs = new String[]{String.valueOf(phone),String.valueOf(password)};
        Cursor cursor = db.query(PassbookConstants.TABLE_USER, null, selection, selectionArgs, null, null, null);
        Optional<User> userOptional = getUser(cursor);
        db.close();
        return userOptional;
    }

    @Nullable
    public static Optional<User> getUser(Cursor cursor) {
        List<User> userList = new ArrayList<>();
        int idColumnIndex = cursor.getColumnIndex(PassbookConstants.COLUMN_ID);
        int nameColumnIndex = cursor.getColumnIndex(PassbookConstants.COLUMN_NAME);
        int emailColumnIndex = cursor.getColumnIndex(PassbookConstants.COLUMN_EMAIL);
        int passwordColumnIndex = cursor.getColumnIndex(PassbookConstants.COLUMN_PASSWORD);
        int genderColumnIndex = cursor.getColumnIndex(PassbookConstants.COLUMN_GENDER);
        int phoneColumnIndex = cursor.getColumnIndex(PassbookConstants.COLUMN_PHONE);
        if (cursor != null && idColumnIndex!=-1 && nameColumnIndex!=-1 && emailColumnIndex!=-1 && passwordColumnIndex!=-1 && genderColumnIndex!=-1 ) {
            if (cursor.moveToFirst()) {
                do {
                    Long id = cursor.getLong(idColumnIndex);
                    String name = cursor.getString(nameColumnIndex);
                    String emailId = cursor.getString(emailColumnIndex);
                    String userPassword = cursor.getString(passwordColumnIndex);
                    String gender = cursor.getString(genderColumnIndex);
                    String phone = cursor.getString(phoneColumnIndex);

                    User user = new User(id, name, emailId, phone, userPassword, gender);
                    userList.add(user);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return userList.stream().findFirst();
    }

    public int updatePassword(Long id, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PassbookConstants.COLUMN_PASSWORD, password);
        int update = db.update(PassbookConstants.TABLE_USER, values, PassbookConstants.COLUMN_ID + " = " + id, null);
        db.close();
        return update;
    }

}
