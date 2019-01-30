package com.cosplay.kotlin.hw.Observerable;

/**
 * Author:wangzhiwei on 2018/12/20.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class ObserverableModeUtils {
    public static void main(String[] args) {
        FirstObserverable observerable = new FirstObserverable();

        FirstObserver observer1 = new FirstObserver("kkk__observer11");
        FirstObserver observer2 = new FirstObserver("kkk__observer22");
        FirstObserver observer3 = new FirstObserver("kkk__observer33");

        observerable.registerObserver(observer1);
        observerable.registerObserver(observer2);
        observerable.registerObserver(observer3);
        observerable.setMessage("l lll  llll");

        observerable.removeObserver(observer1);
        observerable.setMessage("我不在绑定1了");


    }


}
