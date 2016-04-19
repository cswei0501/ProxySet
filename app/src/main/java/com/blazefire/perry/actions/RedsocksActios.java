package com.blazefire.perry.actions;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;

import com.perry.utils.filetool.FileTool;
import com.perry.utils.logtool.Lg;
import com.perry.utils.shelltool.ShellUtils;

import java.io.File;
import java.net.Socket;

/**
 * Created by perry on 2016/3/31.
 */
public class RedsocksActios extends BaseActios {


    private String mBasedir = "/data/data/com.blazefire.perry.proxy/proxyData";        //assets 目录路径
    private Context mContext;

    public RedsocksActios(Context context) {
       //mBasedir = context.getApplicationContext().getFilesDir().getAbsolutePath();
        mContext = context;

    }

    /**
     * 初始化
     */
    private void init() {

        File fileRedsocks = new File(mBasedir + "/redsocks");
        File fileRedirect = new File(mBasedir + "/redirect.sh");
        File fileProxy = new File(mBasedir + "/proxy.sh");
        if (!fileRedsocks.exists()) {
            FileTool.copyAssert(mContext, "redsocks", mBasedir, "redsocks");
        }
        if (!fileRedirect.exists()) {
            FileTool.copyAssert(mContext, "redirect.sh", mBasedir, "redirect.sh");
        }
        if (!fileProxy.exists()) {
            FileTool.copyAssert(mContext, "proxy.sh", mBasedir, "proxy.sh");
        }
        String chmodrwx[] = {
                "chmod 777 " + mBasedir,
                "chown 0.0 " + mBasedir + "/redsocks",
                "chmod 777 " + mBasedir + "/redsocks",
                "chown 0.0 " + mBasedir + "/redirect.sh",
                "chmod 777 " + mBasedir + "/redirect.sh",
                "chown 0.0 " + mBasedir + "/proxy.sh",
                "chmod 777 " + mBasedir + "/proxy.sh"};
        ShellUtils.execCommand(chmodrwx, true, false);
    }

    /**
     * 启动代理服务
     *
     * @return true    启动服务成功
     * false   启动服务失败
     */
    public boolean startRedsocks(String IP, int port, String proxy_type) {
        //初始化文件
        init();
        String command[] = {null, null};
        command[0] = mBasedir + "/proxy.sh  start " + mBasedir + " " + proxy_type + " " + IP + " " + port + " false";
        command[1] = mBasedir + "/redirect.sh start " + proxy_type + "";
        Lg.i(command[0]);
        Lg.i(command[1]);
        if (ShellUtils.checkRootPermission()) {
            ShellUtils.CommandResult commandResult = ShellUtils.execCommand(command, true, false);
            if (commandResult.result != 0) {
                return false;
            }
            restartWiFi(mContext);
        }
        return true;

    }

    /**
     * 检测代理服务是否已经开启
     *
     * @return true   服务已经启动
     * false  服务没有启动
     */
    public boolean checklistener() {
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 8123);
        } catch (Exception e) {
        }

        if (socket != null && socket.isConnected()) {
            try {
                socket.close();
            } catch (Exception e) {
            }
            return true;
        } else {
            return false;
        }
    }

}
