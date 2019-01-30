package com.cosplay.kotlin.hw.proxy;

import java.lang.reflect.Proxy;

/**
 * Author:wangzhiwei on 2019/1/28.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class ProxyUtils {
    public static void main(String[] args) {
        /**
         * 静态代理模式的意义是
         * 提供了监听 控制的
         * 为其他对象提供一种代理以控制对这个对象的访问。
         * 直接执行的无法控制
         */
        ProxyDemo demo = new ProxyDemo();
        ProxyDemo.BuyOwn buyOwn = demo.new BuyOwn();
        buyOwn.buy();
        ProxyDemo.people people1 = demo.new people();
        ProxyDemo.BuyProxy buyProxy = demo.new BuyProxy(people1);
        buyProxy.buy();
        /**
         * 动态代理的方式
         * 主要是通过反射
         * 代理类和委托类的关系是在程序运行时确定
         */
        DynamicSubject dynamicSubject = new DynamicSubject();
        DynamicProxy dynamicProxy = new DynamicProxy(dynamicSubject);
        ClassLoader classLoader = dynamicSubject.getClass().getClassLoader();
        ProxyDemo.Ibuy proxyInstance = (ProxyDemo.Ibuy) Proxy.newProxyInstance(classLoader, dynamicSubject.getClass().getInterfaces(), dynamicProxy);
        proxyInstance.buy();


    }
}
