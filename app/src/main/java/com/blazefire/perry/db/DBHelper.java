package com.blazefire.perry.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.Map;

/**
 * 项目名称:  ProxySet
 * 类描述
 * 创建人:    perry
 * 创建时间:  2016/3/13 14:58
 */
public class DBHelper extends SQLiteOpenHelper {
    // 数据库名
    private static final String DB_NAME = "proxy.db";
    // 数据库版本号
    private static final int DB_VERSION = 1;

    private SQLiteDatabase db;

    private static final String LOG_TAG = "DBHelper";
    //单例模式
    private static DBHelper mdbHelper;
    public static DBHelper getInstance(Context context) {
        if (mdbHelper == null) {
            mdbHelper = new DBHelper(context);
        }
        return mdbHelper;
    }

    private DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(LOG_TAG, "DBHelper onCreate");
        this.db = db;

        operateTable(db, null);     //创建表

        initCountry();              //初始化国家表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == newVersion) {
            return;
        }
        operateTable(db, "DROP TABLE IF EXISTS ");
        onCreate(db);
    }

    /**
     *   创建 反射类里的所有的表
     * @param db
     * @param actionString
     */
    public void operateTable(SQLiteDatabase db, String actionString) {
        Class<DatabaseColumn>[] columnsClasses = DatabaseColumn.getSubClasses();
        DatabaseColumn columns = null;

        for (int i = 0; i < columnsClasses.length; i++) {
            try {
                columns = columnsClasses[i].newInstance();
                if ("".equals(actionString) || actionString == null) {
                    db.execSQL(columns.getTableCreateor());
                } else {
                    db.execSQL(actionString + columns.getTableName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  插入一条数据
     * @param Table_Name    要插入数据的表的名称
     * @param values        key-values
     * @return              新插入行标识(行号)
     */
    public long insert(String Table_Name, ContentValues values) {
        if (db == null)
            db = getWritableDatabase();
        return db.insert(Table_Name, null, values);
    }

    /**
     *  删除数据
     * @param Table_Name    要删除数据的表名
     * @param id            key 数据的主键
     * @return              受影响的行数
     */
    public int delete(String Table_Name, int id) {
        if (db == null)
            db = getWritableDatabase();
        return db.delete(Table_Name, BaseColumns._ID + "=?",
                new String[] { String.valueOf(id) });
    }

    /**
     *  更新数据
     * @param Table_Name    要更新数据的表名
     * @param values        key-values
     * @param WhereClause   可选的where语句
     * @param whereArgs     whereClause语句中表达式的？占位参数列表
     * @return
     */
    public int update(String Table_Name, ContentValues values,
                      String WhereClause, String[] whereArgs) {
        if (db == null) {
            db = getWritableDatabase();
        }
        return db.update(Table_Name, values, WhereClause, whereArgs);
    }

    /**
     *  查询数据
     * @param Table_Name    要查询数据的表名
     * @param columns       想要显示的列，若为空则返回所有列，如果不是返回所有列，不建议设置为空
     * @param whereStr      可选的where语句
     * @param whereArgs     whereClause语句中表达式的？占位参数列表
     * @return
     */
    public Cursor query(String Table_Name, String[] columns, String whereStr,
                        String[] whereArgs) {
        if (db == null) {
            db = getReadableDatabase();
        }
        return db.query(Table_Name, columns, whereStr, whereArgs, null, null,
                null);
    }

    public Cursor rawQuery(String sql, String[] args) {
        if (db == null) {
            db = getReadableDatabase();
        }
        return db.rawQuery(sql, args);
    }

    public void ExecSQL(String sql) {
        if (db == null) {
            db = getWritableDatabase();
        }
        db.execSQL(sql);
    }

    public void closeDb() {
        if (db != null) {
            db.close();
            db = null;
        }
    }

    public void initCountry(){
        String SQL;
        for (Map.Entry<String,Integer> map:CountryColumn.mDataMap.entrySet()) {
            SQL = "insert into " + CountryColumn.TABLE_NAME + "("
                    + CountryColumn.COUNTRY_NAME + "," + CountryColumn.COUNTRY_ICON
                    +") values('" + map.getKey() + "'," + map.getValue() + ")";
            db.execSQL(SQL);
        }
    }
}
