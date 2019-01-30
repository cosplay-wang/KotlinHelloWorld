package com.cosplay.kotlin.hw.composite;

/**
 * Author:wangzhiwei on 2019/1/29.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class ViewElement extends PageElement {
    public ViewElement(String name) {
        super(name);
    }

    @Override
    public void print(String placeholder) {
        System.out.println(placeholder + "──" + getName());
    }
}
