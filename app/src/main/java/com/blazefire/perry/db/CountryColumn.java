package com.blazefire.perry.db;


import android.content.ContentValues;
import android.net.Uri;

import com.blazefire.perry.proxy.R;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称:  ProxySet
 * 类描述
 * 创建人:    perry
 * 创建时间:  2016/3/28 10:44
 */
public class CountryColumn extends DatabaseColumn {

    /* 国家信息表名 */
    public static final String TABLE_NAME = "country";

    /* 表字段  国家名称 */
    public static final String COUNTRY_NAME = "name";

    /* 表字段  国家图标 */
    public static final String COUNTRY_ICON = "icon";

    private static final Map<String,String> mColumnMap = new HashMap<String,String>();
    static {
        mColumnMap.put(_ID, "integer primary key autoincrement");
        mColumnMap.put(COUNTRY_NAME, "text not null");
        mColumnMap.put(COUNTRY_ICON, "integer");
    }

    public static final Map<String,Integer> mDataMap = new HashMap<String,Integer>();
    static {
        mDataMap.put("中国", R.mipmap.cn);
        mDataMap.put("阿富汗",R.mipmap.af);
        mDataMap.put("亚美尼亚",R.mipmap.am);
        mDataMap.put("孟加拉国",R.mipmap.bd);
        mDataMap.put("巴林",R.mipmap.bh);
        mDataMap.put("文莱",R.mipmap.bn);
        mDataMap.put("加拿大",R.mipmap.ca);
        mDataMap.put("印度尼西亚",R.mipmap.id);
        mDataMap.put("印度",R.mipmap.in);
        mDataMap.put("日本",R.mipmap.jp);
        mDataMap.put("韩国",R.mipmap.kr);
        mDataMap.put("美国",R.mipmap.us);
        mDataMap.put("荷兰",R.mipmap.nl);
        mDataMap.put("俄罗斯",R.mipmap.ru);
        mDataMap.put("巴西",R.mipmap.br);
        mDataMap.put("泰国",R.mipmap.th);
        mDataMap.put("厄瓜多尔",R.mipmap.ec);
        mDataMap.put("柬埔寨",R.mipmap.kh);
        mDataMap.put("新加坡",R.mipmap.sg);
        mDataMap.put("德国",R.mipmap.de);
        mDataMap.put("法国",R.mipmap.fr);
        mDataMap.put("马来西亚",R.mipmap.my);
        mDataMap.put("孟加拉国",R.mipmap.bd);
        mDataMap.put("孟加拉国",R.mipmap.bd);
        mDataMap.put("孟加拉国",R.mipmap.bd);
        mDataMap.put("孟加拉国",R.mipmap.bd);
        mDataMap.put("孟加拉国",R.mipmap.bd);
        mDataMap.put("孟加拉国",R.mipmap.bd);
        mDataMap.put("孟加拉国",R.mipmap.bd);
        mDataMap.put("未知",0);
    }
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }


//    public Uri getTableContent() {
//        return null;
//    }

    @Override
    protected Map<String, String> getTableMap() {
        return mColumnMap;
    }
}
