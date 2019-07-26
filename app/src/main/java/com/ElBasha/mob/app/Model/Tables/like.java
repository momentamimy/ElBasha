package com.ElBasha.mob.app.Model.Tables;

/**
 * Created by khaled-pc on 7/23/2019.
 */

public class like {
    public static final String TABLE_NAME = "LikeTable";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ID_API = "idapi";

    private int id;
    private long id_api;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_ID_API + " INTEGER"
                    + ")";

    public like() {
    }

    public like(int id, long id_api) {
        this.id = id;
        this.id_api = id_api;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getId_api() {
        return id_api;
    }

    public void setId_api(long id_api) {
        this.id_api = id_api;
    }
}
