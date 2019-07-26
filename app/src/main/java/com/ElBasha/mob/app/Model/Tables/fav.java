package com.ElBasha.mob.app.Model.Tables;

/**
 * Created by khaled-pc on 7/23/2019.
 */

public class fav {
    public static final String TABLE_NAME = "Favorite";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ID_API_fav = "idfavapi";

    private int id;
    private long id_api_fav;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_ID_API_fav + " INTEGER"
                    + ")";

    public fav() {
    }

    public fav(int id, long id_api_fav) {
        this.id = id;
        this.id_api_fav = id_api_fav;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getId_api_fav() {
        return id_api_fav;
    }

    public void setId_api_fav(long id_api_fav) {
        this.id_api_fav = id_api_fav;
    }
}
