package com.cosplay.kotlin.hw.publishSubscribe;

/**
 * Author:wangzhiwei on 2018/12/20.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public interface IEvent{
    void bindSubscribe(ISubscribe subscribe);

    void unBindSubscribe(ISubscribe subscribe);

    void notifySubscribe();

}
