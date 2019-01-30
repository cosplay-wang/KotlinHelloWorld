package com.cosplay.kotlin.hw.publishSubscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:wangzhiwei on 2018/12/20.
 * Email :wangzhiwei@moyi365.com
 * Description:此例中是由事件直接管理订阅者，若是抽取此块代码，中间加一层。就是完整的订阅。发布者
 */
public class Event implements IEvent {
    List<ISubscribe> subscribeList;
    Object message;

    public Event() {
        subscribeList = new ArrayList<>();
    }

    @Override
    public void bindSubscribe(ISubscribe subscribe) {
        subscribeList.add(subscribe);
    }

    @Override
    public void unBindSubscribe(ISubscribe subscribe) {
        if (subscribeList.contains(subscribe)) {
            subscribeList.remove(subscribe);
        }
    }

    @Override
    public void notifySubscribe() {
        for (ISubscribe subscribe : subscribeList) {
            subscribe.getMessage(message);
        }
    }

    public void setMessage(Object message) {
        this.message = message;
        notifySubscribe();
    }
}
