package com.cosplay.kotlin.hw.composite;

/**
 * Author:wangzhiwei on 2019/1/29.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public abstract class PageElement {
    String name;

    public PageElement(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //抽象组件角色去掉增删等接口

    public abstract void print(String placeholder);
}
