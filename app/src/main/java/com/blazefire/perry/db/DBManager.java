package com.blazefire.perry.db;

/**
 * 项目名称:  ProxySet
 * 类描述
 * 创建人:    perry
 * 创建时间:  2016/3/25 18:09
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    private static DBManager mDBManagerInstance = null;
    private Context mContext = null;
    private DBHelper dbHelper = null;
    private SQLiteDatabase mSQLiteDB = null;

    private DBManager (Context ctx){
        mContext = ctx;
        if(dbHelper == null){
            dbHelper = DBHelper.getInstance(ctx);
            mSQLiteDB = dbHelper.getReadableDatabase();
        }
    }

    public static DBManager getDBManager(Context ctx){
        if(mDBManagerInstance == null ){
            mDBManagerInstance = new DBManager(ctx);
        }
        return mDBManagerInstance;
    }
}
