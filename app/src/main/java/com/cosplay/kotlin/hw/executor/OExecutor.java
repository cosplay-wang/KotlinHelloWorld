package com.cosplay.kotlin.hw.executor;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Author:wangzhiwei on 2019/1/10.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class OExecutor {
    Executor executor;

    public OExecutor() {
        executor = Executors.newCachedThreadPool();
    }
}
