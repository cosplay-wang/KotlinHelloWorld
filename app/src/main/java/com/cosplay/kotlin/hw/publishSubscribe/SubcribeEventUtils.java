package com.cosplay.kotlin.hw.publishSubscribe;

/**
 * Author:wangzhiwei on 2018/12/20.
 * Email :wangzhiwei@moyi365.com
 * Description: 所谓的观察者模式。其实就是事件和订阅者的模式
 * 简单的事件和订阅者模式。
 * 区别就在于是否是事件直接调度订阅者
 */
public class SubcribeEventUtils {
    public static void main(String[] s){
        Event event = new Event();

        Subscribe subscribe1 = new Subscribe("faker");
        Subscribe subscribe2 = new Subscribe("mlxg");
        Subscribe subscribe3 = new Subscribe("ming");

        event.bindSubscribe(subscribe1);
        event.bindSubscribe(subscribe2);
        event.bindSubscribe(subscribe3);

        event.setMessage("S9中国加油!");

        event.unBindSubscribe(subscribe2);
        event.setMessage("s8 IG 起飞");
    }
}
