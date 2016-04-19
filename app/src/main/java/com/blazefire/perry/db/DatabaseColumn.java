package com.blazefire.perry.db;

import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.Map;

/**
 * 项目名称:  ProxySet
 * 类描述
 * 创建人:    perry
 * 创建时间:  2016/3/28 10:31
 */
public abstract class DatabaseColumn implements BaseColumns {

    /**
     * 数据库表类反射
     */
    public static final String[] SUBCLASSES = new String[] {
            "com.blazefire.perry.db.CountryColumn" ,
            "com.blazefire.perry.db.ProxyInfoColumn"};

    /**
     *  根据子类创建数据表
     * @return
     */
    public String getTableCreateor() {
        return getTableCreator(getTableName(), getTableMap());
    }

    /**
     * 初始化所有要反射的类
     *
     * @return Array of sub-classes.
     */
    @SuppressWarnings("unchecked")
    public static final Class<DatabaseColumn>[] getSubClasses() {
        ArrayList<Class<DatabaseColumn>> classes = new ArrayList<Class<DatabaseColumn>>();
        Class<DatabaseColumn> subClass = null;
        for (int i = 0; i < SUBCLASSES.length; i++) {
            try {
                subClass = (Class<DatabaseColumn>) Class.forName(SUBCLASSES[i]);
                classes.add(subClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                continue;
            }
        }
        return classes.toArray(new Class[0]);
    }
    /**
     * 用哈希map 创建语句创建表
     *
     * @param tableName
     *            创建表的名称
     * @param map
     *            创建表的列
     * @return
     */
    private static final String getTableCreator(String tableName,
                                                Map<String, String> map) {
        String[] keys = map.keySet().toArray(new String[0]);
        String value = null;
        StringBuilder creator = new StringBuilder();
        creator.append("CREATE TABLE ").append(tableName).append("( ");
        int length = keys.length;
        for (int i = 0; i < length; i++) {
            value = map.get(keys[i]);
            creator.append(keys[i]).append(" ");
            creator.append(value);
            if (i < length - 1) {
                creator.append(",");
            }
        }
        creator.append(")");
        return creator.toString();
    }

    abstract public String getTableName();

    //abstract public Uri getTableContent();

    abstract protected Map<String, String> getTableMap();
}
