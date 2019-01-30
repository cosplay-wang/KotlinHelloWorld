package com.cosplay.kotlin.hw.Observerable;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:wangzhiwei on 2018/12/20.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class FirstObserverable implements Observerable {
    private List<Observer> list;
    private String message;

    public FirstObserverable() {
        list = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer o) {
        list.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        if (list.contains(o)) {
            list.remove(o);
        }
    }

    @Override
    public void notifyObserver() {
        for (Observer observer : list) {
            observer.getMessage(message);
        }
    }
    public void setMessage(String message){
        this.message = message;
        notifyObserver();
    }
}
