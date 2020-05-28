package com.cosplay.kotlin.hw.datacache.bean;

import com.cosplay.kotlin.hw.datacache.protocol.CacheObserver;
import com.cosplay.kotlin.hw.datacache.protocol.CacheSubject;

/**
 * Author:wangzhiwei on 2020/4/26.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class BaseBean extends CacheSubject{
    public static void main(){
        try {
            Class clazz = Class.forName("");
            Class jj = (Class) clazz.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
