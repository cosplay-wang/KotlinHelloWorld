package com.cosplay.kotlin.hw.publishSubscribe;

/**
 * Author:wangzhiwei on 2018/12/20.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class Subscribe implements ISubscribe {
    String subName;

    public Subscribe(String subName) {
        this.subName = subName;
    }

    @Override
    public void getMessage(Object message) {
        System.out.println(subName+"--接收到了事件：--"+message);
    }
}
