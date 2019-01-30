package com.cosplay.kotlin.hw.composite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Author:wangzhiwei on 2019/1/29.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class ViewGroupElement extends PageElement {
    private List<PageElement> mPageElements = new ArrayList<>();//用来保存页面元素
    public ViewGroupElement(String name) {
        super(name);
    }
    public void addViewElement(PageElement pageElement){
        mPageElements.add(pageElement);
    }
    public void removeViewElement(PageElement pageElement){
        mPageElements.remove(pageElement);
    }
    public void clearViewElement(){
        mPageElements.clear();
    }


    @Override
    public void print(String placeholder) {
        System.out.println(placeholder + "└──" + getName());
        Iterator<PageElement> i = mPageElements.iterator();
        while (i.hasNext()) {
            PageElement pageElement = i.next();
            pageElement.print(placeholder + "   ");
        }
    }
}
