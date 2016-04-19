package com.blazefire.perry.proxy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.blazefire.perry.actions.RedsocksActios;
import com.blazefire.perry.config.Urls;

import com.blazefire.perry.db.biz.ProxyInfoDB;
import com.perry.utils.httptool.CustomHttpURLConnection;
import com.perry.utils.threadtool.ThreadPoolManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private Button mBtnProxy_ON_OFF;
    private Button mBtnShowProxyList;
    private Button mBtnUpdateDB;
    private RedsocksActios mRedsodks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnProxy_ON_OFF = (Button) findViewById(R.id.ID_Proxy_ON_OFF);
        mBtnShowProxyList = (Button) findViewById(R.id.ID_ShowListProxy);
        mBtnUpdateDB = (Button) findViewById(R.id.ID_UpdateProxyInfo);
        mRedsodks = new RedsocksActios(this);

        showBtnText();

        /* 点击 代理开关按钮*/
        mBtnProxy_ON_OFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThreadPoolManager.getInstance().addTask(new Runnable() {
                    @Override
                    public void run() {
                        mRedsodks.startRedsocks("192.168.1.9",3128,"http");

                    }
                });
                showBtnText();
                //new RedsocksActios().restartWiFi(MainActivity.this);
            }
        });
        mBtnShowProxyList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // new RedsocksActios().restartAirMode(MainActivity.this);
            }
        });

        mBtnUpdateDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThreadPoolManager.getInstance().addTask(new Runnable() {
                    @Override
                    public void run() {
                        //联网获取代理ip
                        String[] proxy = CustomHttpURLConnection.GetRequest(Urls.DAILI666API_COM).split("\\n");
                        ProxyInfoDB proxyInfoDB = new ProxyInfoDB(MainActivity.this);
                        for (int i = 0; i != proxy.length; i++) {
                            Map<String, String> map = new HashMap<>();
                            map.put("apikey", "e6332c9450084d4c503c8262b13f6af1");
                            String[] proxyinfo = proxy[i].split(":");
                            String result = CustomHttpURLConnection.GetRequest(Urls.IP_LOOK_UPSERVICE_URL + proxyinfo[0], map);
                            String countryName = null;
                            try {
                                countryName = new JSONObject(result).getJSONObject("retData").getString("country");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (countryName !=null){
                                proxyInfoDB.insert(proxyinfo[0],Integer.parseInt(proxyinfo[1]),countryName);
                                proxyInfoDB.insert(proxyinfo[0],Integer.parseInt(proxyinfo[1]),countryName);
                            }
                            proxyInfoDB.insert(proxyinfo[0],Integer.parseInt(proxyinfo[1]),0);
                        }

                    }
                });

            }
        });
    }

    public void showBtnText(){
        if (mRedsodks.checklistener()){
            //如果代理已经启动了, 显示关闭代理按钮
            mBtnProxy_ON_OFF.setText("关闭代理");
        }else{
            mBtnProxy_ON_OFF.setText("启动代理");
        }
    }

}


