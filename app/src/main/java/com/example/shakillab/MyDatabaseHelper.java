package com.example.shakillab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    //Database Name
    private static final String DATABASE_NAME = "contact_database";
    //Database Table name
    private static final String TABLE_NAME = "CONTACT_DATA";
    //Table columns
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String NUMBER = "number";
    private SQLiteDatabase sqLiteDatabase;

    private static final String CREATE_TABLE = "create table " + TABLE_NAME +"("+ID+
            " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME + " TEXT NOT NULL,"+NUMBER+" TEXT NOT NULL);";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertData(String name, String number){
       SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,name);
        contentValues.put(NUMBER,number);
         long rowId = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
         return rowId;
    }
    public Cursor displayData(){
       SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
       Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);
       return cursor;
    }
    public boolean updateData(String id, String name, String number){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID,id);
        contentValues.put(NAME,name);
        contentValues.put(NUMBER,number);
        sqLiteDatabase.update(TABLE_NAME,contentValues,ID+" = ?",new String[]{
               id
        });
        return true;

    }
    public int deleteData(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
       return sqLiteDatabase.delete(TABLE_NAME,ID+" = ?",new String[]{id});

    }
}
