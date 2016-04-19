package com.perry.utils.httptool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * 项目名称:  ProxySet
 * 类描述
 * 创建人:    perry
 * 创建时间:  2016/3/29 15:52
 */
public class NetWorkHelper {
    private static NetWorkHelper mNetWorkHelper;
    private static String LOG_TAG = "NetWorkHelper";

    public enum mNetworkType { _NONETWORK/*没有联网*/,_2G,_3G,_4G,_WIFI}    //联网类型

    private NetWorkHelper(){}
    //获取单例
    public static NetWorkHelper getInstance(){
        if (null == mNetWorkHelper)
            mNetWorkHelper = new NetWorkHelper();
        return mNetWorkHelper;
    }

    /**
     * 判断是否有网络连接
     */
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity == null) {
            Log.w(LOG_TAG, "couldn't get connectivity manager");
        } else {
            /*
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].isAvailable()) {
                        Log.d(LOG_TAG, "network is available");
                        return true;
                    }
                }
            }
            */
            // 获取网络连接管理的对象
            NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
            if (null != networkInfo && networkInfo.isConnected()){
                // 判断当前网络是否已经连接
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }

            }
            return false;
        }
        Log.d(LOG_TAG, "network is not available");
        return false;
    }

    /**
     * 判断MOBILE网络是否可用     *
     * @param context
     * @return
     * @throws Exception
     */
    public boolean isMobileDataEnable(Context context) throws Exception {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        /*boolean isMobileDataEnable = false;
        isMobileDataEnable = connectivityManager.getNetworkInfo(
                ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        return isMobileDataEnable;
        */
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * 判断wifi 是否可用
     * @param context
     * @return
     * @throws Exception
     */
    public boolean isWifiDataEnable(Context context) throws Exception {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        /*boolean isWifiDataEnable = false;
        isWifiDataEnable = connectivityManager.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        return isWifiDataEnable;*/
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /*获取联网类型*/
    public mNetworkType GetNetworkType(Context context){
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){  //如果联网了
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
                return mNetworkType._WIFI;
            else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
                String _strSubTypeName = networkInfo.getSubtypeName();
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        return mNetworkType._2G;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        return mNetworkType._3G;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        return mNetworkType._4G;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") ||
                                _strSubTypeName.equalsIgnoreCase("WCDMA") ||
                                _strSubTypeName.equalsIgnoreCase("CDMA2000")){
                            return mNetworkType._3G;
                        }
                        break;
                }
            }

        }
        return mNetworkType._NONETWORK;
    }

    /*判断联网状态*/
    /*
    public boolean checkNetState(Context context){
        boolean netstate = false;
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++)
                {
                    // CONNECTED    已连接
                    // CONNECTING   正在连接
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        netstate = true;
                        break;
                    }
                }
            }
        }
        return netstate;
    }
    */
}
