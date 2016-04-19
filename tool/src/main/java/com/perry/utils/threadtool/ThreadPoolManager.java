package com.perry.utils.threadtool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 类描述
 * 创建人:    perry
 * 创建时间:  2016/3/29 16:23
 */
public class ThreadPoolManager {
    private ExecutorService service;

    private ThreadPoolManager(){
        int num = Runtime.getRuntime().availableProcessors();
        service = Executors.newFixedThreadPool(num*2);
    }

    private static ThreadPoolManager manager;


    public static ThreadPoolManager getInstance(){
        if(manager==null)
        {
            manager= new ThreadPoolManager();
        }
        return manager;
    }

    public void addTask(Runnable runnable){
        service.submit(runnable);
    }
}
