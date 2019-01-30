package com.cosplay.kotlin.hw.Observerable;

/**
 * Author:wangzhiwei on 2018/12/20.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public interface Observerable {
     void registerObserver(Observer o);
     void removeObserver(Observer o);
     void notifyObserver();
}
