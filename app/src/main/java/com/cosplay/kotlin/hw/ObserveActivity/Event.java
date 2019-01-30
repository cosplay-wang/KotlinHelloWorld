package com.cosplay.kotlin.hw.ObserveActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:wangzhiwei on 2018/12/21.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class Event implements IEvent {
    List<ISubscribe> subscribeList = new ArrayList<>();

    private Event() {}

    @Override
    public void publishMessage(Object message) {
        for (ISubscribe subscribe : subscribeList) {
            subscribe.getMessage(message);
        }
    }

    @Override
    public void registerEvent(ISubscribe subscribe) {
        if (!subscribeList.contains(subscribe)) {
            subscribeList.add(subscribe);
        }
    }

    @Override
    public void removeEvent(ISubscribe subscribe) {
        if (subscribeList.contains(subscribe)) {
            subscribeList.remove(subscribe);
        }
    }

    private static class EventSingleHolder {
        private final static Event eventSingleHoler = new Event();
    }

    public static Event getEventInstance() {
        return EventSingleHolder.eventSingleHoler;
    }
}
