package com.cosplay.kotlin.hw.executor;

/**
 * Author:wangzhiwei on 2019/1/11.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public interface ResultCallBack {
    void start();
    void complete();
    void doTimetTak();
    void error();
    void progress(int pro);
}
