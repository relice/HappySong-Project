package com.happysong.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 基础数据库
 */
public abstract class BaseDBHelper {
    public static final String DB_NAME = "happy_random.db";
    public static final int DB_VERSION_NUMBER = 1;
    public Context context;
    public DatabaseHelper openHelper;
    public SQLiteDatabase db;

    public BaseDBHelper(Context context) {
        this.context = context;
        openHelper = new DatabaseHelper(context, DB_NAME, null, DB_VERSION_NUMBER);
        db = openHelper.getWritableDatabase();
    }

    /**
     * 子类必须覆盖，每次需要创建的表
     */
    public abstract void onDataBaseCreat(SQLiteDatabase db);

    public abstract void onDataBaseUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);

    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            onDataBaseCreat(db);
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
            //需要在onOpen时检查数据库,否则module的数据库创建不了
            onDataBaseCreat(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onDataBaseUpgrade(db, oldVersion, newVersion);
            onCreate(db);
        }

    }


    protected SQLiteDatabase getDB() {
        return openHelper.getWritableDatabase();
    }

    public void close() {
        if (db != null)
            db.close();
    }

    /**
     * 删除整个表
     *
     * @param table_name
     */
    public void delete(String table_name) {
        delete(table_name, null, null);
    }

    /**
     * 删除表中的某些条件
     *
     * @param table_name
     * @param queryType
     * @param queryValue
     */
    public void delete(String table_name, String queryType, String queryValue) {
        db = getDB();
        db.delete(table_name, queryType + "=?", new String[]{queryValue});
    }
}
