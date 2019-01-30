package com.cosplay.kotlin.hw.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Author:wangzhiwei on 2019/1/28.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class DynamicProxy implements InvocationHandler {
    DynamicSubject dynamicSubject;

    public DynamicProxy(DynamicSubject dynamicSubject) {
        this.dynamicSubject = dynamicSubject;
    }

    /**
     *
     * @param proxy 代理类
     * @param method 被代理的方法
     * @param args 被代理方法的参数
     * @return 返回代理对象
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("前");
        method.invoke(dynamicSubject);
        System.out.println("后");
        return null;
    }
}
