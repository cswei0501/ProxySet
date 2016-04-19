package com.blazefire.perry.db.biz;


import android.content.Context;
import android.database.Cursor;

import com.blazefire.perry.db.CountryColumn;
import com.blazefire.perry.db.DBHelper;
import com.blazefire.perry.db.ProxyInfoColumn;
import com.perry.utils.logtool.Lg;


/**
 * 项目名称:  ProxySet
 * 类描述
 * 创建人:    perry
 * 创建时间:  2016/3/13 14:58
 */
public class ProxyInfoDB {

    private DBHelper dbHelper;

    public ProxyInfoDB(Context context) {
        dbHelper = DBHelper.getInstance(context);
    }

    /**
     * 查询国家的id
     *
     * @param Countryname   国家名称
     * @return              国家的id
     */
    public int queryCountry(String Countryname) {
        int key = 0;

        String SQL = "select * from " + CountryColumn.TABLE_NAME +
                " where " + CountryColumn.COUNTRY_NAME + "='" + Countryname + "'";

        Cursor query = dbHelper.rawQuery(SQL, null);
/*
        Cursor query = dbHelper.query(CountryColumn.TABLE_NAME, new String[]{CountryColumn._ID},
                CountryColumn.COUNTRY_NAME + "=?", new String[]{Countryname});
*/
        if (query.getCount() != 0) {
            query.moveToFirst();
            int nameColumn = query.getColumnIndex(CountryColumn._ID);
            key = query.getInt(nameColumn);

        }
        query.close();
        return key;
    }

    public boolean CheckIpIsexist(String ipaddr, int prot){
        String SQL = "select * from " + ProxyInfoColumn.TABLE_NAME +
                " where " + ProxyInfoColumn.IPADDR + "='" + ipaddr+
                "' and "+ProxyInfoColumn.IPPROT + "='"+prot+"'";
        Cursor query = dbHelper.rawQuery(SQL, null);
        int ret = query.getCount();
        query.close();
        if (ret != 0){
            return true;
        }
        return false;


    }
    /**
     * 插入proxy_info表数据信息
     *
     * @param ipaddr      代理ip地址
     * @param prot        代理ip端口
     * @param countryName 国家名称
     */
    public void insert(String ipaddr, int prot, String countryName) {
        int countryId = queryCountry(countryName);
        insert(ipaddr, prot, countryId);
    }

    /**
     * 插入proxy_info表数据信息
     *
     * @param ipaddr    代理ip地址
     * @param prot      代理ip端口
     * @param countryId 国家的key
     */
    public void insert(String ipaddr, int prot, int countryId) {
        if (CheckIpIsexist(ipaddr,prot)){
            Lg.i("IP is isexist"+ipaddr + ":"+prot);
            return;
        }
        String SQL = "insert into " + ProxyInfoColumn.TABLE_NAME + "("
                + ProxyInfoColumn.COUNTRY_KEY + "," + ProxyInfoColumn.IPADDR + ","
                + ProxyInfoColumn.IPPROT + ") values(" + countryId + ",'" + ipaddr
                + "'," + prot + ")";
        dbHelper.ExecSQL(SQL);
    }

    public Cursor showTable() {
        String SQL = "select * from " + ProxyInfoColumn.TABLE_NAME;
        return dbHelper.rawQuery(SQL, null);
    }

    // ip   prot    countryname icon
    public Cursor queryshowSQL() {
        String SQL = "select " + ProxyInfoColumn.TABLE_NAME + "." + ProxyInfoColumn.IPADDR +
                ProxyInfoColumn.TABLE_NAME + "." + ProxyInfoColumn.IPPROT +
                CountryColumn.TABLE_NAME + "." + CountryColumn.COUNTRY_NAME +
                CountryColumn.TABLE_NAME + "." + CountryColumn.COUNTRY_ICON +
                "from " + ProxyInfoColumn.TABLE_NAME + "," + CountryColumn.TABLE_NAME +
                " where " + ProxyInfoColumn.COUNTRY_KEY + "=1";
        return dbHelper.rawQuery(SQL, null);
    }

    public void dbClose() {
        dbHelper.closeDb();
    }
}
