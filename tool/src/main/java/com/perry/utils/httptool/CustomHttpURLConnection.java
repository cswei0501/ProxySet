package com.perry.utils.httptool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * 类描述:    联网工具类
 * 创建人:    perry
 * 创建时间:  2016/3/29 16:06
 */
public class CustomHttpURLConnection {

    /* 编码格式,请根据自己要求设置 */
    private static final String ENCODING = "utf-8";

    public static String GetRequest(String strUrl) {
        return GetRequest(strUrl,null);
    }

    public static String GetRequest(String strUrl, Map<String, String> requestproperty) {
        StringBuffer sb = new StringBuffer();

        HttpURLConnection conn = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {
            URL url = new URL(strUrl);      //创建URL对象
            conn = (HttpURLConnection) url.openConnection(); //获取 HttpURLConnection
            conn.setDoInput(true);// 设置是否从httpUrlConnection读入，默认情况下是true;
            conn.setInstanceFollowRedirects(true);//打开重定向
            conn.setConnectTimeout(3000);       //超时
            conn.setReadTimeout(4000);
            conn.setRequestProperty("accept", "*/*");
            if (requestproperty != null) {//如果不为空
                Iterator it = requestproperty.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry=(Map.Entry)it.next();
                    conn.setRequestProperty(entry.getKey().toString(),entry.getValue().toString());
                }
            }
            //conn.connect();
            // 此处getInputStream会隐含的进行connect(即：如同调用上面的connect()方法. getOutputStream()也是同理)
            inputStream = conn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, ENCODING);
            bufferedReader = new BufferedReader(inputStreamReader);
            String strLine = null;
            while ((strLine = bufferedReader.readLine()) != null) {
                sb.append(strLine + "\n");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeAll(inputStream,inputStreamReader,bufferedReader);
            conn.disconnect();// 销毁连接
        }
        return sb.toString();
    }

    public static String PostRequest(String strUrl, String params) {
        return PostRequest(strUrl, params,null);
    }
    public static String PostRequest(String strUrl, String params,Map<String, String> requestproperty) {
        StringBuffer sb = new StringBuffer();
        HttpURLConnection conn = null;

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;

        try {
            URL url = new URL(strUrl);      //创建URL对象
            conn = (HttpURLConnection) url.openConnection(); //获取 HttpURLConnection
            conn.setDoInput(true);// 设置是否从httpUrlConnection读入，默认情况下是true;
            conn.setDoOutput(true);// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false;
            conn.setRequestMethod("POST");// 设定请求的方法为"POST"，默认是GET
            conn.setUseCaches(false);// Post 请求不能使用缓存
            conn.setInstanceFollowRedirects(true);  //打开重定向
            conn.setConnectTimeout(3000);       //超时
            conn.setReadTimeout(4000);
            conn.setRequestProperty("accept", "*/*");
            // 设定传送的内容类型是可序列化的java对象
            // (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if (!requestproperty.isEmpty()) {//如果不为空
                Iterator it = requestproperty.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry=(Map.Entry)it.next();
                    conn.setRequestProperty(entry.getKey().toString(),entry.getValue().toString());
                }
            }
            //conn.connect();
            // 此处getInputStream会隐含的进行connect(即：如同调用上面的connect()方法. getOutputStream()也是同理)
            outputStream = conn.getOutputStream();
            // 现在通过输出流对象构建对象输出流对象，以实现输出可序列化的对象。
            outputStreamWriter = new OutputStreamWriter(outputStream, ENCODING);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(params);// 向对象输出流写出数据，这些数据将存到内存缓冲区中
            bufferedWriter.flush();// 刷新对象输出流

            inputStream = conn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String strLine = null;
            while ((strLine = bufferedReader.readLine()) != null) {
                sb.append(strLine + "\n");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeAll(outputStream,outputStreamWriter,bufferedWriter);
            closeAll(inputStream,inputStreamReader,bufferedReader);
            conn.disconnect();// 销毁连接
        }
        return sb.toString();
    }

    private static void closeAll(InputStream inputStream,InputStreamReader inputStreamReader,
                          BufferedReader bufferedReader){

        if (inputStream != null) {
            try{
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (inputStreamReader !=null){
            try {
                inputStreamReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (bufferedReader != null){
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void closeAll(OutputStream outputStream,OutputStreamWriter outputStreamWriter,
                                 BufferedWriter bufferedWriter){

        if (outputStream != null) {
            try{
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (outputStreamWriter !=null){
            try {
                outputStreamWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (bufferedWriter != null){
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
