package com.example.giftsandroid.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseLoginHalper extends SQLiteOpenHelper {

    private static final String DB_NAME = "users";
    private static final int DB_VERSION = 2;

    public DatabaseLoginHalper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateDatabase(db, oldVersion, newVersion);
    }

    private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion > 0){
            db.execSQL("DROP TABLE USERS");
        }
        db.execSQL("CREATE TABLE USERS ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "USERNAME TEXT, "
                + "EMAIL TEXT,"
                + "PASSWORD TEXT);");
    }

    public void createUser(String username, String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues userValues = new ContentValues();
        userValues.put("USERNAME", username);
        userValues.put("EMAIL", email);
        userValues.put("PASSWORD", password);

        db.insert("USERS", null, userValues);
        db.close();
    }

    public void updateUser(int userId, String username, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues userValues = new ContentValues();
        userValues.put("USERNAME", username);
        userValues.put("EMAIL", email);
        userValues.put("PASSWORD", password);

        db.update("USERS",
                userValues,
                "_id = ?",
                new String[] {Integer.toString(userId)});

        db.close();
    }

    public void deleteUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("USERS",
                "_id = ?",
                new String[] {Integer.toString(userId)});

        db.close();
    }

    public boolean checkUser(String email){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("USERS",
                new String[] {"_id"},
                "EMAIL = ?",
                new String[] {email},
                null, null, null, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0)
            return true;

        return false;
    }

    public boolean checkUser(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("USERS",
                new String[] {"_id"},
                "EMAIL = ? AND PASSWORD = ?",
                new String[] {email, password},
                null, null, null, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0)
            return true;

        return false;
    }

    public void changePassword(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues userValues = new ContentValues();

        userValues.put("PASSWORD", password);

        db.update("USERS",
                userValues,
                "EMAIL = ?",
                new String[] {email});

        db.close();
    }

    public String findUsername(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String username = "";

        Cursor cursor = db.query("USERS",
                                new String[] {"USERNAME"},
                                "EMAIL = ?",
                                new String[] {email},
                                null, null, null, null);

        if (cursor.moveToFirst()){
            username = cursor.getString(0);
        }

        return username;
    }
}
