package com.cosplay.kotlin.hw.eventbus;

/**
 * Author:wangzhiwei on 2020/4/30.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class EventBean {
    String name;
    Object object;

    public EventBean(String name, Object object) {
        this.name = name;
        this.object = object;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
