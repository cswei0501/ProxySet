package com.blazefire.perry.actions;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.provider.Settings;

import com.perry.utils.logtool.Lg;
import com.perry.utils.shelltool.ShellUtils;

/**
 * Created by perry on 2016/3/31.
 */
public abstract class BaseActios {

    /**
     *    开启飞行模式  SETAIRMODE + 1
     *    关闭飞行模式  SETAIRMODE + 0
     */
    private static final String SETAIRMODE="settings put global airplane_mode_on ";

    /**
     *   发送广播 使 SETAIRMODE 命令起作用
     *
     *   开启飞行模式    AMAIRMODE + "true"
     *   关闭飞行模式    AMAIRMODE + "false"
     */
    private static final String AMAIRMODE="am broadcast -a android.intent.action.AIRPLANE_MODE --ez state ";

    /* 打开飞行模式*/
    private static final String OPENAIRMODE[]={SETAIRMODE +1,AMAIRMODE + "true"};
    /*关闭飞行模式*/
    private static final String CLOSEAIRMODE[]={SETAIRMODE +0,AMAIRMODE + "false"};

    /**
     * 判断手机是否是飞行模式
     * @param context
     * @return
     */
    public static boolean getAirplaneMode(Context context){
        int isAirplaneMode = Settings.System.getInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) ;
        return (isAirplaneMode == 1)?true:false;
    }
    /**
     *   重启飞行模式,需要su权限
     *  @param context  context
     */
    public boolean restartAirMode(Context context){
        /*
        Settings.System.putInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 1);
        Intent intent = new Intent("android.intent.action.AIRPLANE_MODE").putExtra("state", true);
        context.sendBroadcast(intent);
        */

        if (ShellUtils.checkRootPermission()){
            //打开飞行模式
            Lg.i("打开飞行模式");
            ShellUtils.execCommand(OPENAIRMODE,true);
            if (getAirplaneMode(context)){
                Lg.i("手机已设置飞行模式");
            }else{
                Lg.e("设置飞行模式失败");
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     *  重启Wifi
     * @param context  context
     */
    public void restartWiFi(Context context){
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.isWifiEnabled()) {
            //关闭WiFi
            wifiManager.setWifiEnabled(false);
        }
        wifiManager.setWifiEnabled(true);
    }
}
