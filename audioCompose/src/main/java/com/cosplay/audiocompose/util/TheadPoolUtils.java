package com.cosplay.audiocompose.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author:wangzhiwei on 2020/1/6.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class TheadPoolUtils {
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(8);
    public static ExecutorService getThreadPoolInstance(){
        return  fixedThreadPool;
    }
}
