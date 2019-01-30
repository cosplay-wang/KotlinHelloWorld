package com.cosplay.kotlin.hw.proxy;

/**
 * Author:wangzhiwei on 2019/1/28.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class DynamicSubject implements ProxyDemo.Ibuy {
    @Override
    public void buy() {
    System.out.println("动态代理");
    }


}
