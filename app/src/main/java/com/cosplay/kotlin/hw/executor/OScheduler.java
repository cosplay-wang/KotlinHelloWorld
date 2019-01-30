package com.cosplay.kotlin.hw.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Author:wangzhiwei on 2019/1/10.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class OScheduler {
    Executor executor;

    private OScheduler(Executor executor) {
        this.executor = executor;
    }

    private static OScheduler oScheduler;

    public static OScheduler getOScheduler() {
        if (oScheduler == null) {
            oScheduler = new OScheduler(Executors.newCachedThreadPool());
        }
        return oScheduler;
    }

    public Worker createWorker() {
        return new Worker(executor);
    }

    public static class Worker {
        Executor executor;

        public Worker(Executor executor) {
            this.executor = executor;
        }

        public void schedule(Runnable runnable) {
            executor.execute(runnable);
        }
    }
}
