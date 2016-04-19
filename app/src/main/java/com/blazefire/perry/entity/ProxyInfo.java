package com.blazefire.perry.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 项目名称:  ProxySet
 * 类描述
 * 创建人:    perry
 * 创建时间:  2016/3/11 13:52
 */
public class ProxyInfo {

    private String mIpAddr;          //IP地址
    private String mCountry;         //国家
    private String mProvince;        //省份 国外的默认值为none
    private String mCity;            //城市 国外的默认值为none
    private String mDistrict;        //地区 国外的默认值为none
    private String mCarrier;         //运营商  特殊IP显示为未知
    private int mPort;               //端口
    private int mIcon;

    public ProxyInfo(){
    }
    public ProxyInfo(String ipAddr,String country,int port,int icon){
        this.mIpAddr = ipAddr;
        this.mCountry = country;
        this.mPort = port;
        this.mIcon = icon;
    }
    public void resolveJson(String json,int prot){
        try {
            JSONObject jsonObject = new JSONObject(json);
//            this.errNum = jsonObject.getInt("errNum");
//            this.errMsg = jsonObject.getString("errMsg");
            JSONObject jsonarray = jsonObject.getJSONObject("retData");
            this.mIpAddr = jsonarray.getString("ip");
            this.mCountry=jsonarray.getString("country");
            this.mProvince=jsonarray.getString("province");
            this.mCity=jsonarray.getString("city");
            this.mDistrict = jsonarray.getString("district");
            this.mCarrier = jsonarray.getString("carrier");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String getmIpAddr() {
        return mIpAddr;
    }

    public void setmIpAddr(String mIpAddr) {
        this.mIpAddr = mIpAddr;
    }

    public String getmCountry() {
        return mCountry;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public String getmProvince() {
        return mProvince;
    }

    public void setmProvince(String mProvince) {
        this.mProvince = mProvince;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public String getmDistrict() {
        return mDistrict;
    }

    public void setmDistrict(String mDistrict) {
        this.mDistrict = mDistrict;
    }

    public String getmCarrier() {
        return mCarrier;
    }

    public void setmCarrier(String mCarrier) {
        this.mCarrier = mCarrier;
    }

    public int getmPort() {
        return mPort;
    }

    public void setmPort(int mPort) {
        this.mPort = mPort;
    }

    public int getmIcon() {
        return mIcon;
    }

    public void setmIcon(int mIcon) {
        this.mIcon = mIcon;
    }
}
