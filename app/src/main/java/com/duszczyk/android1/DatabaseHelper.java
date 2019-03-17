package com.duszczyk.android1;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Admin on 04.04.2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Books.db";
    public static final String TABLE_NAME = "books_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "SURNAME_AUTHOR";
    public static final String COL_4 = "PRICE";
    public static final String COL_5 = "DESCRIBE";
    public static final String COL_6 = "CATEGORY";
    public static final String COL_7 = "IMAGE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,SURNAME_AUTHOR TEXT," +
                "PRICE FLOAT, DESCRIBE TEXT, CATEGORY TEXT, IMAGE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }


    public boolean insertDataImage(String name, String surname, float price, String describe, String category, String image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, surname);
        contentValues.put(COL_4, price);
        contentValues.put(COL_5, describe);
        contentValues.put(COL_6, category);
        contentValues.put(COL_7, image);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertData(String name, String surname, float price, String describe, String category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, surname);
        contentValues.put(COL_4, price);
        contentValues.put(COL_5, describe);
        contentValues.put(COL_6, category);
        contentValues.put(COL_7, "https://thumbs.dreamstime.com/b/ksi%C4%85%C5%BCki-i-s%C5%82o%C5%84ca-logo-49359167.jpg");
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME, null);
        return res;
    }

    public Cursor getDataCategory(String cat) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+" where CATEGORY="+"'"+cat+"'", null);
        return res;
    }

    public Cursor getDataName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+" where NAME="+"'"+name+"'", null);
        return res;
    }

//    public Cursor getDataByCategory(String category) {
//
//    }

    public boolean updateData(String id, String name, String surname, float price, String describe, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, surname);
        contentValues.put(COL_4, price);
        contentValues.put(COL_5, describe);
        contentValues.put(COL_6, category);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] { id });
        return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }

    public Integer deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, null, null);
    }

}
