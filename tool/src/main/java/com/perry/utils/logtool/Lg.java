package com.perry.utils.logtool;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 类描述     Log 工具类
 * 创建人:    perry
 * 创建时间:  2016/3/29 15:52
 */
public class Lg {

    /* LOG 的 TAG*/
    private static final String LOG_TAG = "ProxySet";

    /* SD卡的log文件 */
    private static final String LOG_FILE = Environment
            .getExternalStorageDirectory().getPath() + LOG_TAG + ".log";

    public static void i(String log) {
        Log.i(LOG_TAG, "I:" + log);
    }

    public static void e(Exception e) {
        Lg.e(e.toString());
        StackTraceElement[] element = e.getStackTrace();
        if (element.length > 0) {
            Lg.e(element[0].getClassName() + "." + element[0].getMethodName()
                    + "(" + element[0].getLineNumber() + ")");
        }
        //e.printStackTrace();
    }

    public static void e(String log) {
        Log.e(LOG_TAG, "E:" + log);
    }

    public static void w(String log) {
        Log.w(LOG_TAG, "W:" + log);
    }

    /**
     * 打印 log 到文件的方法
     *
     * @param content 打印的 log 信息
     */
    public static void logFile(String content) {
        try {
            content += "\n";
            File file = new File(LOG_FILE);
            if (file != null) {
                RandomAccessFile raf = new RandomAccessFile(file, "rw");
                raf.seek(file.length());
                raf.write(content.getBytes());
                raf.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
