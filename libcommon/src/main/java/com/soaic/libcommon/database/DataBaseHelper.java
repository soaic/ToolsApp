package com.soaic.libcommon.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.soaic.libcommon.database.table.MessageTable;

/**
 * SQLite 数据库帮助类
 * Created by Soaic on 2017/11/24.
 */
public class DataBaseHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "TEST_DB";
    private static final int DB_VERSION = 1;
    private static DataBaseHelper dataBaseHelper;
    private static SQLiteDatabase db;

    private DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private static synchronized void initDataBase(Context context){
        if(dataBaseHelper == null){
            dataBaseHelper = new DataBaseHelper(context, DB_NAME, null, DB_VERSION);
        }
    }

    private static synchronized DataBaseHelper getInstance(Context context){
        initDataBase(context);
        return dataBaseHelper;
    }

    /**
     * 获取DataBase
     * @return SQLiteDatabase
     */
    public static synchronized SQLiteDatabase getDataBase(Context context){
        if(db == null){
            db = getInstance(context).getReadableDatabase();
        }
        return db;
    }

    /**
     * 关闭数据库
     */
    public static synchronized void closeDatabase() {
        if (db != null) {
            db.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //表创建
        db.execSQL(MessageTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //表更新
        switch (oldVersion) {
            case 1:
                break;
            case 2:
                break;
        }
    }
}
