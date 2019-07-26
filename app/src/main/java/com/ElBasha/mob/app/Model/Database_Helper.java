package com.ElBasha.mob.app.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ElBasha.mob.app.Model.Tables.fav;
import com.ElBasha.mob.app.Model.Tables.like;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khaled-pc on 7/23/2019.
 */

public class Database_Helper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "Elbasha_db";


    public Database_Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create Block table
        db.execSQL(fav.CREATE_TABLE);
        // create Block List History table
        db.execSQL(like.CREATE_TABLE);


    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + fav.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + like.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    //TODO:#####################################################3/31/2019  like Operation  ########################################################

    public long insertProductIDLiked(Long id) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id`  will be inserted automatically.
        // no need to add them
        values.put(like.COLUMN_ID_API, id);

        // insert row
        long idreturned = db.insert(like.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return idreturned;
    }


    public List<like> getAllLikedProductedlist() {
        List<like> LikeIDsList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + like.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                like like1 = new like();
                like1.setId(cursor.getInt(cursor.getColumnIndex(like.COLUMN_ID)));
                like1.setId_api(cursor.getLong(cursor.getColumnIndex(like.COLUMN_ID_API)));

                LikeIDsList.add(like1);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return like list
        return LikeIDsList;
    }


    public boolean CheckIsDataAlreadyInDBorNotLike(Long fieldValue) {
        String Query = "Select * from " + like.TABLE_NAME + " where " + like.COLUMN_ID_API + " = " + fieldValue;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }


    public void deleteLiketByID(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(like.TABLE_NAME, like.COLUMN_ID_API + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }




    //TODO:#####################################################3/31/2019  Fav Operation  ########################################################

    public long insertFavProduct(Long id) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id`  will be inserted automatically.
        // no need to add them
        values.put(fav.COLUMN_ID_API_fav, id);

        // insert row
        long idreturned = db.insert(fav.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return idreturned;
    }


    public List<fav> getAllFavProductlist() {
        List<fav> FavIDsList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + fav.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                fav fav1 = new fav();
                fav1.setId(cursor.getInt(cursor.getColumnIndex(fav.COLUMN_ID)));
                fav1.setId_api_fav(cursor.getLong(cursor.getColumnIndex(fav.COLUMN_ID_API_fav)));

                FavIDsList.add(fav1);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return like list
        return FavIDsList;
    }


    public boolean CheckIsDataAlreadyInDBorNotFav(Long fieldValue) {
        String Query = "Select * from " + fav.TABLE_NAME + " where " + fav.COLUMN_ID_API_fav + " = " + fieldValue;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }



}