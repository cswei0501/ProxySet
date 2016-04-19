package com.perry.utils;

import com.perry.utils.httptool.CustomHttpURLConnection;
import com.perry.utils.logtool.Lg;
import com.perry.utils.threadtool.ThreadPoolManager;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void threadtool_ThireadPoolManager() {

        ThreadPoolManager.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                Thread current = Thread.currentThread();
                for (int i = 0; i != 10; i++) {
                    System.out.println("次线程ID:" + current.getId());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread current = Thread.currentThread();
        for (int i = 0; i != 10; i++) {
            System.out.println("主线程ID:" + current.getId());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void httptool_CustomHttpURLConnection(){
        String source = CustomHttpURLConnection.GetRequest("http://www.baidu.com");
        System.out.print(source);
    }
}