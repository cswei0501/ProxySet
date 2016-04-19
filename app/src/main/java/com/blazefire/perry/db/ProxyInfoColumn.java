package com.blazefire.perry.db;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称:  ProxySet
 * 类描述
 * 创建人:    perry
 * 创建时间:  2016/3/28 13:10
 */
public class ProxyInfoColumn extends DatabaseColumn{

    /* 代理信息表名 */
    public static final String TABLE_NAME = "proxy_info";

    /*  表字段 关联的国家信息表 id */
    public static final String COUNTRY_KEY = "count_id";

    /*  表字段 代理的IP地址 */
    public static final String IPADDR = "ip_address";

    /*  表字段 代理的IP端口 */
    public static final String IPPROT = "ip_prot";

    private static final Map<String,String> mColumnMap = new HashMap<String,String>();
    static {
        mColumnMap.put(_ID, "integer primary key autoincrement");
        mColumnMap.put(COUNTRY_KEY, "integer not null");
        mColumnMap.put(IPADDR, "text not null");
        mColumnMap.put(IPPROT, "integer not null");
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected Map<String, String> getTableMap() {
        return mColumnMap;
    }
}
