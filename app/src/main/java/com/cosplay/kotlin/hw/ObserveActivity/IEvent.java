package com.cosplay.kotlin.hw.ObserveActivity;

/**
 * Author:wangzhiwei on 2018/12/21.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public interface IEvent {
    void publishMessage(Object message);
    void registerEvent(ISubscribe subscribe);
    void removeEvent(ISubscribe subscribe);
}
