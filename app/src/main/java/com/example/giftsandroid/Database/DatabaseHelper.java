package com.example.giftsandroid.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.giftsandroid.R;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "gifts";
    private static final int DB_VERSION = 8;


    public DatabaseHelper(Context context) {
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
        if (oldVersion == 0) {
            db.execSQL("CREATE TABLE LIKED (_id INTEGER PRIMARY KEY AUTOINCREMENT, GIFT_ID INTEGER)");
        }
        if (oldVersion > 0) {
            db.execSQL("DROP TABLE GIFTS");
        }
        db.execSQL("CREATE TABLE GIFTS (_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, DESCRIPTION TEXT, PRICE INTEGER, IMAGE INTEGER)");
        insertGifts(db,"Подарочный набор-1", "Фисташки,Оливки,Арахис,Солённые крендельки,Мужские носки,Коробка,Декор", 36, R.drawable.p1);
        insertGifts(db,"Подарочный набор-13", "Банка с трубочкой,Рамка для фотографий,M&M с арахисом,Какао Nesquik,Коробка ,Декор", 44, R.drawable.p13);
        insertGifts(db,"Подарочный набор-19", "Керамическая кружка,Чай BetaTea, MACARONS,Коробка,Декор", 48, R.drawable.p19);
        insertGifts(db,"Подарочный набор-20", "Термокружка Peterhof,Kinder,Nutella,Raffaello,Коробка,Декор", 38, R.drawable.p20);
        insertGifts(db,"Подарочный набор-26", "Цветок Каланхоэ, Шоколад Ritter Sport,Мини мёд суфле Peroni,Напиток Fueztea,Свеча,Коробка,Декор", 50, R.drawable.p26);
        insertGifts(db,"Подарочный набор-33", "Kinder(20шт),Коробка,Декор", 70, R.drawable.p33);
        insertGifts(db,"Подарочный набор-34", "Гель для душа Yves Rocher, Молочка для тела Yves Rocher,Шоколадные конфеты(100гр), Свеча,Коробка,Декор", 43, R.drawable.p34);
        insertGifts(db,"Подарочный набор-35", "Конфеты Twix(100гр),Конфеты Bounty(100гр),Конфеты Snikers(100гр),Конфеты Mars(100гр),Конфеты Nesquik(100гр),Конфеты MilkiWay(100гр),Коробка,Декор", 38, R.drawable.p35);
        insertGifts(db,"Подарочный набор-43", "Керамическая кружка,Напиток кофейный Starbucks,Кофейный Starbucks,Кофейные зёрна,Металлическая ложка,Печенье,Коробка,Декор", 58, R.drawable.p43);

    }

    private void insertGifts(SQLiteDatabase db, String name, String description, int price, int image) {
        ContentValues gifrValues = new ContentValues();

        gifrValues.put("NAME", name);
        gifrValues.put("DESCRIPTION", description);
        gifrValues.put("PRICE", price);
        gifrValues.put("IMAGE", image);
        db.insert("GIFTS", null, gifrValues);
    }

    public void addToLiked(int giftId){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("GIFT_ID", giftId);
        db.insert("LIKED", null, contentValues);
        db.close();
    }

    public void deleteFromLiked(int giftId){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("LIKED", "GIFT_ID = ? ", new String[] {Integer.toString(giftId)});
        db.close();
    }

    public boolean checkGift(int giftId){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("LIKED", new String[] {"_id", "GIFT_ID"}, "GIFT_ID = ?", new String[] {Integer.toString(giftId)}, null, null, null, null);

        int count = cursor.getCount();
        if (count > 0)
            return true;
        else
            return false;
    }

}
