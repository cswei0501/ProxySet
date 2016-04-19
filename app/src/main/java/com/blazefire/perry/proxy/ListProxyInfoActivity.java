package com.blazefire.perry.proxy;


import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.ListView;


import com.blazefire.perry.adapter.AdapterProxyInfo;
import com.blazefire.perry.config.Urls;
import com.blazefire.perry.db.CountryColumn;

import com.blazefire.perry.db.ProxyInfoColumn;
import com.blazefire.perry.db.biz.ProxyInfoDB;

import com.blazefire.perry.entity.ProxyInfo;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.perry.utils.httptool.CustomHttpURLConnection;
import com.perry.utils.threadtool.ThreadPoolManager;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ListProxyInfoActivity extends AppCompatActivity {

    private Handler handler;
    private PullToRefreshListView mPullRefreshListView;
    private AdapterProxyInfo adapterProxyInfo;
    private List<ProxyInfo> proxyInfoLinkedList;
    private ProxyInfoDB mProxyInfoDB;
    private ProxyInfoDB proxyInfoDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptr_list);

        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
        // Set a listener to be invoked when the list should be refreshed.
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                // Do work to refresh the list here.
                //new GetDataTask().execute();
                ThreadPoolManager.getInstance().addTask(new Runnable() {
                    @Override
                    public void run() {
                        List<ProxyInfo> list = new LinkedList<ProxyInfo>();
                        try {
                            //           if (NetWorkHelper.getInstance().isNetworkAvailable(getApplicationContext())){
                            String[] proxy = CustomHttpURLConnection.GetRequest(Urls.DAILI666API_COM).split("\\n");
                            for (int i = 0; i != proxy.length; i++) {
                                Map<String, String> map = new HashMap<>();
                                map.put("apikey", "e6332c9450084d4c503c8262b13f6af1");
                                String[] proxyinfo = proxy[i].split(":");
                                String result = CustomHttpURLConnection.GetRequest(Urls.IP_LOOK_UPSERVICE_URL + proxyinfo[0], map);
                                ProxyInfo ProxyInfo = new ProxyInfo();
                                ProxyInfo.resolveJson(result,Integer.parseInt(proxyinfo[1]));
                                proxyInfoDB.insert(proxyinfo[0],Integer.parseInt(proxyinfo[1]),1);
                                list.add(ProxyInfo);
                            }

                            //       }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Cursor cursor = proxyInfoDB.queryshowSQL();
                        for (cursor.moveToFirst();cursor.isAfterLast();cursor.moveToNext()){
                            String name = cursor.getString(cursor.getColumnIndex(CountryColumn.COUNTRY_NAME));
                            String age = cursor.getString(cursor.getColumnIndex(CountryColumn.COUNTRY_ICON));
                            String sex = cursor.getString(cursor.getColumnIndex(ProxyInfoColumn.IPADDR));
                            String qwe = cursor.getString(cursor.getColumnIndex(ProxyInfoColumn.IPPROT));
                        }
                        Message msg = handler.obtainMessage();
                        msg.obj = list;
                        handler.sendMessage(msg);
                    }
                });
            }
        });
        adapterProxyInfo = new AdapterProxyInfo(this);
        ListView actualListView = mPullRefreshListView.getRefreshableView();
        actualListView.setAdapter(adapterProxyInfo);

        proxyInfoLinkedList = new LinkedList<ProxyInfo>();
        //proxyInfoLinkedList.add(new ProxyInfo("192.168.1.9","123","123","123","123","123",3128));

        adapterProxyInfo.appendToList(proxyInfoLinkedList);
        /*
        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.inputip);
        textView = (TextView) findViewById(R.id.addrinfo);
        getinfo = (Button) findViewById(R.id.getinfo);

        handler = new CHandler();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Map<String, String> map = new HashMap<>();
                        map.put("apikey", "e6332c9450084d4c503c8262b13f6af1");
                        String result = CustomHttpURLConnection.GetRequest(Urls.IP_LOOK_UPSERVICE_URL + editText.getText().toString(), map);
                        BaiduProxyInfo baiduProxyInfo = new BaiduProxyInfo();
                        baiduProxyInfo.resolveJson(result);
                        Message msg = handler.obtainMessage();
                        msg.obj = baiduProxyInfo;
                        handler.sendMessage(msg);
                    }
                };
                ThreadPoolManager.getInstance().addTask(runnable);

             }
        });

        getinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThreadPoolManager.getInstance().addTask(new Runnable() {
                    @Override
                    public void run() {
                        String result = CustomHttpURLConnection.GetRequest(Urls.DAILI666API_COM);
                        Message msg = handler.obtainMessage();
                        msg.obj = result;
                        handler.sendMessage(msg);
                    }
                });
            }
        });*/
    }


    class CHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            List<ProxyInfo> proxyInfos = (List<ProxyInfo>) msg.obj;

            adapterProxyInfo.appendToList(proxyInfos);
            adapterProxyInfo.notifyDataSetChanged();
            // Call onRefreshComplete when the list has been refreshed.
            mPullRefreshListView.onRefreshComplete();
        }
    }
/*
    private class GetDataTask extends AsyncTask<Void, Void, List<ProxyInfo>> {

        @Override
        protected List<ProxyInfo> doInBackground(Void... params) {
            List<ProxyInfo> list = new LinkedList<ProxyInfo>();
            String[] proxy = CustomHttpURLConnection.GetRequest(Urls.DAILI666API_COM).split("\\n");

            for (int i = 0; i != proxy.length; i++) {
                Map<String, String> map = new HashMap<>();
                map.put("apikey", "e6332c9450084d4c503c8262b13f6af1");
                String[] proxyinfo = proxy[i].split(":");
                String result = CustomHttpURLConnection.GetRequest(Urls.IP_LOOK_UPSERVICE_URL + proxyinfo[0], map);
                BaiduProxyInfo baiduProxyInfo = new BaiduProxyInfo();
                baiduProxyInfo.resolveJson(result);
                baiduProxyInfo.setPort(Integer.parseInt(proxyinfo[1]));
                list.add(baiduProxyInfo);
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<ProxyInfo> proxyInfos) {
            adapterProxyInfo.appendToList(proxyInfos);

            adapterProxyInfo.notifyDataSetChanged();

            // Call onRefreshComplete when the list has been refreshed.
            mPullRefreshListView.onRefreshComplete();
            super.onPostExecute(proxyInfos);
        }
    }
    */
}


